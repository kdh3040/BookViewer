package bookviewer.bookviewer.com.bookviewer;

import android.view.View;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class BookListViewHolder extends RecyclerView.ViewHolder{
    public LinearLayout layout;
    public ImageView Thumbnail;
    public TextView Title, Author, Time, Page;
    public BookListViewHolder(View itemView) {
        super(itemView);
        layout = itemView.findViewById(R.id.Book_Layout);
        Thumbnail = itemView.findViewById(R.id.Book_Thumbnail);
        Title = itemView.findViewById(R.id.Book_Title);
        Author = itemView.findViewById(R.id.Book_Author);
        Time = itemView.findViewById(R.id.Book_Time);
        Page = itemView.findViewById(R.id.Book_Page);
        //BackGround = itemView.findViewById(R.id.Book_Back);


    }
}
