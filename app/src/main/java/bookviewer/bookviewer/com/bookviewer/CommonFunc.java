package bookviewer.bookviewer.com.bookviewer;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import bookviewer.bookviewer.com.bookviewer.Data.DataMgr;

public class CommonFunc {
    private static CommonFunc _Instance;

    public static CommonFunc getInstance() {
        if (_Instance == null)
            _Instance = new CommonFunc();

        return _Instance;
    }

    private CommonFunc()
    {
    }
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


    private boolean bLock;
    public void SetLock(boolean lock)
    {
        bLock = lock;
    }
    public boolean GetLock()
    {
        return bLock;
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

    public void ShowBoardWritePopup(Context context, final int BookId)
    {
        EditText BoardTitle, BoardDesc;
        Button WriteYes, WriteNo;

        View v = LayoutInflater.from(context).inflate(R.layout.popup_book_board_write, null, false);

        BoardTitle = v.findViewById(R.id.board_write_title);
        BoardDesc = v.findViewById(R.id.board_write_desc);
        WriteYes = v.findViewById(R.id.board_write_yes);
        WriteNo = v.findViewById(R.id.board_write_no);


        final AlertDialog dialog = new AlertDialog.Builder(context).setView(v).create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();

        WriteYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DataMgr.getInstance().TempData.addBookBoardData(BookId, BoardTitle.getText().toString(), BoardDesc.getText().toString());

                dialog.dismiss();
            }
        });
        WriteNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // TEST 함수
    public static String randomFullName() {
        List<String> 성 = Arrays.asList("김", "이", "박", "최", "정", "강", "조", "윤", "장", "임", "한", "오", "서", "신", "권", "황", "안",
                "송", "류", "전", "홍", "고", "문", "양", "손", "배", "조", "백", "허", "유", "남", "심", "노", "정", "하", "곽", "성", "차", "주",
                "우", "구", "신", "임", "나", "전", "민", "유", "진", "지", "엄", "채", "원", "천", "방", "공", "강", "현", "함", "변", "염", "양",
                "변", "여", "추", "노", "도", "소", "신", "석", "선", "설", "마", "길", "주", "연", "방", "위", "표", "명", "기", "반", "왕", "금",
                "옥", "육", "인", "맹", "제", "모", "장", "남", "탁", "국", "여", "진", "어", "은", "편", "구", "용");
        List<String> 이름 = Arrays.asList("가", "강", "건", "경", "고", "관", "광", "구", "규", "근", "기", "길", "나", "남", "노", "누", "다",
                "단", "달", "담", "대", "덕", "도", "동", "두", "라", "래", "로", "루", "리", "마", "만", "명", "무", "문", "미", "민", "바", "박",
                "백", "범", "별", "병", "보", "빛", "사", "산", "상", "새", "서", "석", "선", "설", "섭", "성", "세", "소", "솔", "수", "숙", "순",
                "숭", "슬", "승", "시", "신", "아", "안", "애", "엄", "여", "연", "영", "예", "오", "옥", "완", "요", "용", "우", "원", "월", "위",
                "유", "윤", "율", "으", "은", "의", "이", "익", "인", "일", "잎", "자", "잔", "장", "재", "전", "정", "제", "조", "종", "주", "준",
                "중", "지", "진", "찬", "창", "채", "천", "철", "초", "춘", "충", "치", "탐", "태", "택", "판", "하", "한", "해", "혁", "현", "형",
                "혜", "호", "홍", "화", "환", "회", "효", "훈", "휘", "희", "운", "모", "배", "부", "림", "봉", "혼", "황", "량", "린", "을", "비",
                "솜", "공", "면", "탁", "온", "디", "항", "후", "려", "균", "묵", "송", "욱", "휴", "언", "령", "섬", "들", "견", "추", "걸", "삼",
                "열", "웅", "분", "변", "양", "출", "타", "흥", "겸", "곤", "번", "식", "란", "더", "손", "술", "훔", "반", "빈", "실", "직", "흠",
                "흔", "악", "람", "뜸", "권", "복", "심", "헌", "엽", "학", "개", "롱", "평", "늘", "늬", "랑", "얀", "향", "울", "련");
        Collections.shuffle(성);
        Collections.shuffle(이름);
        return 성.get(0) + 이름.get(0) + 이름.get(1);
    }

    public static String randomLastName() {

        List<String> 이름 = Arrays.asList("가", "강", "건", "경", "고", "관", "광", "구", "규", "근", "기", "길", "나", "남", "노", "누", "다",
                "단", "달", "담", "대", "덕", "도", "동", "두", "라", "래", "로", "루", "리", "마", "만", "명", "무", "문", "미", "민", "바", "박",
                "백", "범", "별", "병", "보", "빛", "사", "산", "상", "새", "서", "석", "선", "설", "섭", "성", "세", "소", "솔", "수", "숙", "순",
                "숭", "슬", "승", "시", "신", "아", "안", "애", "엄", "여", "연", "영", "예", "오", "옥", "완", "요", "용", "우", "원", "월", "위",
                "유", "윤", "율", "으", "은", "의", "이", "익", "인", "일", "잎", "자", "잔", "장", "재", "전", "정", "제", "조", "종", "주", "준",
                "중", "지", "진", "찬", "창", "채", "천", "철", "초", "춘", "충", "치", "탐", "태", "택", "판", "하", "한", "해", "혁", "현", "형",
                "혜", "호", "홍", "화", "환", "회", "효", "훈", "휘", "희", "운", "모", "배", "부", "림", "봉", "혼", "황", "량", "린", "을", "비",
                "솜", "공", "면", "탁", "온", "디", "항", "후", "려", "균", "묵", "송", "욱", "휴", "언", "령", "섬", "들", "견", "추", "걸", "삼",
                "열", "웅", "분", "변", "양", "출", "타", "흥", "겸", "곤", "번", "식", "란", "더", "손", "술", "훔", "반", "빈", "실", "직", "흠",
                "흔", "악", "람", "뜸", "권", "복", "심", "헌", "엽", "학", "개", "롱", "평", "늘", "늬", "랑", "얀", "향", "울", "련");
        Collections.shuffle(이름);
        return 이름.get(0) + 이름.get(1);
    }

}

