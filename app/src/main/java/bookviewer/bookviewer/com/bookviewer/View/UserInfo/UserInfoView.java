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
    public TextView NickName, SchoolName, AuthorName;
    public ImageView ThumbNail;

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
        UserReadBookListViewAdapter.setHasStableIds(true);
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

}
