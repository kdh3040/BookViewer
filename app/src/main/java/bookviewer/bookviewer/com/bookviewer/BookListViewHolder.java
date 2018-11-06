package bookviewer.bookviewer.com.bookviewer;

import android.view.View;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

public class BookListViewHolder extends RecyclerView.ViewHolder{
    public ImageView Thumbnail;
    public BookListViewHolder(View itemView) {
        super(itemView);
        Thumbnail = itemView.findViewById(R.id.Book_Thumbnail);
        Thumbnail.setImageResource(R.drawable.book_1);
    }
}
