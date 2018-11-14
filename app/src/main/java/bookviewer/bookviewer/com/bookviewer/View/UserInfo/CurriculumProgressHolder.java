package bookviewer.bookviewer.com.bookviewer.View.UserInfo;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.lzyzsd.circleprogress.CircleProgress;
import com.github.lzyzsd.circleprogress.DonutProgress;

import bookviewer.bookviewer.com.bookviewer.R;

public class CurriculumProgressHolder extends RecyclerView.ViewHolder{
    public LinearLayout Layout;
    public CircleProgress Progress;
    public TextView CurriculumName;
    public CurriculumProgressHolder(View itemView) {
        super(itemView);
        Layout = itemView.findViewById(R.id.curriculum_progress_layout);
        Progress = itemView.findViewById(R.id.curriculum_progress);
        CurriculumName = itemView.findViewById(R.id.curriculum_name);
    }
}
