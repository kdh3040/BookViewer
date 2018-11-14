package bookviewer.bookviewer.com.bookviewer.View.Curriculum;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.content.res.AssetManager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.io.IOException;

import bookviewer.bookviewer.com.bookviewer.CommonFunc;
import bookviewer.bookviewer.com.bookviewer.Data.BookData;
import bookviewer.bookviewer.com.bookviewer.Data.BookLocalData;
import bookviewer.bookviewer.com.bookviewer.Data.DataMgr;
import bookviewer.bookviewer.com.bookviewer.R;
import bookviewer.bookviewer.com.bookviewer.ViewActivity;

public class BookListAdapter extends RecyclerView.Adapter<BookListViewHolder>
{
    Context AppContext;

    public BookListAdapter(Context context) {
        super();
        AppContext = context;
    }


    @Override
    public BookListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(AppContext).inflate(R.layout.holder_booklist,parent,false);
        return new BookListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(BookListViewHolder holder, final int position) {

        int bookId = DataMgr.getInstance().myData.viewBookIdList.get(position);
        BookData data = DataMgr.getInstance().myData.getBookData(bookId);
        BookLocalData localData = DataMgr.getInstance().bookLocalDataList.get(bookId);

        //holder.Thumbnail.setLayoutParams(new RelativeLayout.LayoutParams(CommonFunc.getInstance().GetDisplayWidth() /3, (CommonFunc.getInstance().GetDisplayWidth() / 3)));
        //holder.Thumbnail.setImageResource(localData.ImgIdx);

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





/*
        RelativeLayout.LayoutParams lpProgress = new RelativeLayout.LayoutParams((int) (CommonFunc.getInstance().GetDisplayWidth() / 3 * 0.4), (int) (CommonFunc.getInstance().GetDisplayWidth() / 3 * 0.2));
        lpProgress.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        lpProgress.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        holder.Progress.setLayoutParams(lpProgress);
*/

       // holder.BackGround.setLayoutParams(new RelativeLayout.LayoutParams(CommonFunc.getInstance().GetDisplayWidth() /3, (CommonFunc.getInstance().GetDisplayWidth() / 3)));

       // holder.BackGround.setImageResource(R.drawable.booklist_bottom2);


        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AssetManager assetManager = AppContext.getAssets();
                try {
                    BookData data = DataMgr.getInstance().myData.getBookData(bookId);
                    BookLocalData localData = DataMgr.getInstance().bookLocalDataList.get(bookId);
                    String Name = data.bookName;
                    if(localData == null)
                    {
                        CommonFunc.ShowPopup_Listener listener = new CommonFunc.ShowPopup_Listener() {
                            @Override
                            public void Listener() {
                                BookLocalData localData = new BookLocalData();
                                localData.bookId = data.bookId;
                                localData.ImgIdx = R.drawable.book_1;
                                DataMgr.getInstance().bookLocalDataList.put(data.bookId, localData);
                                DataMgr.getInstance().saveBookLocalData(AppContext);

                                notifyDataSetChanged();
                            }
                        };

                        CommonFunc.getInstance().ShowDefaultPopup(AppContext,listener,null, "구매", Name + "을(를) 구매하시겠습니까?", "확인", "취소");
                    }
                    else
                    {
                        final Intent intent = new Intent(AppContext, ViewActivity.class);
                        intent.putExtra("Title", Name);
                        intent.putExtra("Path", AppContext.getAssets().list("Data")[1]);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        AppContext.startActivity(intent);
                    }
                } catch (IOException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return  DataMgr.getInstance().myData.viewBookIdList.size();
    }
}
