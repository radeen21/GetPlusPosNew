package com.example.babartrihapsoro.getpluspos.helper;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by randiwaranugraha on 1/19/17.
 */

public class Permission {

    private static List<String> pendingPermissions = new ArrayList<>();

    private final int REQUEST_CODE = RandomInteger.next();

    private Activity activity;

    private List<String> permissions;

    private List<PermissionReport> reports;

    private PermissionListener permissionListener;

    private PermissionsListener permissionsListener;

    private Permission(Builder builder) {
        activity = builder.activity;
        permissions = builder.permissions;
        permissionListener = builder.permissionListener;
        permissionsListener = builder.permissionsListener;
    }

    public static PermissionReport checkPermission(Context context,
        @ManifestPermission String permission) {
        int grantResult = ActivityCompat.checkSelfPermission(context, permission);
        return new PermissionReport(permission, grantResult);
    }

    public static boolean hasPermission(Context context, @ManifestPermission String permission) {
        return checkPermission(context, permission).isGranted();
    }

    private static void addPendingPermission(String permission) {
        if (!hasPendingPermission(permission)) {
            pendingPermissions.add(permission);
        }
    }

    private static void addPendingPermissions(List<String> permissions) {
        for (String permission : permissions) {
            addPendingPermission(permission);
        }
    }

    private static void removePendingPermission(String permission) {
        if (hasPendingPermission(permission)) {
            pendingPermissions.remove(permission);
        }
    }

    private static void removePendingPermissions(List<String> permissions) {
        for (String permission : permissions) {
            removePendingPermission(permission);
        }
    }

    private static boolean hasPendingPermission(String permission) {
        for (String pendingPermission : pendingPermissions) {
            if (pendingPermission.equals(permission)) {
                return true;
            }
        }

        return false;
    }

    private static boolean hasPendingPermissions(List<String> permissions) {
        for (String permission : permissions) {
            if (hasPendingPermission(permission)) {
                return true;
            }
        }

        return false;
    }

    public void check() {
        if (activity == null) {
            throw new IllegalStateException("A non-null activity must be provided");
        }

        List<String> requestedPermissions = new ArrayList<>();
        reports = new ArrayList<>();

        for (String permission : permissions) {
            PermissionReport report = checkPermission(activity, permission);
            if (report.isGranted()) {
                reports.add(report);
            } else {
                requestedPermissions.add(permission);
            }
        }

        if (requestedPermissions.size() > 0 && !hasPendingPermissions(permissions)) {
            addPendingPermissions(permissions);

            ActivityCompat.requestPermissions(activity,
                requestedPermissions.toArray(new String[requestedPermissions.size()]),
                REQUEST_CODE);
        } else {
            reporting(reports);
        }
    }

    private void reporting(List<PermissionReport> reports) {
        for (PermissionReport report : reports) {
            notifyListener(report);
        }
        notifyMultipleListener(reports);
    }

    private void notifyListener(PermissionReport report) {
        if (report != null && permissionListener != null) {
            if (report.isGranted()) {
                permissionListener.onPermissionGranted(report);
            } else {
                permissionListener.onPermissionDenied(report);
            }
        }
    }

    private void notifyMultipleListener(List<PermissionReport> reports) {
        if (reports != null && reports.size() > 0 && permissionsListener != null) {
            permissionsListener.onPermissionsChecked(reports);
        }
    }

    public boolean onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
        @NonNull int[] grantResults) {
            reports = new ArrayList<>();
        for (int i = 0; i < permissions.length; i++) {
            String systemPermission = permissions[i];
            for (String requestPermission : this.permissions) {
                if (systemPermission.equalsIgnoreCase(requestPermission)) {
                    removePendingPermission(systemPermission);

                    PermissionReport report = new PermissionReport(systemPermission,
                        grantResults[i]);
                    if(report != null) {
                        reports.add(report);
                        notifyListener(report);
                    }
                }
            }
        }

        if (reports.size() > 0) {
            notifyMultipleListener(reports);
            return true;
        }

        return false;
    }

    public interface PermissionListener {

        void onPermissionGranted(PermissionReport report);

        void onPermissionDenied(PermissionReport report);

    }

    public interface PermissionsListener {

        void onPermissionsChecked(List<PermissionReport> reports);

    }

    public static class Builder {

        private Activity activity;

        private List<String> permissions;

        private PermissionListener permissionListener;

        private PermissionsListener permissionsListener;

        public Builder(Activity activity) {
            this.activity = activity;
            permissions = new ArrayList<>();
        }

        /**
         * Add single permission request
         */
        public Builder permission(@ManifestPermission String manifestPermission) {
            permissions.add(manifestPermission);
            return this;
        }

        /**
         * Add multiple permission request
         */
        public Builder permission(@ManifestPermission String... manifestPermissions) {
            Collections.addAll(permissions, manifestPermissions);
            return this;
        }

        /**
         * To handle single permission listener
         */
        public Builder listener(PermissionListener permissionListener) {
            this.permissionListener = permissionListener;
            return this;
        }

        /**
         * To handle multiple permission listener
         */
        public Builder listener(PermissionsListener permissionsListener) {
            this.permissionsListener = permissionsListener;
            return this;
        }

        public Permission build() {
            return new Permission(this);
        }
    }

    public static class PermissionReport {

        private final String permissionName;

        private int grantResult = PackageManager.PERMISSION_DENIED;

        private PermissionReport(String permissionName, int grantResult) {
            this.permissionName = permissionName;
            this.grantResult = grantResult;
        }

        public String getPermissionName() {
            return permissionName;
        }

        public boolean isGranted() {
            return grantResult == PackageManager.PERMISSION_GRANTED;
        }
    }
}