package br.com.miller.farmaciaatendente.utils.presenters;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import br.com.miller.farmaciaatendente.MainActivity;
import br.com.miller.farmaciaatendente.utils.tasks.PermissionsTask;

public class Permissions {

    private Activity act;
    private PermissionsTask.Presenter presenter;
    private String[] permissions;
    public static int pCode = 666;

    public Permissions(Activity act, MainActivity presenter){

        this.presenter = presenter;
        this.act = act;

        permissions = new String[]{
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_NETWORK_STATE,
                Manifest.permission.CALL_PHONE,
                Manifest.permission.INTERNET,
                Manifest.permission.CAMERA
        };

    }

    public void checkSelfPermission(){

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){

            boolean flag = false;

            for(String s : permissions)
                if(ActivityCompat.checkSelfPermission(act, s) != PackageManager.PERMISSION_GRANTED) {
                    flag = true;
                }

            if(flag) {
                ActivityCompat.requestPermissions(act, permissions, pCode);
            }else
                presenter.onPermissionOnSuccess();

        }else{
            presenter.onPermissionOnSuccess();
        }
    }
}
