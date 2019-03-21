package ttuktak.bookviewer.com.bookviewer;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Point;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;

import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import java.util.List;

import ttuktak.bookviewer.com.bookviewer.Data.DataMgr;

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
                int userIdx = DataMgr.getInstance().loadLocalUserData(LoginActivity.this);

          /*    intent = new Intent(LoginActivity.this, SignUpActivity.class);
                finish();*/

                if(userIdx != 0)
                {
                    DataMgr.getInstance().initMyData(userIdx);
                    DataMgr.getInstance().loadLocalAllData(LoginActivity.this);

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
                // TODO userIdx 가 0이면 어쩌지??
                int userIdx = DataMgr.getInstance().loadLocalUserData(LoginActivity.this);
                DataMgr.getInstance().initMyData(userIdx);
                DataMgr.getInstance().loadLocalAllData(LoginActivity.this);

                intent = new Intent(LoginActivity.this, MainViewActivity.class);
                finish();
            }
        };

        TedPermission.with(this)
                .setPermissionListener(permissionlistener)
                .setDeniedMessage("권한 설정이 거부되었습니다")
                .setPermissions(Manifest.permission.CAMERA, Manifest.permission.USE_FINGERPRINT)
                .check();

    }
}
