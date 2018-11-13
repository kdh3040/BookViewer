package bookviewer.bookviewer.com.bookviewer;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import bookviewer.bookviewer.com.bookviewer.Data.DataMgr;

public class ParentsModeActivity extends AppCompatActivity {

    ImageView BackBtn;
    TextView ChildName, ChildSchoolName;
    RecyclerView CurriculumRecyclerView;
    SimpleCurriculumAdapter SimpleCurriculumAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parents_mode);

        BackBtn = findViewById(R.id.parent_mode_back);
        BackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        ChildName = findViewById(R.id.child_profile_nickname);
        ChildName.setText(DataMgr.getInstance().myData.nickName);

        ChildSchoolName = findViewById(R.id.child_profile_schoolname);
        ChildSchoolName.setText(DataMgr.getInstance().myData.schoolName);

        CurriculumRecyclerView = (RecyclerView)findViewById(R.id.recyclerview_curriculumlist) ;
        CurriculumRecyclerView.setHasFixedSize(true);
        CurriculumRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        SimpleCurriculumAdapter = new SimpleCurriculumAdapter(ParentsModeActivity.this);
        CurriculumRecyclerView.setAdapter(SimpleCurriculumAdapter);
    }


}