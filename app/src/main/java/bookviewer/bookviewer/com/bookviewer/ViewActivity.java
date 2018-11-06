package bookviewer.bookviewer.com.bookviewer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener;
import com.github.barteksc.pdfviewer.listener.OnPageScrollListener;
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle;
import com.github.barteksc.pdfviewer.scroll.ScrollHandle;
import com.github.barteksc.pdfviewer.util.FitPolicy;

public class ViewActivity extends AppCompatActivity implements OnPageChangeListener {
    private  String BookPath, BookName;
    private  PDFView pdfView;

    private TextView Title_Text, Title_Page, Menu_scroll;
    private Button Title_Button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);

        Intent intent = getIntent();
        BookPath = intent.getStringExtra("Path");
        BookName = intent.getStringExtra("Title");

        Title_Text = (TextView)findViewById(R.id.title_text);
        Title_Text.setText(BookName);

        Title_Button = (Button)findViewById(R.id.title_back);
        Title_Button.setOnClickListener(mClickListener);

        Title_Page = (TextView)findViewById(R.id.title_page);


        pdfView = (PDFView)findViewById(R.id.pdfView);
        pdfView.fromAsset("Data/"+BookPath)
                .enableSwipe(true)
                .swipeHorizontal(true)
                .onPageChange(this)
                .pageSnap(true)
                .autoSpacing(true)
                .pageFling(true)
                .pageFitPolicy(FitPolicy.BOTH)
                .scrollHandle(new DefaultScrollHandle(this))
                .load();
    }


    @Override
    public void onPageChanged(int page, int pageCount) {
        Title_Page.setText((String.format("%s / %s", page + 1, pageCount)));
        pdfView.setScrollBarFadeDuration(1);
    }

    Button.OnClickListener mClickListener = new View.OnClickListener() {
        public void onClick(View v) {
            onBackPressed();
        }
    };

}
