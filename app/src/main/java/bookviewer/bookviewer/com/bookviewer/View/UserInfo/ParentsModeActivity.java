package bookviewer.bookviewer.com.bookviewer.View.UserInfo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import bookviewer.bookviewer.com.bookviewer.Data.DataMgr;
import bookviewer.bookviewer.com.bookviewer.R;

public class ParentsModeActivity extends AppCompatActivity {

    ImageView BackBtn;
    RecyclerView CurriculumRecyclerView, BookBoardRecyclerView;
    CurriculumProgressAdapter CurriculumAdapter;
    ParentsBookBoardAdapter ParentsBookBoardAdapter;


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

        CurriculumRecyclerView = (RecyclerView)findViewById(R.id.curriculum_progress_list) ;
        CurriculumRecyclerView.setHasFixedSize(true);
        CurriculumRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));

        CurriculumAdapter = new CurriculumProgressAdapter(ParentsModeActivity.this);
        CurriculumRecyclerView.setAdapter(CurriculumAdapter);

        BookBoardRecyclerView = (RecyclerView)findViewById(R.id.book_board_list) ;
        BookBoardRecyclerView.setHasFixedSize(true);
        BookBoardRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        ParentsBookBoardAdapter = new ParentsBookBoardAdapter(ParentsModeActivity.this);
        BookBoardRecyclerView.setAdapter(ParentsBookBoardAdapter);



       ////./CurriculumRecyclerView.scrollToPosition(0);
    }


}