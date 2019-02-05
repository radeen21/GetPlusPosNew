package com.example.babartrihapsoro.getpluspos.helper;

import android.Manifest;
import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by randiwaranugraha on 1/20/17.
 */
@StringDef({ManifestPermission.CALL_PHONE, ManifestPermission.WRITE_EXTERNAL_STORAGE,
    ManifestPermission.READ_EXTERNAL_STORAGE, ManifestPermission.CAMERA, ManifestPermission
    .READ_CONTACTS, ManifestPermission.RECEIVE_SMS})
@Retention(RetentionPolicy.SOURCE)
public @interface ManifestPermission {

    String CALL_PHONE = Manifest.permission.CALL_PHONE;

    String WRITE_EXTERNAL_STORAGE = Manifest.permission.WRITE_EXTERNAL_STORAGE;

    String READ_EXTERNAL_STORAGE = Manifest.permission.READ_EXTERNAL_STORAGE;

    String CAMERA = Manifest.permission.CAMERA;

    String READ_CONTACTS = Manifest.permission.READ_CONTACTS;

    String RECEIVE_SMS = Manifest.permission.RECEIVE_SMS;

}