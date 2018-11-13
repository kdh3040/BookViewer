package bookviewer.bookviewer.com.bookviewer.View.UserInfo;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import bookviewer.bookviewer.com.bookviewer.R;

public class SimpleCurriculumHolder extends RecyclerView.ViewHolder{
    public LinearLayout layout;
    public TextView CurriculumName;
    public TextView WrongAnswerCount;
    public ProgressBar CurriculumProgress;
    public SimpleCurriculumHolder(View itemView) {
        super(itemView);
        layout = itemView.findViewById(R.id.parents_mode_layout);
        CurriculumName = itemView.findViewById(R.id.curriculum_name);
        WrongAnswerCount = itemView.findViewById(R.id.curriculum_wrong_count);
        CurriculumProgress = itemView.findViewById(R.id.curriculum_progress);
    }
}