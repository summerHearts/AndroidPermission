package com.example.kenvin.androidpermission;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        requestPermission();
//        requestPermissions();
//        requestPermissionFromBaseActivity();

        requestPermissionFromUtil();
    }


    /**
     * 请求授权
     */
    private void requestPermission(){

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){ //表示未授权时
            //进行授权
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.CALL_PHONE},1);
        }else{
            //调用打电话的方法
            makeCall();
        }
    }

    /**
     * 当有多个权限需要申请的时候
     * 这里以打电话和SD卡读写权限为例
     */
    private  void requestPermissions(){

            List<String> permissionList = new ArrayList<>();
            if (ContextCompat.checkSelfPermission(this,Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){
                permissionList.add(Manifest.permission.CALL_PHONE);
            }

            if (ContextCompat.checkSelfPermission(this,Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
                permissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            }

            if (!permissionList.isEmpty()){  //申请的集合不为空时，表示有需要申请的权限
                ActivityCompat.requestPermissions(this,permissionList.toArray(new String[permissionList.size()]),1);
            }else { //所有的权限都已经授权过了

            }
    }


    private void requestPermissionFromUtil(){
        Util.requestPermission();
    }

    private void requestPermissionFromBaseActivity(){
        requestRunTimePermission(new String[]{Manifest.permission.CALL_PHONE, Manifest.permission.WRITE_EXTERNAL_STORAGE}
                , new PermissionListener() {
                    @Override
                    public void onGranted() {  //所有权限授权成功
                        Log.i(TAG," 所有权限授权成功 ");
                    }

                    @Override
                    public void onGranted(List<String> grantedPermission) { //授权失败权限集合
                        Log.i(TAG," 授权失败权限集合 ");

                    }

                    @Override
                    public void onDenied(List<String> deniedPermission) { //授权成功权限集合
                        Log.i(TAG,"授权成功权限集合");
                    }
                });
    }



    /**
     * 权限申请返回结果
     * @param requestCode 请求码
     * @param permissions 权限数组
     * @param grantResults  申请结果数组，里面都是int类型的数
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){ //同意权限申请
                    makeCall();
                }else if(grantResults.length > 0 && grantResults[1] == PackageManager.PERMISSION_GRANTED) { //拒绝权限申请
                    Toast.makeText(this,"SD卡的写权限已经开启", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(this,"权限被拒绝了", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }


    /**
     * 打电话方法
     */
    private void makeCall(){
        try {
            Intent intent = new Intent(Intent.ACTION_CALL);
            intent.setData(Uri.parse("tel://123456789"));
            startActivity(intent);
        }catch (SecurityException e){
            e.printStackTrace();
        }
    }

}
