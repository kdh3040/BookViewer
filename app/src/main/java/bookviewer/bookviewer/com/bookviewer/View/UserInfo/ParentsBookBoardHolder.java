package bookviewer.bookviewer.com.bookviewer.View.UserInfo;

import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.lzyzsd.circleprogress.CircleProgress;

import bookviewer.bookviewer.com.bookviewer.R;

public class ParentsBookBoardHolder extends RecyclerView.ViewHolder{

    public ImageView BookBoardImg;
    public TextView Title;
    public TextView Desc;
    public ParentsBookBoardHolder(View itemView) {
        super(itemView);
        BookBoardImg = itemView.findViewById(R.id.board_book_img);
        Title = itemView.findViewById(R.id.board_book_title);
        Desc = itemView.findViewById(R.id.board_book_desc);
    }
}
