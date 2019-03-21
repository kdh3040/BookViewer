package ttuktak.bookviewer.com.bookviewer.View.UserInfo;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.Random;

import ttuktak.bookviewer.com.bookviewer.CommonFunc;
import ttuktak.bookviewer.com.bookviewer.Data.CurriculumData;
import ttuktak.bookviewer.com.bookviewer.Data.DataMgr;
import ttuktak.bookviewer.com.bookviewer.R;

public class CurriculumProgressAdapter extends RecyclerView.Adapter<CurriculumProgressHolder> {
    Context AppContext;

    public CurriculumProgressAdapter(Context context) {
        super();
        AppContext = context;
    }


    @Override
    public CurriculumProgressHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(AppContext).inflate(R.layout.holder_parents_curriculum, parent, false);

        return new CurriculumProgressHolder(view);
    }

    @Override
    public void onBindViewHolder(CurriculumProgressHolder holder, final int position) {
        holder.Layout.setLayoutParams(new LinearLayout.LayoutParams(CommonFunc.getInstance().GetDisplayWidth() / 4, (CommonFunc.getInstance().GetDisplayWidth() / 3)));

        CurriculumData curriculumData = DataMgr.getInstance().myData.curriculumData.get(position);
        holder.CurriculumName.setText(curriculumData.curriculumName);
        Random test = new Random();
        holder.Progress.setProgress(test.nextInt(90) + 10);
    }

    @Override
    public int getItemCount() {
        return DataMgr.getInstance().myData.curriculumData.size();
    }
}
