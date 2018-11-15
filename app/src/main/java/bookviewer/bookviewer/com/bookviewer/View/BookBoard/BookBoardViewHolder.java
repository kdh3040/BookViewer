package bookviewer.bookviewer.com.bookviewer.View.BookBoard;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import bookviewer.bookviewer.com.bookviewer.R;

public class BookBoardViewHolder extends RecyclerView.ViewHolder{
    public LinearLayout layout;
    public ImageView Thumbnail;
    public TextView Title, Author, Time;

    public BookBoardViewHolder(View itemView) {
        super(itemView);
        layout = itemView.findViewById(R.id.Book_Layout);
        Thumbnail = itemView.findViewById(R.id.Book_Thumbnail);
        Title = itemView.findViewById(R.id.Book_Title);
        Author = itemView.findViewById(R.id.Book_Author);
        Time = itemView.findViewById(R.id.Book_Time);
        //BackGround = itemView.findViewById(R.id.Book_Back);


    }
}
