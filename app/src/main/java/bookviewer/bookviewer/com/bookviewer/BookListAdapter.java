package bookviewer.bookviewer.com.bookviewer;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.PorterDuff;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.content.res.AssetManager;
import android.support.v7.app.AppCompatActivity;

import java.io.IOException;

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

        BookData data = CommonFunc.getInstance().BookDataList.get(position);

        holder.Thumbnail.setLayoutParams(new RelativeLayout.LayoutParams(CommonFunc.getInstance().GetDisplayWidth() /3, (CommonFunc.getInstance().GetDisplayWidth() / 3)));
        holder.Thumbnail.setImageResource(data.ImgIdx);

        if(data.Buy == false)
            holder.Thumbnail.setColorFilter(Color.parseColor("#555555"), PorterDuff.Mode.MULTIPLY);
        else
            holder.Thumbnail.clearColorFilter();

        RelativeLayout.LayoutParams lpProgress = new RelativeLayout.LayoutParams((int) (CommonFunc.getInstance().GetDisplayWidth() / 3 * 0.4), (int) (CommonFunc.getInstance().GetDisplayWidth() / 3 * 0.2));
        lpProgress.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        lpProgress.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        holder.Progress.setLayoutParams(lpProgress);

        holder.BackGround.setLayoutParams(new RelativeLayout.LayoutParams(CommonFunc.getInstance().GetDisplayWidth() /3, (CommonFunc.getInstance().GetDisplayWidth() / 3)));

        holder.BackGround.setImageResource(R.drawable.booklist_bottom2);


        holder.Thumbnail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AssetManager assetManager = AppContext.getAssets();
                try {
                    BookData data = CommonFunc.getInstance().BookDataList.get(position);
                    String Name = AppContext.getResources().getString(data.NameIdx);
                    if(data.Buy == false)
                    {
                        CommonFunc.ShowPopup_Listener listener = new CommonFunc.ShowPopup_Listener() {
                            @Override
                            public void Listener() {
                                data.Buy = true;
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
        return  CommonFunc.getInstance().BookDataList.size();
    }
}
