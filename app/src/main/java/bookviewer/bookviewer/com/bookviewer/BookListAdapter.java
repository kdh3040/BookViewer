package bookviewer.bookviewer.com.bookviewer;

import android.content.Context;
import android.content.Intent;
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

        holder.Thumbnail.setLayoutParams(new RelativeLayout.LayoutParams(CommonFunc.getInstance().GetDisplayWidth() /3, (CommonFunc.getInstance().GetDisplayWidth() / 3)));
        holder.Thumbnail.setImageResource(R.drawable.book_1);

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
                    final Intent intent = new Intent(AppContext, ViewActivity.class);
                    intent.putExtra("Title", "톰소여의 모험");
                    intent.putExtra("Path", AppContext.getAssets().list("Data")[1]);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    AppContext.startActivity(intent);
                } catch (IOException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return  40;
    }
}
