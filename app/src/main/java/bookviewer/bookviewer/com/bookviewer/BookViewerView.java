package bookviewer.bookviewer.com.bookviewer;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener;
import com.github.barteksc.pdfviewer.util.FitPolicy;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link BookViewerView.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link BookViewerView#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BookViewerView extends Fragment implements OnPageChangeListener, OnLoadCompleteListener {

    View fragView;
    Context context;
    private PDFView pdfView;
    private  String BookPath, BookName;

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

        BookPath = "Book_1.pdf";

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

    @Override
    public void loadComplete(int nbPages) {
        CommonFunc.getInstance().HideProgressDialog();
    }
}
