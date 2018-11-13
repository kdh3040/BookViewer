package bookviewer.bookviewer.com.bookviewer;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.io.IOException;
import java.util.ArrayList;

import bookviewer.bookviewer.com.bookviewer.Data.BookData;
import bookviewer.bookviewer.com.bookviewer.Data.BookLocalData;
import bookviewer.bookviewer.com.bookviewer.Data.DataMgr;

public class UserInfoViewAdapter extends RecyclerView.Adapter<UserInfoViewHolder> {


    Context AppContext;

    public UserInfoViewAdapter(Context context) {
        super();
        AppContext = context;
    }

    @Override
    public UserInfoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(AppContext).inflate(R.layout.holder_userreadbooklist,parent,false);
        return new UserInfoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserInfoViewHolder holder, int position) {

        ArrayList<Integer> recentBookList = DataMgr.getInstance().getRecentBookLocalData();
        BookLocalData localData = DataMgr.getInstance().getBookLocalData(recentBookList.get(position));
        BookData data = DataMgr.getInstance().myData.getBookData(recentBookList.get(position));

        holder.layout.setLayoutParams(new LinearLayout.LayoutParams(CommonFunc.getInstance().GetDisplayWidth(), (CommonFunc.getInstance().GetDisplayHeight() / 7)));
        Glide.with(AppContext)
                .load(localData.ImgIdx)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                //.bitmapTransform(new CropCircleTransformation(AppContext))
                .into(holder.Thumbnail);


        if(localData == null)
            holder.Thumbnail.setColorFilter(Color.parseColor("#555555"), PorterDuff.Mode.MULTIPLY);
        else
        {
            holder.Thumbnail.clearColorFilter();
            holder.Title.setText(data.bookName);
        }
    }

    @Override
    public int getItemCount() {
        return  2;
    }
}