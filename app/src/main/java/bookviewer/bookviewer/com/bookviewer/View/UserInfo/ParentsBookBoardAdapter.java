package bookviewer.bookviewer.com.bookviewer.View.UserInfo;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import bookviewer.bookviewer.com.bookviewer.CommonFunc;
import bookviewer.bookviewer.com.bookviewer.Data.BookLocalData;
import bookviewer.bookviewer.com.bookviewer.Data.DataMgr;
import bookviewer.bookviewer.com.bookviewer.Data.SchoolCurriculumData;
import bookviewer.bookviewer.com.bookviewer.Data.TempFireBaseData;
import bookviewer.bookviewer.com.bookviewer.R;

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

        TempFireBaseData.BookReport data =  DataMgr.getInstance().TempData.bookReportDataList.get(position);
        int bookId = data.bookId;
        TempFireBaseData.BookData bookData = DataMgr.getInstance().getBookData(bookId);
        BookLocalData bookLocalData = DataMgr.getInstance().getBookLocalData(bookId);

        Glide.with(AppContext)
                .load(bookLocalData.ImgIdx)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                //.bitmapTransform(new CropCircleTransformation(AppContext))
                .into(holder.BookBoardImg);

        holder.Title.setText(data.title);
        holder.Desc.setText(data.report);
    }

    @Override
    public int getItemCount() {
        return DataMgr.getInstance().TempData.bookReportDataList.size();
    }
}
