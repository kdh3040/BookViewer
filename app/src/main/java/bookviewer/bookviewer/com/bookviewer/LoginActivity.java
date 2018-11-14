package bookviewer.bookviewer.com.bookviewer;

import android.content.Intent;
import android.graphics.Point;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;

import bookviewer.bookviewer.com.bookviewer.Data.DataMgr;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        CommonFunc.getInstance().SetDisplayWidth(size.x);
        CommonFunc.getInstance().SetDisplayHeight(size.y);

        DataMgr.getInstance().loadLocalData(LoginActivity.this);

        Intent intent;
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
}
