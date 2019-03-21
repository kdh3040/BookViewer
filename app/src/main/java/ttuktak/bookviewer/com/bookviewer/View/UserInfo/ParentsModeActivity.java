package ttuktak.bookviewer.com.bookviewer.View.UserInfo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import ttuktak.bookviewer.com.bookviewer.R;

public class ParentsModeActivity extends AppCompatActivity {

    RecyclerView CurriculumRecyclerView, BookBoardRecyclerView;
    CurriculumProgressAdapter CurriculumAdapter;
    ParentsBookBoardAdapter ParentsBookBoardAdapter;
    public Toolbar TopBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parents_mode);

        TopBar = (Toolbar) findViewById(R.id.toolbar);
        TopBar.setTitle(R.string.Parents_Mode_Title);
        setSupportActionBar(TopBar);

        TopBar.setNavigationIcon(R.drawable.back_arrow);

        TopBar.setNavigationOnClickListener(new View.OnClickListener() {
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
        BookBoardRecyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL));

        ParentsBookBoardAdapter = new ParentsBookBoardAdapter(ParentsModeActivity.this);
        BookBoardRecyclerView.setAdapter(ParentsBookBoardAdapter);



       ////./CurriculumRecyclerView.scrollToPosition(0);
    }


}