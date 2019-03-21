package ttuktak.bookviewer.com.bookviewer.View.Curriculum;

import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import ttuktak.bookviewer.com.bookviewer.R;

public class BookListViewHolder extends RecyclerView.ViewHolder{
    public ConstraintLayout layout;
    public ImageView Thumbnail;
    public TextView Title, Author, Time;
    public ImageView Clear;
    public ProgressBar Progress;
    public BookListViewHolder(View itemView) {
        super(itemView);
        layout = itemView.findViewById(R.id.Book_Layout);
        Thumbnail = itemView.findViewById(R.id.Book_Thumbnail);
        Title = itemView.findViewById(R.id.Book_Title);
        Author = itemView.findViewById(R.id.Book_Author);
        Time = itemView.findViewById(R.id.Book_Time);
        Clear = itemView.findViewById(R.id.Book_Good);
        Progress = itemView.findViewById(R.id.Book_Progress);
        //BackGround = itemView.findViewById(R.id.Book_Back);


    }
}
