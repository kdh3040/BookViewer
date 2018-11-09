package bookviewer.bookviewer.com.bookviewer;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AlertDialog;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CommonFunc {
    private static CommonFunc _Instance;

    public static CommonFunc getInstance() {
        if (_Instance == null)
            _Instance = new CommonFunc();

        return _Instance;
    }

    private CommonFunc()
    {
        BookData data_1 = new BookData();
        data_1.Buy = true;
        data_1.ImgIdx = R.drawable.book_1;
        data_1.NameIdx = R.string.book_1;
        BookDataList.add(data_1);

        BookData data_2 = new BookData();
        data_2.Buy = false;
        data_2.ImgIdx = R.drawable.book_2;
        data_2.NameIdx = R.string.book_2;
        BookDataList.add(data_2);

        BookData data_3 = new BookData();
        data_3.Buy = false;
        data_3.ImgIdx = R.drawable.book_3;
        data_3.NameIdx = R.string.book_3;
        BookDataList.add(data_3);

        BookData data_4 = new BookData();
        data_4.Buy = false;
        data_4.ImgIdx = R.drawable.book_4;
        data_4.NameIdx = R.string.book_4;
        BookDataList.add(data_4);

        BookData data_5 = new BookData();
        data_5.Buy = true;
        data_5.ImgIdx = R.drawable.book_5;
        data_5.NameIdx = R.string.book_5;
        BookDataList.add(data_5);

        BookData data_6 = new BookData();
        data_6.Buy = false;
        data_6.ImgIdx = R.drawable.book_6;
        data_6.NameIdx = R.string.book_6;
        BookDataList.add(data_6);

        BookData data_7 = new BookData();
        data_7.Buy = true;
        data_7.ImgIdx = R.drawable.book_7;
        data_7.NameIdx = R.string.book_7;
        BookDataList.add(data_7);
    }

    public List<BookData> BookDataList = new ArrayList<>();

    private  ProgressDialog dialog;

    public void ShowProgressDialog(Context context)
    {
        dialog = new ProgressDialog(context);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setMessage("불러오는 중");
        dialog.show();
    }

    public void HideProgressDialog()
    {
        dialog.dismiss();
    }

    private int Width, Height;

    public void SetDisplayWidth(int width)
    {
        Width = width;
    }
    public int GetDisplayWidth()
    {
        return Width;
    }
    public void SetDisplayHeight(int height)
    {
        Height = height;
    }
    public int GetDisplayHeight()
    {
        return Height;
    }

    public void ShowToast(Context context, String msg, boolean shortView)
    {
        if(shortView)
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
    }

    public interface ShowPopup_Listener {
        void Listener();
    }

    public void ShowDefaultPopup(Context context, String centerDesc) {
        ShowDefaultPopup(context, null, null, "알림", centerDesc, "확인", "");
    }
    public void ShowDefaultPopup(Context context, final ShowPopup_Listener listenerYes, final ShowPopup_Listener listenerNo, String title, String centerDesc, String yesDesc, String noDesc) {
        TextView Title, CenterDesc;
        Button YesButton, NoButton;

        View v = LayoutInflater.from(context).inflate(R.layout.popup_default, null, false);

        Title = (TextView) v.findViewById(R.id.popup_title);
        CenterDesc = (TextView) v.findViewById(R.id.popup_msg);
        YesButton = (Button) v.findViewById(R.id.popup_yes);
        NoButton = (Button) v.findViewById(R.id.popup_no);

        Title.setText(title);
        CenterDesc.setText(centerDesc);

        final AlertDialog dialog = new AlertDialog.Builder(context).setView(v).create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();

        YesButton.setVisibility(View.VISIBLE);
        NoButton.setVisibility(View.VISIBLE);

        if(noDesc == null || noDesc.equals(""))
            NoButton.setVisibility(View.GONE);

        YesButton.setText(yesDesc);
        NoButton.setText(noDesc);

        YesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listenerYes != null)
                    listenerYes.Listener();
                dialog.dismiss();
            }
        });
        NoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listenerNo != null)
                    listenerNo.Listener();
                dialog.dismiss();
            }
        });
    }

    public interface ShowQuestionPopup_Listener_1 {
        void Listener(String correctStr);
    }

    public void ShowQuestionPopup_1(Context context, final ShowQuestionPopup_Listener_1 listenerYes, String centerDesc) {
        TextView Title, CenterDesc;
        Button YesButton;
        final EditText CorrectText;

        View v = LayoutInflater.from(context).inflate(R.layout.popup_question_1, null, false);

        Title = (TextView) v.findViewById(R.id.question_title);
        CenterDesc = (TextView) v.findViewById(R.id.question_msg);
        YesButton = (Button) v.findViewById(R.id.question_submit);
        CorrectText = (EditText) v.findViewById(R.id.question_correct);
        Title.setText("문제");
        CenterDesc.setText(centerDesc);


        final AlertDialog dialog = new AlertDialog.Builder(context).setView(v).create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();

        YesButton.setText("제출");

        YesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listenerYes != null)
                    listenerYes.Listener(CorrectText.getText().toString());
                dialog.dismiss();
            }
        });
    }

    public interface ShowQuestionPopup_Listener_2 {
        void Listener(int correctValue);
    }

    public void ShowQuestionPopup_2(Context context, final ShowQuestionPopup_Listener_2 listenerYes, String centerDesc) {
        TextView Title, CenterDesc;
        Button YesButton;

        View v = LayoutInflater.from(context).inflate(R.layout.popup_question_2, null, false);

        final List<RadioButton> ExampleBtnList = new ArrayList<>();
        ExampleBtnList.add((RadioButton) v.findViewById(R.id.question_example_1));
        ExampleBtnList.add((RadioButton) v.findViewById(R.id.question_example_2));
        ExampleBtnList.add((RadioButton) v.findViewById(R.id.question_example_3));
        ExampleBtnList.add((RadioButton) v.findViewById(R.id.question_example_4));

        Title = (TextView) v.findViewById(R.id.question_title);
        CenterDesc = (TextView) v.findViewById(R.id.question_msg);
        YesButton = (Button) v.findViewById(R.id.question_submit);
        Title.setText("문제");
        CenterDesc.setText(centerDesc);


        final AlertDialog dialog = new AlertDialog.Builder(context).setView(v).create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();

        YesButton.setText("제출");

        YesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listenerYes != null)
                {
                    int index = 1;
                    for(RadioButton object : ExampleBtnList) {
                        RadioButton element = (RadioButton) object;
                        if(element.isChecked())
                            listenerYes.Listener(index);
                        index++;
                    }
                }
                dialog.dismiss();
            }
        });
    }
}

