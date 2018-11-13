package bookviewer.bookviewer.com.bookviewer.View.UserInfo;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import bookviewer.bookviewer.com.bookviewer.CommonFunc;
import bookviewer.bookviewer.com.bookviewer.Data.DataMgr;
import bookviewer.bookviewer.com.bookviewer.Data.SchoolCurriculumData;
import bookviewer.bookviewer.com.bookviewer.R;

public class SimpleCurriculumAdapter extends RecyclerView.Adapter<SimpleCurriculumHolder>
{
    Context AppContext;

    public SimpleCurriculumAdapter(Context context) {
        super();
        AppContext = context;
    }


    @Override
    public SimpleCurriculumHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(AppContext).inflate(R.layout.holder_parents_mode,parent,false);

        return new SimpleCurriculumHolder(view);
    }

    @Override
    public void onBindViewHolder(SimpleCurriculumHolder holder, final int position) {
        holder.layout.setLayoutParams(new LinearLayout.LayoutParams(CommonFunc.getInstance().GetDisplayWidth(), (CommonFunc.getInstance().GetDisplayHeight() / 8)));

        SchoolCurriculumData curriculumData = DataMgr.getInstance().myData.schoolCurriculumDataList.get(position);
        holder.CurriculumName.setText(curriculumData.schoolCurriculumName);
        holder.WrongAnswerCount.setText("3개 틀림");
        holder.CurriculumProgress.setProgress(40);
    }

    @Override
    public int getItemCount() {
        return DataMgr.getInstance().myData.schoolCurriculumDataList.size();
    }
}
