package bookviewer.bookviewer.com.bookviewer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import bookviewer.bookviewer.com.bookviewer.Data.DataMgr;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
        startActivity(intent);


    }
}
