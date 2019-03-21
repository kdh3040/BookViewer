package ttuktak.bookviewer.com.bookviewer;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import ttuktak.bookviewer.com.bookviewer.View.BookBoard.BookBoardView;
import ttuktak.bookviewer.com.bookviewer.View.Curriculum.BookListAdapter;
import ttuktak.bookviewer.com.bookviewer.View.UserInfo.UserInfoView;

public class MainViewActivity extends AppCompatActivity {

    private TextView mTextMessage;
    private  RecyclerView BookRecyclerView;
    private BookListAdapter BookListViewAdapter;

    public static android.support.v4.app.FragmentManager mFragmentMng;
    private CurriculumView CurriculumFragment = new CurriculumView();
    private BookViewerView BookViewFragment = new BookViewerView();
    private BookBoardView BookBoardFragment = new BookBoardView();
    private UserInfoView UserInfoFragment = new UserInfoView();
    //private BookViewerView BookViewFragment;// = HomeFragment.getInstance();

    public Toolbar TopBar;

    private BottomNavigationView navigation, navigation_viewer;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    TopBar.setTitle(R.string.Main_Bottom_Curriculum);
                    getSupportFragmentManager().beginTransaction().replace(R.id.frag_container, CurriculumFragment, "Curriculum").commit();
                    return true;
                case R.id.navigation_bookView:
                    navigation.setSelectedItemId(R.id.navigation_home);
                    Intent intent = new Intent(MainViewActivity.this, ViewActivity.class);
                    intent.putExtra("Title", getString(R.string.book_2));
                    intent.putExtra("Path", "Book_1.pdf");
                    startActivity(intent);

             /*       ArrayList<Integer> recentBookList = DataMgr.getInstance().getRecentBookLocalData();
                    BookData data = DataMgr.getInstance().myData.getBookData(recentBookList.get(0));
                    Tobbar.setTitle(data.bookName);

                    getSupportFragmentManager().beginTransaction().replace(R.id.frag_container, BookViewFragment, "BookViewer").commit();*/
                    return true;
                case R.id.navigation_bookBoard:
                    TopBar.setTitle(R.string.Main_Bottom_Board);
                    getSupportFragmentManager().beginTransaction().replace(R.id.frag_container, BookBoardFragment, "BookBoard").commit();
                    return true;
                case R.id.navigation_userInfo:
                    TopBar.setTitle(R.string.Main_Bottom_UserInfo);
                    getSupportFragmentManager().beginTransaction().replace(R.id.frag_container, UserInfoFragment, "UserInfo").commit();
                    return true;


                case R.id.navigation_BookViewer_home:
                  //  ViewBottomNavigation(true);
                    getSupportFragmentManager().beginTransaction().replace(R.id.frag_container, CurriculumFragment, "Curriculum").commit();
                    return true;

                case R.id.navigation_BookViewer_Memo:

                    return true;

                case R.id.navigation_BookViewer_Quiz:

                    return true;

                case R.id.navigation_BookViewer_Setting:

                    return true;

            }
            return false;
        }
    };

    private void ViewBottomNavigation(boolean bVisible)
    {
        if(bVisible)
        {
            navigation.setVisibility(View.VISIBLE);
            navigation_viewer.setVisibility(View.INVISIBLE);
        }
        else
        {
            navigation.setVisibility(View.INVISIBLE);
            navigation_viewer.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:{ //toolbar의 back키 눌렀을 때 동작
                finish();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_view);

        mFragmentMng = getSupportFragmentManager();

        TopBar = (Toolbar) findViewById(R.id.toolbar);
        TopBar.setTitle(R.string.Main_Bottom_Curriculum);

        setSupportActionBar(TopBar);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (CurriculumFragment == null)
            CurriculumFragment = new CurriculumView();

        //getSupportFragmentManager().beginTransaction().replace(R.id.frag_container, UserInfoFragment, "UserInfo").commit();

        getSupportFragmentManager().beginTransaction().replace(R.id.frag_container, CurriculumFragment, "Curriculum").commit();


   /*     BookRecyclerView = (RecyclerView)findViewById(R.id.recyclerview_booklist);
        BookRecyclerView.setHasFixedSize(true);
        BookRecyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 3));

        BookListViewAdapter = new BookListAdapter(MainViewActivity.this);
        BookRecyclerView.setAdapter(BookListViewAdapter);*/

        navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        navigation_viewer = (BottomNavigationView) findViewById(R.id.navigation_Viewer);
        navigation_viewer.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);


    }

}
