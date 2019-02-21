package bookviewer.bookviewer.com.bookviewer;

import android.Manifest;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.admin.DeviceAdminReceiver;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Point;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.accessibility.AccessibilityManager;
import android.widget.Toast;

import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import java.security.Permission;
import java.util.List;

import bookviewer.bookviewer.com.bookviewer.Data.DataMgr;

public class LoginActivity extends AppCompatActivity {

    Intent intent;


    private Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        activity =  this;

        final Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        CommonFunc.getInstance().SetDisplayWidth(size.x);
        CommonFunc.getInstance().SetDisplayHeight(size.y);

        InitPermission();
    }



    private void InitPermission()
    {
        PermissionListener permissionlistener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {


                DataMgr.getInstance().loadLocalData(LoginActivity.this);
          /*    intent = new Intent(LoginActivity.this, SignUpActivity.class);
                finish();*/

                if(DataMgr.getInstance().myData.isJoin())
                {
                    intent = new Intent(LoginActivity.this, MainViewActivity.class);
                    finish();
                }
                else
                {
                    intent = new Intent(LoginActivity.this, SignUpActivity.class);
                    finish();
                }

                startActivity(intent);
            }

            @Override
            public void onPermissionDenied(List<String> deniedPermissions) {
              /*  DataMgr.getInstance().loadLocalData(LoginActivity.this);
                intent = new Intent(LoginActivity.this, MainViewActivity.class);
                finish();*/
            }
        };

        TedPermission.with(this)
                .setPermissionListener(permissionlistener)
                .setDeniedMessage("권한 설정이 거부되었습니다")
                .setPermissions(Manifest.permission.CAMERA, Manifest.permission.USE_FINGERPRINT)
                .check();

    }
}
