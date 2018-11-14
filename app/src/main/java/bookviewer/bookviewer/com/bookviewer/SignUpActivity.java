package bookviewer.bookviewer.com.bookviewer;

import android.content.Context;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import bookviewer.bookviewer.com.bookviewer.Data.DataMgr;

public class SignUpActivity extends AppCompatActivity {

    private EditText mNickName, mCode;
    private Button CheckBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mNickName = (EditText) findViewById(R.id.SignUp_EditText_NickName);
        final InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        ConstraintLayout ll = (ConstraintLayout) findViewById(R.id.linearLayout);
        ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //imm.hideSoftInputFromWindow(mMemo.getWindowToken(), 0);
                imm.hideSoftInputFromWindow(mNickName.getWindowToken(), 0);
            }
        });

        mCode = (EditText) findViewById(R.id.SignUp_EditText_Code);
        ll = (ConstraintLayout) findViewById(R.id.linearLayout);
        ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //imm.hideSoftInputFromWindow(mMemo.getWindowToken(), 0);
                imm.hideSoftInputFromWindow(mCode.getWindowToken(), 0);
            }
        });

        CheckBtn = (Button) findViewById(R.id.SignUp_Button);
        CheckBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String strNickName = mNickName.getText().toString();
                String strCode = mCode.getText().toString();

                // TODO 임시 현재 학교의 추천코드는 a 뿐임
                DataMgr.getInstance().myData.init("a", strNickName);
                DataMgr.getInstance().saveMyData(SignUpActivity.this);

               // strNickName = CommonFunc.getInstance().RemoveEmptyString(strNickName);

                    Intent intent = new Intent(SignUpActivity.this, MainViewActivity.class);
                    startActivity(intent);
                    finish();


            }
        });
    }
}
