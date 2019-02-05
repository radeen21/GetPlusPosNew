package com.example.babartrihapsoro.getpluspos.helper;

//Author by Ignat.

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.PixelFormat;
import android.graphics.Point;
import android.hardware.Camera;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Display;
import android.view.Surface;
import android.view.WindowManager;

//import com.google.gson.Gson;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
//
//import okhttp3.OkHttpClient;
//import retrofit2.Retrofit;
//import retrofit2.converter.gson.GsonConverterFactory;

@SuppressWarnings("deprecation")
public class Fungsi extends AppCompatActivity
{
	//public static void setCameraParameter(Camera camera, int prevwidth, int prevheight, int picwidth, int picheight, String strOrie)
	public static void setCameraParameter(Camera camera, int picwidth, int picheight, String strOrie)
	{
		Camera.Parameters parameters = camera.getParameters();
		parameters.set("orientation", strOrie);
		parameters.set("jpeg-quality", 100);
		parameters.setPictureFormat(PixelFormat.JPEG);
		parameters.setPictureSize(picwidth, picheight);
		camera.setParameters(parameters);
	}

	public static void setCameraDisplayOrientation(Camera camera, Context context, int FacingCamera)
	{
		Display display = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
		int rotation = display.getRotation();
		int degrees = 0;

		switch (rotation)
		{
			case Surface.ROTATION_0:
				degrees = 0;
				break;
			case Surface.ROTATION_90:
				degrees = 90;
				break;
			case Surface.ROTATION_180:
				degrees = 180;
				break;
			case Surface.ROTATION_270:
				degrees = 270;
				break;
		}

		int result;
		Camera.CameraInfo camInfo = new Camera.CameraInfo();
		Camera.getCameraInfo(getFacingCameraId(FacingCamera), camInfo);

		if (camInfo.facing == Camera.CameraInfo.CAMERA_FACING_FRONT)
		{
			result = (camInfo.orientation + degrees) % 360;
			result = (360 - result) % 360;  // compensate the mirror
		}
		else
			result = (camInfo.orientation - degrees + 360) % 360;

		camera.setDisplayOrientation(result);
	}

	public static int getFacingCameraId(int FacingCamera)
	{
		int cameraId = -1;
		int numberOfCameras = Camera.getNumberOfCameras();

		for (int i =0; i<numberOfCameras; i++)
		{
			Camera.CameraInfo info = new Camera.CameraInfo();
			Camera.getCameraInfo(i, info);

			if (info.facing == FacingCamera)
			{
				cameraId = i;
				break;
			}
		}

		return cameraId;
	}

	public static Point getBestPreviewSize(Camera camera)
	{
		int MIN_PREV_PIXELS = 480 * 320; // normal screen
		int MAX_PREV_PIXELS = 1920 * 1080; // more than large/HD screen

		Camera.Parameters p = camera.getParameters();
		List<Camera.Size> lstSize = new ArrayList<Camera.Size>(p.getSupportedPreviewSizes());
		Collections.sort(lstSize, new Comparator<Camera.Size>()
		{
			@Override
			public int compare(Camera.Size a, Camera.Size b) {
				int aPixels = a.height * a.width;
				int bPixels = b.height * b.width;

				if (bPixels < aPixels)
					return -1;

				if (bPixels > aPixels)
					return 1;

				return 0;
			}
		});

		List<Point> lstHasil = new ArrayList<Point>();
		lstHasil.clear();

		for (Camera.Size size : lstSize)
		{
			int realWidth = size.width;
			int realHeight = size.height;
			int pixels = realWidth * realHeight;

			if(pixels < MIN_PREV_PIXELS || pixels > MAX_PREV_PIXELS)
				continue;

			Point pTemp = new Point(realWidth, realHeight);
			lstHasil.add(pTemp);
			Log.d("[fungsi]", "Preview Screen -> " + pTemp);
		}

		return lstHasil.get(0);
	}

	public static int isNetworkAvailable(Context context)
	{
		ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

//		NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
//		if (null != activeNetwork)
//		{
//			if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI)
//				return FixValue.TYPE_WIFI;
//
//			if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE)
//				return FixValue.TYPE_MOBILE;
//		}

		return FixValue.TYPE_NONE;
	}

//	public static DataLink BindingData()
//	{
//		OkHttpClient okClient = new OkHttpClient();
//
//		okClient.newBuilder().connectTimeout(FixValue.TimeoutConnection, TimeUnit.SECONDS).
//				readTimeout(FixValue.TimeoutConnection, TimeUnit.SECONDS).
//				writeTimeout(FixValue.TimeoutConnection, TimeUnit.SECONDS).build();
//
//		Retrofit retBindingData = new Retrofit.Builder().baseUrl(FixValue.BASE_URL).
//				addConverterFactory(GsonConverterFactory.create()).
//				client(okClient).build();
//
//		return retBindingData.create(DataLink.class);
//	}

//	public static POSLink BindingPOS()
//	{
//		OkHttpClient okClient = new OkHttpClient();
//
//		okClient.newBuilder().connectTimeout(FixValue.TimeoutConnection, TimeUnit.SECONDS).
//			readTimeout(FixValue.TimeoutConnection, TimeUnit.SECONDS).
//			writeTimeout(FixValue.TimeoutConnection, TimeUnit.SECONDS).build();
//
//		Retrofit retBindingPOS = new Retrofit.Builder().baseUrl(FixValue.POS_URL).
//			addConverterFactory(GsonConverterFactory.create()).
//			client(okClient).build();
//
//		return retBindingPOS.create(POSLink.class);
//	}

	public static boolean CheckPermission(Activity activity, Context context)
	{
		List<String> listPermissionsNeeded = new ArrayList<>();

		int result = ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE);
		if (result != PackageManager.PERMISSION_GRANTED)
			listPermissionsNeeded.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);

		result = ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE);
		if (result != PackageManager.PERMISSION_GRANTED)
			listPermissionsNeeded.add(Manifest.permission.READ_EXTERNAL_STORAGE);

		result = ContextCompat.checkSelfPermission(context, Manifest.permission.INTERNET);
		if (result != PackageManager.PERMISSION_GRANTED)
			listPermissionsNeeded.add(Manifest.permission.INTERNET);

		result = ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_NETWORK_STATE);
		if (result != PackageManager.PERMISSION_GRANTED)
			listPermissionsNeeded.add(Manifest.permission.ACCESS_NETWORK_STATE);

		result = ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION);
		if (result != PackageManager.PERMISSION_GRANTED)
			listPermissionsNeeded.add(Manifest.permission.ACCESS_COARSE_LOCATION);

		result = ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION);
		if (result != PackageManager.PERMISSION_GRANTED)
			listPermissionsNeeded.add(Manifest.permission.ACCESS_FINE_LOCATION);

		result = ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_LOCATION_EXTRA_COMMANDS);
		if (result != PackageManager.PERMISSION_GRANTED)
			listPermissionsNeeded.add(Manifest.permission.ACCESS_LOCATION_EXTRA_COMMANDS);

		result = ContextCompat.checkSelfPermission(context, Manifest.permission.LOCATION_HARDWARE);
		if (result != PackageManager.PERMISSION_GRANTED)
			listPermissionsNeeded.add(Manifest.permission.LOCATION_HARDWARE);

		result = ContextCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE);
		if (result != PackageManager.PERMISSION_GRANTED)
			listPermissionsNeeded.add(Manifest.permission.READ_PHONE_STATE);

		result = ContextCompat.checkSelfPermission(context, Manifest.permission.VIBRATE);
		if (result != PackageManager.PERMISSION_GRANTED)
			listPermissionsNeeded.add(Manifest.permission.VIBRATE);

		result = ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA);
		if (result != PackageManager.PERMISSION_GRANTED)
			listPermissionsNeeded.add(Manifest.permission.CAMERA);

		result = ContextCompat.checkSelfPermission(context, Manifest.permission.BLUETOOTH);
		if (result != PackageManager.PERMISSION_GRANTED)
			listPermissionsNeeded.add(Manifest.permission.BLUETOOTH);

		result = ContextCompat.checkSelfPermission(context, Manifest.permission.BLUETOOTH_ADMIN);
		if (result != PackageManager.PERMISSION_GRANTED)
			listPermissionsNeeded.add(Manifest.permission.BLUETOOTH_ADMIN);

		if (!listPermissionsNeeded.isEmpty())
		{
			ActivityCompat.requestPermissions(activity, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), 1);
			return true;
		}

		return true;
	}

	public static boolean getBooleanFromSharedPref(final Context context, String key)
	{
		SharedPreferences prefs = context.getSharedPreferences(FixValue.strNamePref,  Context.MODE_PRIVATE);
		return prefs.getBoolean(key, true);
	}

	public static String FormatDesimal(Integer intSource)
	{
		DecimalFormat fmt = new DecimalFormat();
		DecimalFormatSymbols fmts = new DecimalFormatSymbols();

		fmts.setGroupingSeparator('.');
		fmt.setGroupingSize(3);
		fmt.setGroupingUsed(true);
		fmt.setDecimalFormatSymbols(fmts);
		return fmt.format(intSource);
	}

	public static String getStringFromSharedPref(final Context context, String key)
	{
		SharedPreferences prefs = context.getSharedPreferences(FixValue.strNamePref,  Context.MODE_PRIVATE);
		return prefs.getString(key, "");
	}

	public static int getIntFromSharedPref(final Context context, String key)
	{
		SharedPreferences prefs = context.getSharedPreferences(FixValue.strNamePref,  Context.MODE_PRIVATE);
		return prefs.getInt(key, 0);
	}

	public static void storeToSharedPref(final Context context, String value, String key)
	{
		SharedPreferences.Editor editor = context.getSharedPreferences(FixValue.strNamePref, Context.MODE_PRIVATE).edit();
		editor.putString(key, value).commit();
	}

	public static void storeToSharedPref(final Context context, int value, String key)
	{
		SharedPreferences.Editor editor = context.getSharedPreferences(FixValue.strNamePref, Context.MODE_PRIVATE).edit();
		editor.putInt(key, value).commit();
	}

	public static void storeObjectToSharedPref(final Context context, Object object, String key)
	{
		SharedPreferences sharedPreferences = context.getSharedPreferences(FixValue.strNamePref, 0);
		SharedPreferences.Editor sharedPreferencesEditor = sharedPreferences.edit();
//		final Gson gson = new Gson();
//		String serializedObject = gson.toJson(object);
//		sharedPreferencesEditor.putString(key, serializedObject);
		sharedPreferencesEditor.apply();
	}

	public static <GenericClass> GenericClass getObjectFromSharedPref(Context context, Class<GenericClass> classType, String key)
	{
		SharedPreferences sharedPreferences = context.getSharedPreferences(FixValue.strNamePref, 0);
		if (sharedPreferences.contains(key))
		{
//			final Gson gson = new Gson();
//			return gson.fromJson(sharedPreferences.getString(key, ""), classType);
		}

		return null;
	}

	public static String getDate(String tgl, String DateTimeFormat, String DateFormat) {
		Date tanggal;
		String dateConvert = null;
		SimpleDateFormat form = new SimpleDateFormat(DateTimeFormat, Locale.US);

		try {
			java.text.DateFormat df = new SimpleDateFormat(DateFormat, Locale.US);
			tanggal = form.parse(tgl);
			dateConvert = df.format(tanggal);

		} catch (ParseException e) {
			e.printStackTrace();
		}

		return dateConvert;
	}

	public static String getTime(String time, String DateTimeFormat, String TimeFormat) {
		Date tanggal;
		String dateConvert = null;
		SimpleDateFormat form = new SimpleDateFormat(DateTimeFormat, Locale.US);

		try {
			tanggal = form.parse(time);
			DateFormat tf = new SimpleDateFormat(TimeFormat, Locale.US);
			dateConvert = tf.format(tanggal);

		} catch (ParseException e) {
			e.printStackTrace();
		}
		return dateConvert;
	}
}
