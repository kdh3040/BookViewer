package bookviewer.bookviewer.com.bookviewer.View.UserInfo;

import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import bookviewer.bookviewer.com.bookviewer.R;

public class UserInfoViewHolder extends RecyclerView.ViewHolder {
    public ConstraintLayout layout;
    //public LinearLayout layout_desc;
    public ImageView Thumbnail;
    public TextView Title, Author, Time;
    public ImageView Clear;
    public ProgressBar Progress;
    public UserInfoViewHolder(View itemView) {
        super(itemView);
        layout = itemView.findViewById(R.id.Book_Layout);
        //layout_desc = itemView.findViewById(R.id.Book_Desc);
        Thumbnail = itemView.findViewById(R.id.Book_Thumbnail);
        Title = itemView.findViewById(R.id.Book_Title);
        Author = itemView.findViewById(R.id.Book_Author);
        Time = itemView.findViewById(R.id.Book_Time);
        Clear = itemView.findViewById(R.id.Book_Good);
        Progress = itemView.findViewById(R.id.Book_Progress);
    }
}
