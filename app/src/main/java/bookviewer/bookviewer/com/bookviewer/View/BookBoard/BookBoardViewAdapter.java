package bookviewer.bookviewer.com.bookviewer.View.BookBoard;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.ramotion.foldingcell.FoldingCell;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import bookviewer.bookviewer.com.bookviewer.CommonFunc;
import bookviewer.bookviewer.com.bookviewer.Data.BookData;
import bookviewer.bookviewer.com.bookviewer.Data.BookLocalData;
import bookviewer.bookviewer.com.bookviewer.Data.DataMgr;
import bookviewer.bookviewer.com.bookviewer.Data.TempFireBaseData;
import bookviewer.bookviewer.com.bookviewer.R;

public class BookBoardViewAdapter extends ArrayAdapter<TempFireBaseData.BookReport> {

    private HashSet<Integer> unfoldedIndexes = new HashSet<>();
    Context AppContext;
    public BookBoardViewAdapter(Context context, ArrayList<TempFireBaseData.BookReport> objects) {
        super(context, 0, objects);
        AppContext = context;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        FoldingCell cell = (FoldingCell) convertView;
        ViewHolder viewHolder;
        if (cell == null) {
            viewHolder = new ViewHolder();
            LayoutInflater vi = LayoutInflater.from(getContext());
            cell = (FoldingCell) vi.inflate(R.layout.holder_bookboard, parent, false);
            viewHolder.TitleBookThumnail = cell.findViewById(R.id.report_book_thumnail);
            viewHolder.TitleReportNickname = cell.findViewById(R.id.report_nickname);
            viewHolder.TitleReportSchoolname = cell.findViewById(R.id.report_schoolname);
            viewHolder.TitleReportTitle = cell.findViewById(R.id.report_desc);
            viewHolder.TitleLike = cell.findViewById(R.id.report_like);
            viewHolder.TitleLikeCount = cell.findViewById(R.id.report_like_count);
            viewHolder.TitleLikeImg = cell.findViewById(R.id.report_like_img);
            viewHolder.ContentsBookThumnail = cell.findViewById(R.id.contents_report_book_thumnail);
            viewHolder.ContentsReportNickname = cell.findViewById(R.id.contents_report_nickname);
            viewHolder.ContentsReportSchoolname = cell.findViewById(R.id.contents_report_schoolname);
            viewHolder.ContentsReport = cell.findViewById(R.id.contents_report_desc);
            viewHolder.ContentsLike = cell.findViewById(R.id.contents_report_like);
            viewHolder.ContentsLikeCount = cell.findViewById(R.id.contents_report_like_count);
            viewHolder.ContentsLikeImg = cell.findViewById(R.id.contents_report_like_img);
            // binding view parts to view holder
            cell.setTag(viewHolder);
        } else {
            if (unfoldedIndexes.contains(position)) {
                cell.unfold(true);
            } else {
                cell.fold(true);
            }
            viewHolder = (ViewHolder) cell.getTag();
        }

        final TempFireBaseData.BookReport item = getItem(position);
        int bookId = item.bookId;
        TempFireBaseData.BookData bookData = DataMgr.getInstance().getBookData(bookId);
        BookLocalData bookLocalData = DataMgr.getInstance().getBookLocalData(bookId);

        Glide.with(AppContext)
                .load(bookLocalData.ImgIdx)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                //.bitmapTransform(new CropCircleTransformation(AppContext))
                .into(viewHolder.TitleBookThumnail);

        Glide.with(AppContext)
                .load(bookLocalData.ImgIdx)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                //.bitmapTransform(new CropCircleTransformation(AppContext))
                .into(viewHolder.ContentsBookThumnail);

        viewHolder.TitleReportNickname.setText(item.nickName);
        viewHolder.TitleReportSchoolname.setText(item.schoolName);
        viewHolder.TitleReportTitle.setText(item.title);
        viewHolder.TitleLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DataMgr.getInstance().clickBookReportLike(view.getContext(), item.reportId);
                notifyDataSetChanged();
            }
        });

        int likeHeartImgIdx = DataMgr.getInstance().isBookReportLike(item.reportId) ? R.drawable.report_heart_on : R.drawable.report_heart_off;

        Glide.with(AppContext)
                .load(likeHeartImgIdx)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                //.bitmapTransform(new CropCircleTransformation(AppContext))
                .into(viewHolder.TitleLikeImg);
        viewHolder.TitleLikeCount.setText(String.valueOf(item.likeCount));


        viewHolder.ContentsReportNickname.setText(item.nickName);
        viewHolder.ContentsReportSchoolname.setText(item.schoolName);
        viewHolder.ContentsReport.setText(item.report);
        viewHolder.ContentsLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DataMgr.getInstance().clickBookReportLike(view.getContext(), item.reportId);
                notifyDataSetChanged();
            }
        });

        Glide.with(AppContext)
                .load(likeHeartImgIdx)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                //.bitmapTransform(new CropCircleTransformation(AppContext))
                .into(viewHolder.ContentsLikeImg);
        viewHolder.ContentsLikeCount.setText(String.valueOf(item.likeCount));

        return cell;
    }

    // simple methods for register cell state changes
    public void registerToggle(int position) {
        if (unfoldedIndexes.contains(position))
            registerFold(position);
        else
            registerUnfold(position);
    }

    public void registerFold(int position) {
        unfoldedIndexes.remove(position);
    }

    public void registerUnfold(int position) {
        unfoldedIndexes.add(position);
    }
    // View lookup cache
    private static class ViewHolder {
        ImageView TitleBookThumnail;
        TextView TitleReportNickname;
        TextView TitleReportSchoolname;
        TextView TitleReportTitle;
        LinearLayout TitleLike;
        TextView TitleLikeCount;
        ImageView TitleLikeImg;

        ImageView ContentsBookThumnail;
        TextView ContentsReportNickname;
        TextView ContentsReportSchoolname;
        TextView ContentsReport;
        LinearLayout ContentsLike;
        TextView ContentsLikeCount;
        ImageView ContentsLikeImg;
    }
}

//
//public class BookBoardViewAdapter  extends RecyclerView.Adapter<BookBoardViewHolder>
//{
//    Context AppContext;
//
//    public BookBoardViewAdapter(Context context) {
//        super();
//        AppContext = context;
//    }
//
//
//    @Override
//    public BookBoardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(AppContext).inflate(R.layout.holder_booklist,parent,false);
//        return new BookBoardViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(BookBoardViewHolder holder, final int position) {
//
//        int bookId = DataMgr.getInstance().myData.viewBookIdList.get(position);
//        BookData data = DataMgr.getInstance().myData.getBookData(bookId);
//        BookLocalData localData = DataMgr.getInstance().bookLocalDataList.get(bookId);
//
//        holder.layout.setLayoutParams(new LinearLayout.LayoutParams(CommonFunc.getInstance().GetDisplayWidth(), (CommonFunc.getInstance().GetDisplayHeight() / 5)));
//
//        Glide.with(AppContext)
//                .load(localData.ImgIdx)
//                .diskCacheStrategy(DiskCacheStrategy.ALL)
//                //.bitmapTransform(new CropCircleTransformation(AppContext))
//                .into(holder.Thumbnail);
//
//
//        if(localData == null)
//            holder.Thumbnail.setColorFilter(Color.parseColor("#555555"), PorterDuff.Mode.MULTIPLY);
//        else
//        {
//            holder.Thumbnail.clearColorFilter();
//            holder.Title.setText(data.bookName);
//        }
//
//
///*
//        RelativeLayout.LayoutParams lpProgress = new RelativeLayout.LayoutParams((int) (CommonFunc.getInstance().GetDisplayWidth() / 3 * 0.4), (int) (CommonFunc.getInstance().GetDisplayWidth() / 3 * 0.2));
//        lpProgress.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
//        lpProgress.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
//        holder.Progress.setLayoutParams(lpProgress);
//*/
//
//        // holder.BackGround.setLayoutParams(new RelativeLayout.LayoutParams(CommonFunc.getInstance().GetDisplayWidth() /3, (CommonFunc.getInstance().GetDisplayWidth() / 3)));
//
//        // holder.BackGround.setImageResource(R.drawable.booklist_bottom2);
//
///*
//        holder.layout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                AssetManager assetManager = AppContext.getAssets();
//                try {
//                    BookData data = DataMgr.getInstance().myData.getBookData(bookId);
//                    BookLocalData localData = DataMgr.getInstance().bookLocalDataList.get(bookId);
//                    String Name = data.bookName;
//                    if(localData == null)
//                    {
//                        CommonFunc.ShowPopup_Listener listener = new CommonFunc.ShowPopup_Listener() {
//                            @Override
//                            public void Listener() {
//                                BookLocalData localData = new BookLocalData();
//                                localData.bookId = data.bookId;
//                                localData.ImgIdx = R.drawable.book_1;
//                                DataMgr.getInstance().bookLocalDataList.put(data.bookId, localData);
//                                DataMgr.getInstance().saveBookLocalData(AppContext);
//
//                                notifyDataSetChanged();
//                            }
//                        };
//
//                        CommonFunc.getInstance().ShowDefaultPopup(AppContext,listener,null, "구매", Name + "을(를) 구매하시겠습니까?", "확인", "취소");
//                    }
//                    else
//                    {
//                        final Intent intent = new Intent(AppContext, ViewActivity.class);
//                        intent.putExtra("Title", Name);
//                        intent.putExtra("Path", AppContext.getAssets().list("Data")[1]);
//                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                        AppContext.startActivity(intent);
//                    }
//                } catch (IOException e1) {
//                    // TODO Auto-generated catch block
//                    e1.printStackTrace();
//                }
//            }
//        });*/
//    }
//
//    @Override
//    public int getItemCount() {
//        //return  DataMgr.getInstance().myData.viewBookIdList.size();
//        return  5;
//    }
//}
