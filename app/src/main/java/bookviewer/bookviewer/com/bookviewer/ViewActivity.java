package bookviewer.bookviewer.com.bookviewer;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.hardware.fingerprint.FingerprintManager;
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

import com.multidots.fingerprintauth.AuthErrorCodes;
import com.multidots.fingerprintauth.FingerPrintAuthCallback;
import com.multidots.fingerprintauth.FingerPrintAuthHelper;
import com.multidots.fingerprintauth.FingerPrintUtils;
import com.ramotion.fluidslider.FluidSlider;

import java.security.KeyStore;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;

import bookviewer.bookviewer.com.bookviewer.Data.BookData;
import bookviewer.bookviewer.com.bookviewer.Data.DataMgr;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;

public class ViewActivity extends AppCompatActivity implements OnPageChangeListener, OnLoadCompleteListener, OnPageScrollListener, FingerPrintAuthCallback {
    private String BookPath, BookName;
    private PDFView pdfView;

    MarkerSeekBar bar1;
    private Camera mCamera;
    public CameraPreView mPreview;

    TextView timerText, pageText, pageTitle, pageTime, pageFinger;
    static int counter = 3;
    static boolean bFace = true;
    boolean bFingerPrint = false;

    public Toolbar TopBar;
    private Timer timer;

    private FloatingActionButton fab;
    private Context mContext;


    @Override
    public void onDestroy() {
        timer.cancel();
        timer.purge();
        timer = null;
        mFingerPrintAuthHelper.stopAuth();
        super.onDestroy();
    }
    FingerPrintAuthHelper mFingerPrintAuthHelper;
    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);

        mContext = ViewActivity.this;

        mFingerPrintAuthHelper = FingerPrintAuthHelper.getHelper(this, this);



        if (checkCameraHardware(getApplicationContext())) {
            mCamera = getCameraInstance();

            mPreview = new CameraPreView(this, this, mCamera);
            FrameLayout preview = (FrameLayout) findViewById(R.id.camera_preview);
            preview.addView(mPreview);
        }


        timerText = (TextView) findViewById(R.id.timer);
        timerText.setVisibility(View.INVISIBLE);

        pageFinger = (TextView) findViewById(R.id.FingerPrint);
        pageFinger.setVisibility(View.VISIBLE);

        pageText = (TextView) findViewById(R.id.Page);
        pageTitle = (TextView) findViewById(R.id.BookTitle);

        pageTime = (TextView) findViewById(R.id.PageTime);

        TimerTask timertask = new TimerTask() {
            @Override
            public void run() {
                Message msg = handler.obtainMessage();
                handler.sendMessage(msg);
            }
        };

        timer = new Timer();
        timer.schedule(timertask, 0, 1000);

        TimerTask timerFingerTask = new TimerTask() {
            @Override
            public void run() {
                Message msg = handlerFinger.obtainMessage();
                handlerFinger.sendMessage(msg);
            }
        };

        timer = new Timer();
        timer.schedule(timerFingerTask, 0, 5000);


        bar1 = (MarkerSeekBar) findViewById(R.id.bar1);
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
                FingerPrintUtils.openSecuritySettings(ViewActivity.this);
            }
        });

        Intent intent = getIntent();
        BookPath = intent.getStringExtra("Path");
        BookName = intent.getStringExtra("Title");

        pageTitle.setText(BookName);

        TopBar = (Toolbar) findViewById(R.id.toolbar);
        TopBar.setTitle(BookName);
        setSupportActionBar(TopBar);

        pdfView = (PDFView) findViewById(R.id.pdfView);
        pdfView.fromAsset("Data/" + BookPath)
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

        //mFingerPrintAuthHelper.startAuth();

        bar1.setProgress(page);
        final int CurrPage = page + 1;
        pageText.setText("페이지 " + CurrPage + "/" + pageCount);

        if (counter <= 0) {
            counter = 3;
            if (CurrPage == 4) {
                CommonFunc.ShowQuestionPopup_Listener_1 listener = new CommonFunc.ShowQuestionPopup_Listener_1() {
                    @Override
                    public void Listener(String correctStr) {
                        if (correctStr.equals("1")) {
                            CommonFunc.getInstance().ShowToast(mContext, "정답입니다.", true);
                        } else {
                            pdfView.jumpTo(0);
                            CommonFunc.getInstance().ShowToast(mContext, "오답입니다.", true);
                        }
                    }
                };

                CommonFunc.getInstance().ShowQuestionPopup_1(mContext, listener, "문제 입니다.");
            } else if (CurrPage == 8) {
                CommonFunc.ShowQuestionPopup_Listener_2 listener = new CommonFunc.ShowQuestionPopup_Listener_2() {
                    @Override
                    public void Listener(int correctValue) {
                        if (correctValue == 3) {
                            CommonFunc.getInstance().ShowToast(mContext, "정답입니다.", true);
                        } else {
                            pdfView.jumpTo(0);
                            CommonFunc.getInstance().ShowToast(mContext, "오답입니다.", true);
                        }
                    }
                };
                CommonFunc.getInstance().ShowQuestionPopup_2(mContext, listener, "문제 입니다.");
            }
        }

    }

    @Override
    public void onPageScrolled(int page, float positionOffset) {

        final int CurrPage = page;
        if (counter > 0) {
            pdfView.jumpTo(page - 1);
        }

    }

    @Override
    public void loadComplete(int nbPages) {
        bar1.setMax(nbPages);
        CommonFunc.getInstance().HideProgressDialog();
    }

    @Override
    public void onNoFingerPrintHardwareFound() {
        int aaa = 0;

    }

    @Override
    public void onNoFingerPrintRegistered() {
        int aaa = 0;
    }

    @Override
    public void onBelowMarshmallow() {
        int aaa = 0;
    }

    @Override
    public void onAuthSuccess(FingerprintManager.CryptoObject cryptoObject) {
        int aaa = 0;
        pageFinger.setVisibility(View.INVISIBLE);
        bFingerPrint = true;
    }

    @Override
    public void onAuthFailed(int errorCode, String errorMessage) {

        switch (errorCode) {    //Parse the error code for recoverable/non recoverable error.
            case AuthErrorCodes.CANNOT_RECOGNIZE_ERROR:
                //Cannot recognize the fingerprint scanned.

                pageFinger.setVisibility(View.VISIBLE);
                pageFinger.setText("등록된 지문이 아닙니다");
                bFingerPrint = false;

                    //pageFinger.setVisibility(View.VISIBLE);
                  //  pageFinger.setText("등록된 지문이 아닙니다");
                //    bFingerPrint = false;
                   // CheckFingerPrint();

                break;
            case AuthErrorCodes.NON_RECOVERABLE_ERROR:
                //This is not recoverable error. Try other options for user authentication. like pin, password.
              //  pageFinger.setVisibility(View.VISIBLE);
                //CheckFingerPrint();
                break;
            case AuthErrorCodes.RECOVERABLE_ERROR:
                pageFinger.setVisibility(View.VISIBLE);
                pageFinger.setText("올바른 위치에 지문 올려주세요");
                CheckFingerPrint();
                break;
        }
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

    public void CheckFingerPrint()
    {
        try {
            Thread.sleep(5000);
            mFingerPrintAuthHelper.startAuth();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
    private boolean checkCameraHardware(Context context) {
        if (context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
            // this device has a camera
            return true;
        } else {
            // no camera on this device
            return false;
        }
    }

    final Handler handlerFinger = new Handler() {
        public void handleMessage(Message msg) {
            if(bFingerPrint == false)
                mFingerPrintAuthHelper.startAuth();
        }
    };


    final Handler handler = new Handler() {
        public void handleMessage(Message msg) {

            if (bFace && bFingerPrint) {
                timerText.setVisibility(View.INVISIBLE);

                ArrayList<Integer> recentBookList = DataMgr.getInstance().getRecentBookLocalData();
                BookData data = DataMgr.getInstance().myData.getBookData(recentBookList.get(0));

                //((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(data.bookName + "읽고 있는 중입니다" + String.valueOf(counter) + "초");

                TopBar.setTitle(data.bookName + "  " + String.valueOf(counter) + "초");
                pageTime.setText(" 남은 시간 : " + counter + "초");
                counter--;

            } else if(bFace == false){
                timerText.setVisibility(View.VISIBLE);
                counter = 5;
            }else if(bFingerPrint == false){
                pageFinger.setVisibility(View.VISIBLE);
                counter = 5;
            }

            bFace = false;

            if (counter <= 0) {
                pdfView.setSwipeEnabled(true);
                counter = 0;
            } else
                pdfView.setSwipeEnabled(false);
        }
    };

    public static Camera getCameraInstance() {
        Camera c = null;
        try {
            c = Camera.open(Camera.CameraInfo.CAMERA_FACING_FRONT); // attempt to get a Camera instance
        } catch (Exception e) {
            // Camera is not available (in use or does not exist)
        }
        return c; // returns null if camera is unavailable
    }
}
