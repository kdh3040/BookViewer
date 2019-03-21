package ttuktak.bookviewer.com.bookviewer;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.bq.markerseekbar.MarkerSeekBar;
import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener;
import com.github.barteksc.pdfviewer.listener.OnPageScrollListener;
import com.github.barteksc.pdfviewer.util.FitPolicy;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import ttuktak.bookviewer.com.bookviewer.Data.BookData;
import ttuktak.bookviewer.com.bookviewer.Data.DataMgr;


public class BookViewerView extends Fragment implements OnPageChangeListener, OnLoadCompleteListener, OnPageScrollListener {

    View fragView;
    Context context;
    private PDFView pdfView;
    private  String BookPath, BookName;
    MarkerSeekBar bar1;

    private Camera mCamera;
    public  CameraPreView mPreview;

    TextView timerText;
    static int counter = 5;
    static boolean bFace = true;



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

                ArrayList<BookData> recentBookList = DataMgr.getInstance().myData.getRecentReadBookData();
                BookData data = recentBookList.get(0);
                //((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(data.bookName + "읽고 있는 중입니다" + String.valueOf(counter) + "초");

                ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(data.bookName + "  " + String.valueOf(counter) + "초");
                timerText.setText("읽고 있는 중 " + String.valueOf(counter) + "초 남음");
                counter--;
            }
            else
            {
                timerText.setVisibility(View.VISIBLE);
                timerText.setText("얼굴 인식 안됨");
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


    public BookViewerView() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        context = getActivity();
        fragView = inflater.inflate(R.layout.fragment_book_viewer_view,container,false);

        if(checkCameraHardware(context))
        {
            mCamera = getCameraInstance();

            //mPreview = new CameraPreView(context, getActivity(), mCamera);
            FrameLayout preview = (FrameLayout)fragView.findViewById(R.id.camera_preview);
            preview.addView(mPreview);
        }

        timerText = (TextView)fragView.findViewById(R.id.timer);
        timerText.setVisibility(View.INVISIBLE);

        TimerTask timertask = new TimerTask() {
            @Override
            public void run() {
                Message msg = handler.obtainMessage();
                handler.sendMessage(msg);
            }
        };

        Timer timer = new Timer();
        timer.schedule(timertask, 0, 1000);

        BookPath = "Book_1.pdf";

        bar1 = (MarkerSeekBar)fragView.findViewById(R.id.bar1);
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


        pdfView = (PDFView)fragView.findViewById(R.id.pdfView);
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

        CommonFunc.getInstance().ShowProgressDialog(context);



        return fragView;
    }

    @Override
    public void onPageChanged(int page, int pageCount) {

        final int CurrPage = page;
        bar1.setProgress(CurrPage);

        if(counter  <= 0)
        {
            counter = 5;
            if(page == 4)
            {
                CommonFunc.ShowQuestionPopup_Listener_1 listener = new CommonFunc.ShowQuestionPopup_Listener_1() {
                    @Override
                    public void Listener(String correctStr) {
                        if(correctStr.equals("1"))
                        {
                            CommonFunc.getInstance().ShowToast(context, "정답입니다.", true);
                        }
                        else
                        {
                            pdfView.jumpTo(0);
                            CommonFunc.getInstance().ShowToast(context, "오답입니다.", true);
                        }
                    }
                };

                CommonFunc.getInstance().ShowQuestionPopup_1(context, listener, "문제 입니다.");
            }
            else if(page == 8)
            {
                CommonFunc.ShowQuestionPopup_Listener_2 listener = new CommonFunc.ShowQuestionPopup_Listener_2() {
                    @Override
                    public void Listener(int correctValue) {
                        if(correctValue == 3)
                        {
                            CommonFunc.getInstance().ShowToast(context, "정답입니다.", true);
                        }
                        else
                        {
                            pdfView.jumpTo(0);
                            CommonFunc.getInstance().ShowToast(context, "오답입니다.", true);
                        }
                    }
                };
                CommonFunc.getInstance().ShowQuestionPopup_2(context, listener, "문제 입니다.");
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
}
