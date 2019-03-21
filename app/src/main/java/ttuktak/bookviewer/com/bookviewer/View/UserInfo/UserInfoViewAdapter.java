package ttuktak.bookviewer.com.bookviewer.View.UserInfo;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;
import java.util.Random;

import ttuktak.bookviewer.com.bookviewer.CommonFunc;
import ttuktak.bookviewer.com.bookviewer.Data.BookData;
import ttuktak.bookviewer.com.bookviewer.Data.DataMgr;
import ttuktak.bookviewer.com.bookviewer.R;

public class UserInfoViewAdapter extends RecyclerView.Adapter<UserInfoViewHolder> {


    Context AppContext;

    public UserInfoViewAdapter(Context context) {
        super();
        AppContext = context;
    }

    @Override
    public UserInfoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(AppContext).inflate(R.layout.holder_user_readbook_list,parent,false);
        return new UserInfoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserInfoViewHolder holder, int position) {

        ArrayList<BookData> recentBookList = DataMgr.getInstance().myData.getRecentReadBookData();
        BookData data = recentBookList.get(position);

        holder.layout.setLayoutParams(new ConstraintLayout.LayoutParams(CommonFunc.getInstance().GetDisplayWidth(), (CommonFunc.getInstance().GetDisplayHeight() / 6)));


       /* LinearLayout.LayoutParams lpForIcon = new LinearLayout.LayoutParams((int) (CommonFunc.getInstance().GetDisplayWidth() * 0.1), (int) (CommonFunc.getInstance().GetDisplayHeight() / 7));
        holder.Thumbnail.setLayoutParams(lpForIcon);

        LinearLayout.LayoutParams lpForDesc = new LinearLayout.LayoutParams((int) (CommonFunc.getInstance().GetDisplayWidth() * 0.8), (int) (CommonFunc.getInstance().GetDisplayHeight() / 7));
        holder.layout_desc.setLayoutParams(lpForDesc);*/



        Glide.with(AppContext)
                .load(data.ImgIdx)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                //.bitmapTransform(new CropCircleTransformation(AppContext))
                .into(holder.Thumbnail);


        if(data == null)
            holder.Thumbnail.setColorFilter(Color.parseColor("#555555"), PorterDuff.Mode.MULTIPLY);
        else
        {
            holder.Thumbnail.clearColorFilter();
            holder.Title.setText(data.bookName);
            holder.Author.setText(data.bookAuthor);

            // TODO 빼야댐
            String[] strBookTime = {"~ 2018-11-20", "~ 2018-11-25", "~ 2018-11-27", "~ 2018-12-20", "~ 2018-12-11", "~ 2018-12-5", "~ 2018-12-30" };
            Random random = new Random();
            int i = (int)(random.nextInt(7));

            holder.Time.setText(strBookTime[i]);
        }

        /*LinearLayout.LayoutParams lpForClear = new LinearLayout.LayoutParams((int) (CommonFunc.getInstance().GetDisplayWidth() * 0.1), (int) (CommonFunc.getInstance().GetDisplayHeight() / 7));
        holder.Clear.setLayoutParams(lpForClear);
*/
        Glide.with(AppContext)
                .load(R.drawable.book_good)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                //.bitmapTransform(new CropCircleTransformation(AppContext))
                .into(holder.Clear);

        if(position % 2 == 0)
            holder.Clear.setColorFilter(Color.parseColor("#555555"), PorterDuff.Mode.MULTIPLY);
        else
        {
            holder.Clear.clearColorFilter();
            holder.Progress.setProgress(100);
        }
    }

    @Override
    public int getItemCount() {
        return  2;
    }
}