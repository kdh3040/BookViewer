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
import java.util.Random;

import bookviewer.bookviewer.com.bookviewer.CommonFunc;
import bookviewer.bookviewer.com.bookviewer.Data.BookData;
import bookviewer.bookviewer.com.bookviewer.Data.DataMgr;
import bookviewer.bookviewer.com.bookviewer.R;
import bookviewer.bookviewer.com.bookviewer.ViewActivity;

public class BookListAdapter extends RecyclerView.Adapter<BookListViewHolder>
{
    Context AppContext;
    int mTabCount;

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

        int bookId = DataMgr.getInstance().myData.nowCurriculumBookList.get(position);
        BookData data = DataMgr.getInstance().myData.getBookData(bookId);

        //holder.Thumbnail.setLayoutParams(new RelativeLayout.LayoutParams(CommonFunc.getInstance().GetDisplayWidth() /3, (CommonFunc.getInstance().GetDisplayWidth() / 3)));
        //holder.Thumbnail.setImageResource(localData.ImgIdx);

        holder.layout.setLayoutParams(new LinearLayout.LayoutParams(CommonFunc.getInstance().GetDisplayWidth(), (CommonFunc.getInstance().GetDisplayHeight() / 7)));

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
                    String Name = data.bookName;
                    if(data == null)
                    {
                        CommonFunc.ShowPopup_Listener listener = new CommonFunc.ShowPopup_Listener() {
                            @Override
                            public void Listener() {
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
        return  DataMgr.getInstance().myData.nowCurriculumBookList.size();
    }
}
