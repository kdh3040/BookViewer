package bookviewer.bookviewer.com.bookviewer;

import android.view.View;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

public class BookListViewHolder extends RecyclerView.ViewHolder{
    public ImageView Thumbnail, BackGround;
    public TextView Progress;
    public BookListViewHolder(View itemView) {
        super(itemView);
        Thumbnail = itemView.findViewById(R.id.Book_Thumbnail);
        BackGround = itemView.findViewById(R.id.Book_Back);
        Progress = itemView.findViewById(R.id.Book_Progress);

    }
}
