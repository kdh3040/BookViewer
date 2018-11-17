package bookviewer.bookviewer.com.bookviewer;

import android.accessibilityservice.AccessibilityServiceInfo;
import android.app.ActivityManager;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.provider.Settings;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.accessibility.AccessibilityManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

import bookviewer.bookviewer.com.bookviewer.Data.DataMgr;
import bookviewer.bookviewer.com.bookviewer.Receiver.MyDeviceAdminReceiver;

public class SignUpActivity extends AppCompatActivity {

    private EditText mNickName, mCode, mChildCode;
    private TextView mModeChangeText;
    private boolean mChildMode = true;
    private ConstraintLayout mChildModeLayout, mParentsModeLayout;
    private Button mSignUPBtn, mLoginBtn;
    DevicePolicyManager mDPM;
    private static ActivityManager am;

    private void SetPermission()
    {
//        am = (ActivityManager)getApplicationContext().getSystemService(ACTIVITY_SERVICE);
//        final ComponentName comp = new ComponentName(this, MyDeviceAdminReceiver.class);
//        mDPM = (DevicePolicyManager) this
//                .getSystemService(Context.DEVICE_POLICY_SERVICE);
//
//        if( !mDPM.isAdminActive(comp) ){
//            Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
//            intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, comp);
//            intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION, "message string");
//            startActivityForResult(intent, 1);
//        }else{
//            mDPM.removeActiveAdmin(comp);
//            //mDPM.lockNow();
//            //InitPermission();
//        }
//
//        if(!checkAccessibilityPermissions()) {
//            setAccessibilityPermissions();
//        }
//        else
//        {
//
//        }
    }

    public boolean checkAccessibilityPermissions() {
        AccessibilityManager accessibilityManager = (AccessibilityManager) getSystemService(Context.ACCESSIBILITY_SERVICE);
        // getEnabledAccessibilityServiceList는 현재 접근성 권한을 가진 리스트를 가져오게 된다
        List<AccessibilityServiceInfo> list = accessibilityManager.getEnabledAccessibilityServiceList(AccessibilityServiceInfo.DEFAULT);

        for (int i = 0; i < list.size(); i++) {
            AccessibilityServiceInfo info = list.get(i);

            // 접근성 권한을 가진 앱의 패키지 네임과 패키지 네임이 같으면 현재앱이 접근성 권한을 가지고 있다고 판단함
            if (info.getResolveInfo().serviceInfo.packageName.equals(getApplication().getPackageName())) {
                return true;
            }
        }
        return false;
    }

    // 접근성 설정화면으로 넘겨주는 부분
    public void setAccessibilityPermissions() {
        AlertDialog.Builder gsDialog = new AlertDialog.Builder(this);
        gsDialog.setTitle("접근성 권한 설정");
        gsDialog.setMessage("접근성 권한을 필요로 합니다");
        gsDialog.setPositiveButton("확인", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // 설정화면으로 보내는 부분
                startActivity(new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS));
                //   ActivityCompat.finishAffinity(activity);
                return;
            }
        }).create().show();
    }

    @Override
    public void onResume() {
        super.onResume();  // Always call the superclass method first
    /*    Intent intent = new Intent(SignUpActivity.this, MainViewActivity.class);
        startActivity(intent);
        finish();*/
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mNickName = (EditText) findViewById(R.id.nickname_str);
        mCode = findViewById(R.id.schoolcode_str);
        mChildCode = findViewById(R.id.child_code_str);
        mModeChangeText = findViewById(R.id.mode_change);
        mChildMode = true;
        mChildModeLayout = findViewById(R.id.student_mode);
        mParentsModeLayout = findViewById(R.id.parents_mode);
        mSignUPBtn = findViewById(R.id.SignUp_Button);
        mLoginBtn = findViewById(R.id.Login_Button);


        final InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        ConstraintLayout ll = (ConstraintLayout) findViewById(R.id.linearLayout);
        ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //imm.hideSoftInputFromWindow(mMemo.getWindowToken(), 0);
                imm.hideSoftInputFromWindow(mNickName.getWindowToken(), 0);
                imm.hideSoftInputFromWindow(mCode.getWindowToken(), 0);
                imm.hideSoftInputFromWindow(mChildCode.getWindowToken(), 0);
            }
        });

        mSignUPBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String strNickName = mNickName.getText().toString();
                String strCode = mCode.getText().toString();

                // TODO 임시 현재 학교의 추천코드는 a 뿐임
                // TODO 파베에서 유저 인덱스를 받아야함
                DataMgr.getInstance().myData.init(1,"a", strNickName);
                DataMgr.getInstance().initMyData(DataMgr.getInstance().myData.userData.userIdx);
                DataMgr.getInstance().saveLocalData(SignUpActivity.this);

                if(strCode.equals(""))
                {
                    Intent intent = new Intent(SignUpActivity.this, MainViewActivity.class);
                    startActivity(intent);
                    finish();
                }
                else
                {
                    SetPermission();
                }
            }
        });

        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // TODO 학부모 모드 진입

                String strNickName = mNickName.getText().toString();
                String strCode = mCode.getText().toString();

                // TODO 임시 현재 학교의 추천코드는 a 뿐임
                // TODO 파베에서 유저 인덱스를 받아야함
                DataMgr.getInstance().myData.init(1,"a", strNickName);
                DataMgr.getInstance().initMyData(DataMgr.getInstance().myData.userData.userIdx);
                DataMgr.getInstance().saveLocalData(SignUpActivity.this);

                if(strCode.equals(""))
                {
                    Intent intent = new Intent(SignUpActivity.this, MainViewActivity.class);
                    startActivity(intent);
                    finish();
                }
                else
                {
                    SetPermission();
                }
            }
        });

        mModeChangeText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mChildMode = !mChildMode;
                refreshModeChange();
            }
        });


        refreshModeChange();
    }

    private void refreshModeChange()
    {
        if(mChildMode)
        {
            mChildModeLayout.setVisibility(View.VISIBLE);
            mParentsModeLayout.setVisibility(View.INVISIBLE);

            mModeChangeText.setText("학부모 이신가요?");
        }
        else
        {
            mChildModeLayout.setVisibility(View.INVISIBLE);
            mParentsModeLayout.setVisibility(View.VISIBLE);

            mModeChangeText.setText("학생 이신가요?");
        }
    }
}
