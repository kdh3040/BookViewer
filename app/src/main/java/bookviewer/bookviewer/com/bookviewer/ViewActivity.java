package bookviewer.bookviewer.com.bookviewer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener;
import com.github.barteksc.pdfviewer.util.FitPolicy;

public class ViewActivity extends AppCompatActivity implements OnPageChangeListener {
    private  String BookPath, BookName;
    private  PDFView pdfView;

    private TextView Title_Text;
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

        pdfView = (PDFView)findViewById(R.id.pdfView);
        pdfView.fromAsset("Data/"+BookPath)
                .enableSwipe(true)
                .swipeHorizontal(true)
                .onPageChange(this)
                .pageSnap(true)
                .autoSpacing(true)
                .pageFling(true)
                .pageFitPolicy(FitPolicy.BOTH)
                .load();
    }

    @Override
    public void onPageChanged(int page, int pageCount) {

    }

    Button.OnClickListener mClickListener = new View.OnClickListener() {
        public void onClick(View v) {
            onBackPressed();
        }
    };
}
