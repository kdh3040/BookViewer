package bookviewer.bookviewer.com.bookviewer;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.widget.TextView;

public class MainViewActivity extends AppCompatActivity {

    private TextView mTextMessage;
    private  RecyclerView BookRecyclerView;
    private  BookListAdapter BookListViewAdapter;

    public static android.support.v4.app.FragmentManager mFragmentMng;
    private CurriculumView CurriculumFragment = new CurriculumView();
    private BookViewerView BookViewFragment = new BookViewerView();
    private BookBoardView BookBoardFragment = new BookBoardView();
    private UserInfoView UserInfoFragment = new UserInfoView();
    //private BookViewerView BookViewFragment;// = HomeFragment.getInstance();


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    getSupportFragmentManager().beginTransaction().replace(R.id.frag_container, CurriculumFragment, "Curriculum").commit();
                    return true;
                case R.id.navigation_bookView:
                    getSupportFragmentManager().beginTransaction().replace(R.id.frag_container, BookViewFragment, "BookViewer").commit();
                    return true;
                case R.id.navigation_bookBoard:
                    getSupportFragmentManager().beginTransaction().replace(R.id.frag_container, BookBoardFragment, "BookBoard").commit();
                    return true;
                case R.id.navigation_userInfo:
                    getSupportFragmentManager().beginTransaction().replace(R.id.frag_container, UserInfoFragment, "UserInfo").commit();
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_view);

        mFragmentMng = getSupportFragmentManager();

        if (CurriculumFragment == null)
            CurriculumFragment = new CurriculumView();

        getSupportFragmentManager().beginTransaction().replace(R.id.frag_container, CurriculumFragment, "Curriculum").commit();


   /*     BookRecyclerView = (RecyclerView)findViewById(R.id.recyclerview_booklist);
        BookRecyclerView.setHasFixedSize(true);
        BookRecyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 3));

        BookListViewAdapter = new BookListAdapter(MainViewActivity.this);
        BookRecyclerView.setAdapter(BookListViewAdapter);*/

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

}
