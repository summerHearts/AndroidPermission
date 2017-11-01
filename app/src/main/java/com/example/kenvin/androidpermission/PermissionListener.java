package com.example.kenvin.androidpermission;

import java.util.List;

/**
 * Created by Kenvin on 2017/11/1.
 */

public interface PermissionListener {
    //授权成功
    void onGranted();

    //授权部分
    void onGranted(List<String> grantedPermission);

    //拒绝授权
    void onDenied(List<String> deniedPermission);

}
