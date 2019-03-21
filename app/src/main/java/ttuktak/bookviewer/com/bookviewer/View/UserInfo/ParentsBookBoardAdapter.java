package ttuktak.bookviewer.com.bookviewer.View.UserInfo;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import ttuktak.bookviewer.com.bookviewer.Data.BookBoardData;
import ttuktak.bookviewer.com.bookviewer.Data.BookData;
import ttuktak.bookviewer.com.bookviewer.Data.DataMgr;
import ttuktak.bookviewer.com.bookviewer.R;

public class ParentsBookBoardAdapter extends RecyclerView.Adapter<ParentsBookBoardHolder> {
    Context AppContext;

    public ParentsBookBoardAdapter(Context context) {
        super();
        AppContext = context;
    }


    @Override
    public ParentsBookBoardHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(AppContext).inflate(R.layout.holder_parents_book_board, parent, false);

        return new ParentsBookBoardHolder(view);
    }

    @Override
    public void onBindViewHolder(ParentsBookBoardHolder holder, final int position) {

        BookBoardData boardData =  DataMgr.getInstance().bookBoardDataList.get(position);
        int bookId = boardData.bookId;
        BookData bookData = DataMgr.getInstance().loadBookData(bookId);

        Glide.with(AppContext)
                .load(bookData.ImgIdx)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                //.bitmapTransform(new CropCircleTransformation(AppContext))
                .into(holder.BookBoardImg);

        holder.Title.setText(boardData.title);
        holder.Desc.setText(boardData.report);
    }

    @Override
    public int getItemCount() {
        return DataMgr.getInstance().bookBoardDataList.size();
    }
}
