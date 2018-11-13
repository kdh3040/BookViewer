package bookviewer.bookviewer.com.bookviewer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import bookviewer.bookviewer.com.bookviewer.View.Curriculum.BookListAdapter;

public class MainActivity extends AppCompatActivity {
    RecyclerView BookRecyclerView;
    BookListAdapter BookListViewAdapter;







//
//    private TextView mText[] = new TextView[2];
//    private String[] BookFiles = new String[2];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        BookRecyclerView = (RecyclerView)findViewById(R.id.recyclerview_booklist);
        BookRecyclerView.setHasFixedSize(true);
        BookRecyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 3));

        BookListViewAdapter = new BookListAdapter(MainActivity.this);
        BookRecyclerView.setAdapter(BookListViewAdapter);

        //BookRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));


//        mText[0] = (TextView)findViewById(R.id.text1);
//        mText[1] = (TextView)findViewById(R.id.text2);
//
//        GetBookData();
//
//        final Intent intent = new Intent(this, ViewActivity.class);
//        View.OnClickListener listener = new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                switch (view.getId()) {
//                    case R.id.text1:
//                        intent.putExtra("Title", getString(R.string.book_1));
//                        intent.putExtra("Path", BookFiles[0]);
//                        startActivity(intent);
//                        break;
//                    case R.id.text2:
//                        intent.putExtra("Title", getString(R.string.book_2));
//                        intent.putExtra("Path", BookFiles[1]);
//                        startActivity(intent);
//                        break;
//                }
//            }
//        };
//        mText[0].setOnClickListener(listener);
//        mText[1].setOnClickListener(listener);
    }
//
//    private void GetBookData() {
//
//        AssetManager assetManager = getAssets();
//        try {
//            BookFiles = assetManager.list("Data");
//        } catch (IOException e1) {
//            // TODO Auto-generated catch block
//            e1.printStackTrace();
//        }
//    }
}
