package com.example.babartrihapsoro.getpluspos.view.main;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.PlanarYUVLuminanceSource;
import com.google.zxing.ReaderException;
import com.google.zxing.Result;
import com.google.zxing.common.HybridBinarizer;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Rect;
import android.hardware.Camera;
import android.os.Handler;
import android.os.Looper;
import android.util.AttributeSet;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import me.dm7.barcodescanner.core.BarcodeScannerView;
import me.dm7.barcodescanner.core.CameraUtils;
import me.dm7.barcodescanner.core.CameraWrapper;
import me.dm7.barcodescanner.core.DisplayUtils;
import me.dm7.barcodescanner.core.IViewFinder;

/**
 * Created by aldo on 4/5/17.
 * This class is ZXingScannerView with SquareViewFinder as IViewFinder
 * Since we can't overide createViewFinderView method directly
 * This class also limit Scan format to only QR Code.
 * Other than that, the code is similar from original ZXingScannerView class
 */
public class QrScannerView extends BarcodeScannerView {

    public static final List<BarcodeFormat> ALL_FORMATS = new ArrayList<>();

    private static final String TAG = "QrScannerView";

    static {
        ALL_FORMATS.add(BarcodeFormat.QR_CODE);
    }

    private MultiFormatReader multiFormatReader;

    private List<BarcodeFormat> formatList;

    private ResultHandler resultHandler;

    public QrScannerView(Context context) {
        super(context);
        initMultiFormatReader();
    }

    public QrScannerView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        initMultiFormatReader();
    }

    @Override
    protected IViewFinder createViewFinderView(Context context) {
        return new SquareViewFinder(context);
    }

    public void setResultHandler(ResultHandler resultHandler) {
        this.resultHandler = resultHandler;
    }

    @Override
    public void startCamera(int cameraId) {
        Camera camera = CameraUtils.getCameraInstance(cameraId);
        setupCameraPreview(CameraWrapper.getWrapper(camera, cameraId));
    }

    public Collection<BarcodeFormat> getFormats() {
        if (formatList == null) {
            return ALL_FORMATS;
        }
        return formatList;
    }

    public void setFormats(List<BarcodeFormat> formats) {
        formatList = formats;
        initMultiFormatReader();
    }

    private void initMultiFormatReader() {
        Map<DecodeHintType, Object> hints = new EnumMap<>(DecodeHintType.class);
        hints.put(DecodeHintType.POSSIBLE_FORMATS, getFormats());
        multiFormatReader = new MultiFormatReader();
        multiFormatReader.setHints(hints);
    }

    @Override
    public void onPreviewFrame(byte[] data, Camera camera) {
        if (resultHandler == null) return;

        try {
            Camera.Parameters parameters = camera.getParameters();
            Camera.Size size = parameters.getPreviewSize();
            int width = size.width;
            int height = size.height;

            if (DisplayUtils
                .getScreenOrientation(getContext()) == Configuration.ORIENTATION_PORTRAIT) {
                byte[] rotatedData = new byte[data.length];
                for (int y = 0; y < height; y++) {
                    for (int x = 0; x < width; x++)
                        rotatedData[x * height + height - y - 1] = data[x + y * width];
                }
                int tmp = width;
                width = height;
                height = tmp;
                data = rotatedData;
            }

            Result rawResult = null;
            PlanarYUVLuminanceSource source = buildLuminanceSource(data, width, height);

            if (source != null) {
                BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
                try {
                    rawResult = multiFormatReader.decodeWithState(bitmap);
                } catch (ReaderException | ArrayIndexOutOfBoundsException
                    | NullPointerException e) {
                    // continue
                } finally {
                    multiFormatReader.reset();
                }
            }

            final Result finalRawResult = rawResult;

            if (finalRawResult != null) {
                Handler handler = new Handler(Looper.getMainLooper());
                handler.post(() -> {
                    // Stopping the preview can take a little long.
                    // So we want to set result handler to null to discard subsequent calls to
                    // onPreviewFrame.
                    ResultHandler tmpResultHandler = resultHandler;
                    resultHandler = null;

                    stopCameraPreview();
                    if (tmpResultHandler != null) {
                        tmpResultHandler.handleResult(finalRawResult);
                    }
                });
            } else {
                camera.setOneShotPreviewCallback(this);
            }
        } catch (RuntimeException e) {
            // TODO: Terrible hack. It is possible that this method is invoked after camera is
            // released.
            Log.e(TAG, e.toString(), e);
        }
    }

    public void resumeCameraPreview(ResultHandler resultHandler) {
        this.resultHandler = resultHandler;
        super.resumeCameraPreview();
    }

    public PlanarYUVLuminanceSource buildLuminanceSource(byte[] data, int width, int height) {
        Rect rect = getFramingRectInPreview(width, height);
        if (rect == null) {
            return null;
        }
        // Go ahead and assume it's YUV rather than die.
        PlanarYUVLuminanceSource source = null;

        try {
            source = new PlanarYUVLuminanceSource(data, width, height, rect.left, rect.top,
                rect.width(), rect.height(), false);
        } catch (Exception ignored) {
        }

        return source;
    }

    public interface ResultHandler {

        void handleResult(Result rawResult);
    }

}