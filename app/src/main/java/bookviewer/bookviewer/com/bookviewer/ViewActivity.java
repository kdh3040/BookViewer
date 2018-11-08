package bookviewer.bookviewer.com.bookviewer;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.bq.markerseekbar.MarkerSeekBar;
import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener;
import com.github.barteksc.pdfviewer.util.FitPolicy;

import com.ramotion.fluidslider.FluidSlider;

import kotlin.Unit;
import kotlin.jvm.functions.Function0;

public class ViewActivity extends AppCompatActivity implements OnPageChangeListener, OnLoadCompleteListener {
    private  String BookPath, BookName;
    private  PDFView pdfView;

    private TextView Title_Text, Title_Page, Menu_scroll;
    private Button Title_Button;

    //MarkerSeekBar bar1;
    ProgressDialog dialog;

    static FluidSlider slider;
    int max = 358;
    int min = 1;
    int total = max - min;

    @SuppressWarnings("Convert2Lambda")
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
                .onLoad(this)
                .pageSnap(true)
                .autoSpacing(true)
                .pageFling(true)
                .pageFitPolicy(FitPolicy.BOTH)
              //  .scrollHandle(new DefaultScrollHandle(this))
                .load();


        /*bar1 = (MarkerSeekBar) findViewById(R.id.bar1);
        assert bar1 != null;
        bar1.setProgressAdapter(new MarkerSeekBar.ProgressAdapter() {
            @SuppressLint("DefaultLocale")
            @Override
            public String toText(int progress) {
                //pdfView.jumpTo(progress);
                return String.format(" ¯\\_(ツ)_/¯ %d ", progress);
            }

            @Override
            public String onMeasureLongestText(int seekBarMax) {
                return toText(seekBarMax);
            }
        });*/

        CommonFunc.getInstance().ShowProgressDialog(this);

        slider = findViewById(R.id.fluidSlider);
        slider.setPosition(0f);
        slider.setStartText(String.valueOf(1));
        slider.setEndText(String.valueOf(358));


        slider.setBeginTrackingListener(new Function0<Unit>() {
            @Override
            public Unit invoke() {
                return Unit.INSTANCE;
            }
        });

        slider.setEndTrackingListener(new Function0<Unit>() {
            @Override
            public Unit invoke() {
                return Unit.INSTANCE;
            }
        });

        // Java 8 lambda
        slider.setPositionListener(pos -> {
            Log.d("asdasd", min + "    sasd   " + max + " pos : " + pos );
            final String value = String.valueOf( (int)(min + total * pos) );
            slider.setBubbleText(value);
            return Unit.INSTANCE;
        });

        
    }

    @Override
    public void onPageChanged(int page, int pageCount) {
        Title_Page.setText((String.format("%s / %s", page + 1, pageCount)));
        //bar1.setProgress(page);
        float start =page;
        float end = pageCount;

        slider.setPosition((start + 1) /end);

        final int CurrPage = page;
        if(page == 4)
        {
            CommonFunc.ShowQuestionPopup_Listener_1 listener = new CommonFunc.ShowQuestionPopup_Listener_1() {
                @Override
                public void Listener(String correctStr) {
                    if(correctStr.equals("1"))
                    {
                        CommonFunc.getInstance().ShowToast(ViewActivity.this, "정답입니다.", true);
                    }
                    else
                    {
                        pdfView.jumpTo(0);
                        CommonFunc.getInstance().ShowToast(ViewActivity.this, "오답입니다.", true);
                    }
                }
            };

            CommonFunc.getInstance().ShowQuestionPopup_1(ViewActivity.this, listener, "문제 입니다.");
        }
        else if(page == 8)
        {
            CommonFunc.ShowQuestionPopup_Listener_2 listener = new CommonFunc.ShowQuestionPopup_Listener_2() {
                @Override
                public void Listener(int correctValue) {
                    if(correctValue == 3)
                    {
                        CommonFunc.getInstance().ShowToast(ViewActivity.this, "정답입니다.", true);
                    }
                    else
                    {
                        pdfView.jumpTo(0);
                        CommonFunc.getInstance().ShowToast(ViewActivity.this, "오답입니다.", true);
                    }
                }
            };
            CommonFunc.getInstance().ShowQuestionPopup_2(ViewActivity.this, listener, "문제 입니다.");
        }
    }


    Button.OnClickListener mClickListener = new View.OnClickListener() {
        public void onClick(View v) {
            onBackPressed();
        }
    };

    @Override
    public void loadComplete(int nbPages) {
       // bar1.setMax(nbPages);
        CommonFunc.getInstance().HideProgressDialog();

        max = nbPages;
        min = 1;
        total = nbPages;



        InitSlider(min, max);



    }

    public void InitSlider(int min, int max)
    {


    }
}
