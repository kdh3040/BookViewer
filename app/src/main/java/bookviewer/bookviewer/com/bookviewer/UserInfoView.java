package bookviewer.bookviewer.com.bookviewer;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import bookviewer.bookviewer.com.bookviewer.Data.BookData;
import bookviewer.bookviewer.com.bookviewer.Data.BookLocalData;
import bookviewer.bookviewer.com.bookviewer.Data.DataMgr;


public class UserInfoView extends Fragment {

    public UserInfoView() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    View fragView;
    Context context;
    public TextView NickName, SchoolName;
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
        NickName = (TextView)fragView.findViewById(R.id.user_info_nickname);
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
        refreshRecentBook();

        return fragView;
    }

    private void refreshUserInfo()
    {
        NickName.setText(DataMgr.getInstance().myData.nickName);
        SchoolName.setText(DataMgr.getInstance().myData.schoolName);
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
