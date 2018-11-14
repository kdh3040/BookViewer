package bookviewer.bookviewer.com.bookviewer.View.UserInfo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
        import android.support.v4.app.Fragment;
        import android.support.v7.widget.GridLayoutManager;
        import android.support.v7.widget.RecyclerView;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.ImageView;
        import android.widget.RelativeLayout;
        import android.widget.TextView;

        import com.bumptech.glide.Glide;
        import com.bumptech.glide.load.engine.DiskCacheStrategy;

        import java.util.ArrayList;

        import bookviewer.bookviewer.com.bookviewer.Data.BookData;
        import bookviewer.bookviewer.com.bookviewer.Data.BookLocalData;
        import bookviewer.bookviewer.com.bookviewer.Data.DataMgr;
import bookviewer.bookviewer.com.bookviewer.R;
        import jp.wasabeef.glide.transformations.CropCircleTransformation;


public class UserInfoView extends Fragment {

    RecyclerView UserReadBookRecyclerView;
    UserInfoViewAdapter UserReadBookListViewAdapter;
    View fragView;
    public TextView ParentsMode;

    public UserInfoView() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    Context context;
    public TextView NickName, SchoolName;
    public ImageView ThumbNail;
    public ArrayList<RelativeLayout> RecentBook = new ArrayList<>();
    public ArrayList<ImageView> RecentBookImg = new ArrayList<>();
    public ArrayList<TextView> RecentBookTitle = new ArrayList<>();
    public ArrayList<TextView> RecentBookAuthor = new ArrayList<>();
    public ArrayList<TextView> RecentBookCompleteTime = new ArrayList<>();
    public ArrayList<TextView> RecentBookReadPage = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        context = getActivity();
        fragView = inflater.inflate(R.layout.fragment_user_info_view,container,false);

        UserReadBookRecyclerView = (RecyclerView)fragView.findViewById(R.id.recyclerview_User_readBookList);
        UserReadBookRecyclerView.setHasFixedSize(true);
        UserReadBookRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 1));

        UserReadBookListViewAdapter = new UserInfoViewAdapter(getContext());
        UserReadBookRecyclerView.setAdapter(UserReadBookListViewAdapter);

        NickName = (TextView)fragView.findViewById(R.id.user_info_nickname);
        SchoolName = (TextView)fragView.findViewById(R.id.user_info_schoolname);
        ThumbNail = (ImageView)fragView.findViewById(R.id.user_info_profile);

        ParentsMode = fragView.findViewById(R.id.parents_mode);
        ParentsMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent intent = new Intent(fragView.getContext(), ParentsModeActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                fragView.getContext().startActivity(intent);
            }
        });

       /* NickName = (TextView)fragView.findViewById(R.id.user_info_nickname);
        SchoolName = (TextView)fragView.findViewById(R.id.user_info_schoolname);

        RecentBook.add((RelativeLayout)fragView.findViewById(R.id.recent_book_1));
        RecentBook.add((RelativeLayout)fragView.findViewById(R.id.recent_book_2));
        RecentBook.add((RelativeLayout)fragView.findViewById(R.id.recent_book_3));

        RecentBookImg.add((ImageView)fragView.findViewById(R.id.recent_book_1_img));
        RecentBookImg.add((ImageView)fragView.findViewById(R.id.recent_book_2_img));
        RecentBookImg.add((ImageView)fragView.findViewById(R.id.recent_book_3_img));

        RecentBookTitle.add((TextView)fragView.findViewById(R.id.recent_book_1_title));
        RecentBookTitle.add((TextView)fragView.findViewById(R.id.recent_book_2_title));
        RecentBookTitle.add((TextView)fragView.findViewById(R.id.recent_book_3_title));

        RecentBookAuthor.add((TextView)fragView.findViewById(R.id.recent_book_1_author));
        RecentBookAuthor.add((TextView)fragView.findViewById(R.id.recent_book_2_author));
        RecentBookAuthor.add((TextView)fragView.findViewById(R.id.recent_book_3_author));

        RecentBookCompleteTime.add((TextView)fragView.findViewById(R.id.recent_book_1_time));
        RecentBookCompleteTime.add((TextView)fragView.findViewById(R.id.recent_book_2_time));
        RecentBookCompleteTime.add((TextView)fragView.findViewById(R.id.recent_book_3_time));

        RecentBookReadPage.add((TextView)fragView.findViewById(R.id.recent_book_1_page));
        RecentBookReadPage.add((TextView)fragView.findViewById(R.id.recent_book_2_page));
        RecentBookReadPage.add((TextView)fragView.findViewById(R.id.recent_book_3_page));

        refreshUserInfo();
        refreshRecentBook();*/

        refreshUserInfo();

        return fragView;
    }

    private void refreshUserInfo()
    {
        NickName.setText(DataMgr.getInstance().myData.nickName);
        SchoolName.setText(DataMgr.getInstance().myData.schoolName);

        Glide.with(getContext())
                .load(R.drawable.user_profile)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .bitmapTransform(new CropCircleTransformation(getContext()))
                .into(ThumbNail);
    }

    private void refreshRecentBook()
    {
        ArrayList<Integer> recentBookList = DataMgr.getInstance().getRecentBookLocalData();

        for(int index = 0 ; index < RecentBook.size() ; ++index)
        {
            if(index >= recentBookList.size())
            {
                RecentBook.get(index).setVisibility(View.INVISIBLE);
                continue;
            }

            BookLocalData localData = DataMgr.getInstance().getBookLocalData(recentBookList.get(index));
            if(localData == null)
            {
                RecentBook.get(index).setVisibility(View.INVISIBLE);
                continue;
            }

            BookData bookData = DataMgr.getInstance().myData.getBookData(recentBookList.get(index));
            RecentBook.get(index).setVisibility(View.VISIBLE);
            RecentBookImg.get(index).setImageResource(localData.ImgIdx);
            RecentBookTitle.get(index).setText(bookData.bookName);
            RecentBookAuthor.get(index).setText(bookData.bookAuthor);
            RecentBookCompleteTime.get(index).setText("1주일 후에 완료");
            RecentBookReadPage.get(index).setText(localData.recentPage + "Page");
        }
    }

}