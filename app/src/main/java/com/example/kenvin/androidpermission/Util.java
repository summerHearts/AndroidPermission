package com.example.kenvin.androidpermission;

import android.Manifest;
import android.util.Log;

import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * Created by Kenvin on 2017/11/1.
 */

public class Util {

    public static void requestPermission(){
        BaseActivity.requestRunTimePermission(new String[]{Manifest.permission.CALL_PHONE, Manifest.permission.WRITE_EXTERNAL_STORAGE}
                , new PermissionListener() {
                    @Override
                    public void onGranted() {
                        Log.i(TAG," 所有权限授权成功 ");
                    }

                    @Override
                    public void onGranted(List<String> grantedPermission) {

                    }

                    @Override
                    public void onDenied(List<String> deniedPermission) {

                    }
                });
    }

}
