package bookviewer.bookviewer.com.bookviewer;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.bq.markerseekbar.MarkerSeekBar;
import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener;
import com.github.barteksc.pdfviewer.listener.OnPageScrollListener;
import com.github.barteksc.pdfviewer.util.FitPolicy;

import com.ramotion.fluidslider.FluidSlider;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import bookviewer.bookviewer.com.bookviewer.Data.BookData;
import bookviewer.bookviewer.com.bookviewer.Data.DataMgr;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;

public class ViewActivity extends AppCompatActivity implements OnPageChangeListener, OnLoadCompleteListener, OnPageScrollListener {
    private  String BookPath, BookName;
    private  PDFView pdfView;

    MarkerSeekBar bar1;
    private Camera mCamera;
    public  CameraPreView mPreview;

    TextView timerText, pageText, pageTitle, pageTime;
    static int counter = 5;
    static boolean bFace = true;

    public Toolbar TopBar;
    private Timer timer;

    private FloatingActionButton fab;

    @Override
    public void onDestroy()
    {
        timer.cancel();
        timer.purge();
        timer = null;
        super.onDestroy();
    }

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);

        if(checkCameraHardware(getApplicationContext()))
        {
            mCamera = getCameraInstance();

            mPreview = new CameraPreView(this, this, mCamera);
            FrameLayout preview = (FrameLayout)findViewById(R.id.camera_preview);
            preview.addView(mPreview);
        }


        timerText = (TextView)findViewById(R.id.timer);
        timerText.setVisibility(View.INVISIBLE);

        pageText = (TextView)findViewById(R.id.Page);
        pageTitle = (TextView)findViewById(R.id.BookTitle);

        pageTime = (TextView)findViewById(R.id.PageTime);

        TimerTask timertask = new TimerTask() {
            @Override
            public void run() {
                Message msg = handler.obtainMessage();
                handler.sendMessage(msg);
            }
        };

        timer = new Timer();
        timer.schedule(timertask, 0, 1000);


        bar1 = (MarkerSeekBar)findViewById(R.id.bar1);
        assert bar1 != null;
        bar1.setProgressAdapter(new MarkerSeekBar.ProgressAdapter() {
            @SuppressLint("DefaultLocale")
            @Override
            public String toText(int progress) {
                //pdfView.jumpTo(progress);
                return String.format("%d", progress);
            }

            @Override
            public String onMeasureLongestText(int seekBarMax) {
                return toText(seekBarMax);
            }
        });

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setVisibility(View.INVISIBLE);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TopBar.isShown()) {
                    TopBar.setVisibility(View.GONE);
                } else
                    TopBar.setVisibility(View.VISIBLE);
            }
        });

        Intent intent = getIntent();
        BookPath = intent.getStringExtra("Path");
        BookName = intent.getStringExtra("Title");

        pageTitle.setText(BookName);

        TopBar = (Toolbar) findViewById(R.id.toolbar);
        TopBar.setTitle(BookName);
        setSupportActionBar(TopBar);

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

        /*pdfView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(TopBar.isShown()) {
                    TopBar.setVisibility(View.INVISIBLE);
                } else
                    TopBar.setVisibility(View.VISIBLE);
                return false;
            }
        });*/

        CommonFunc.getInstance().ShowProgressDialog(ViewActivity.this);

    }


    @Override
    public void onPageChanged(int page, int pageCount) {

        final int CurrPage = page + 1;
        bar1.setProgress(CurrPage);
        pageText.setText("페이지 " + CurrPage  + "/" + pageCount);

        if(counter  <= 0)
        {
            counter = 5;
            if(CurrPage == 4)
            {
                CommonFunc.ShowQuestionPopup_Listener_1 listener = new CommonFunc.ShowQuestionPopup_Listener_1() {
                    @Override
                    public void Listener(String correctStr) {
                        if(correctStr.equals("1"))
                        {
                            CommonFunc.getInstance().ShowToast(getApplicationContext(), "정답입니다.", true);
                        }
                        else
                        {
                            pdfView.jumpTo(0);
                            CommonFunc.getInstance().ShowToast(getApplicationContext(), "오답입니다.", true);
                        }
                    }
                };

                CommonFunc.getInstance().ShowQuestionPopup_1(getApplicationContext(), listener, "문제 입니다.");
            }
            else if(CurrPage == 8)
            {
                CommonFunc.ShowQuestionPopup_Listener_2 listener = new CommonFunc.ShowQuestionPopup_Listener_2() {
                    @Override
                    public void Listener(int correctValue) {
                        if(correctValue == 3)
                        {
                            CommonFunc.getInstance().ShowToast(getApplicationContext(), "정답입니다.", true);
                        }
                        else
                        {
                            pdfView.jumpTo(0);
                            CommonFunc.getInstance().ShowToast(getApplicationContext(), "오답입니다.", true);
                        }
                    }
                };
                CommonFunc.getInstance().ShowQuestionPopup_2(getApplicationContext(), listener, "문제 입니다.");
            }
        }

    }

    @Override
    public void onPageScrolled(int page, float positionOffset) {

        final int CurrPage = page;
        if( counter > 0)
        {
            pdfView.jumpTo(page - 1);
        }

    }

    @Override
    public void loadComplete(int nbPages) {
        bar1.setMax(nbPages);
        CommonFunc.getInstance().HideProgressDialog();
    }

    static class FaceDetect implements Camera.FaceDetectionListener {
        @Override
        public void onFaceDetection(Camera.Face[] faces, Camera camera) {
            Log.d("FaceDetection", "face not detected");

            if (faces.length > 0) {
                Log.d("FaceDetection", "face detected: " + faces.length +
                        " Face 1 Location X: " + faces[0].rect.centerX() +
                        "Y: " + faces[0].rect.centerY());
                bFace = true;
            }
        }
    }

    private boolean checkCameraHardware(Context context) {
        if (context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)){
            // this device has a camera
            return true;
        } else {
            // no camera on this device
            return false;
        }
    }

    final Handler handler = new Handler() {
        public void handleMessage(Message msg) {

            if(bFace)
            {
                timerText.setVisibility(View.INVISIBLE);

                ArrayList<Integer> recentBookList = DataMgr.getInstance().getRecentBookLocalData();
                BookData data = DataMgr.getInstance().myData.getBookData(recentBookList.get(0));

                //((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(data.bookName + "읽고 있는 중입니다" + String.valueOf(counter) + "초");

                TopBar.setTitle(data.bookName + "  " + String.valueOf(counter) + "초");
                pageTime.setText(" 남은 시간 : " + counter + "초");
                counter--;

            }
            else
            {
                timerText.setVisibility(View.VISIBLE);
                counter = 5;
            }

            bFace = false;

            if(counter <= 0)
            {
                pdfView.setSwipeEnabled(true);
                counter = 0;
            }
            else
                pdfView.setSwipeEnabled(false);
        }
    };

    public static Camera getCameraInstance(){
        Camera c = null;
        try {
            c = Camera.open(Camera.CameraInfo.CAMERA_FACING_FRONT); // attempt to get a Camera instance
        }
        catch (Exception e){
            // Camera is not available (in use or does not exist)
        }
        return c; // returns null if camera is unavailable
    }
}
