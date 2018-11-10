package bookviewer.bookviewer.com.bookviewer.Data;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import bookviewer.bookviewer.com.bookviewer.R;

public class DataMgr {

    private static DataMgr _Instance;

    public static DataMgr getInstance() {
        if (_Instance == null)
            _Instance = new DataMgr();

        return _Instance;
    }

    public TempFireBaseData TempData;

    public Map<Integer, BookLocalData> bookLocalDataList = new LinkedHashMap<>();
    public UserData myData = new UserData();

    private DataMgr()
    {
        TempData = new TempFireBaseData();
    }

    public ArrayList<SchoolCurriculumData> getSchoolCurriculumDataList(String SchoolCode)
    {
        ArrayList<SchoolCurriculumData> returnValue = new ArrayList<>();

        // TODO 임시
        TempFireBaseData.SchoolData userGroupData = TempData.schoolDataList.get(SchoolCode);
        for(int index = 0; index < userGroupData.schoolCurriculumIdList.size() ; ++index)
        {
            SchoolCurriculumData bookGroupData = getSchoolCurriculumData(userGroupData.schoolCurriculumIdList.get(index));
            if(bookGroupData == null)
                continue;

            returnValue.add(bookGroupData);
        }

        return returnValue;
    }

    public SchoolCurriculumData getSchoolCurriculumData(int SchoolCurriculumId)
    {
        // TODO 임시
        TempFireBaseData.SchoolCurriculumData tempSchoolCurriculumData = TempData.schoolCurriculumDataList.get(SchoolCurriculumId);
        if(tempSchoolCurriculumData == null)
            return null;

        SchoolCurriculumData schoolCurriculumData = new SchoolCurriculumData();
        schoolCurriculumData.schoolCurriculumId = tempSchoolCurriculumData.schoolCurriculumId;
        schoolCurriculumData.schoolCurriculumName = tempSchoolCurriculumData.schoolCurriculumName;
        schoolCurriculumData.bookIdList.addAll(tempSchoolCurriculumData.bookIdList);

        return schoolCurriculumData;
    }

    public ArrayList<BookData> getBookDataList(int SchoolCurriculumId)
    {
        ArrayList<BookData> returnValue = new ArrayList<>();

        // TODO 임시
        TempFireBaseData.SchoolCurriculumData schoolCurriculumData = TempData.schoolCurriculumDataList.get(SchoolCurriculumId);
        for(int index = 0; index < schoolCurriculumData.bookIdList.size() ; ++index)
        {
            TempFireBaseData.BookData TempbookData = TempData.bookDataList.get(schoolCurriculumData.bookIdList.get(index));

            BookData bookData = new BookData();
            bookData.bookId = TempbookData.bookId;
            bookData.bookName = TempbookData.bookName;
            bookData.questionIdList.addAll(TempbookData.questionIdList);
            returnValue.add(bookData);
        }

        return returnValue;
    }

    public QuestionData getQuestionData(int QuestionId)
    {
        ArrayList<BookData> returnValue = new ArrayList<>();

        // TODO 임시
        TempFireBaseData.QuestionData TempQuestionData = TempData.questionDataList.get(QuestionId);

        QuestionData questionData = new QuestionData();
        questionData.questionDesc = TempQuestionData.questionDesc;
        questionData.correctStr = TempQuestionData.correctStr;
        questionData.showPageCount = TempQuestionData.showPageCount;
        questionData.questionId = TempQuestionData.questionId;

        return questionData;
    }

    public void loadLocalData(Context ViewContext)
    {
        // 책 정보 로드
        SharedPreferences book_pref = ViewContext.getSharedPreferences("BookData", Context.MODE_PRIVATE);
        int index = 1;
        while (true)
        {
            String bookIdStr = getSharedPreferences_String(book_pref, "BookId_"+index);
            if(bookIdStr.isEmpty())
                break;

            BookLocalData bookLocalData = new BookLocalData();
            bookLocalData.bookId = Integer.parseInt(bookIdStr);
            bookLocalData.bookImgURL = getSharedPreferences_String(book_pref, "BookImg_"+index);
            bookLocalData.bookPDFFileName = getSharedPreferences_String(book_pref, "BookFile_"+index);
            bookLocalDataList.put(bookLocalData.bookId, bookLocalData);
        }

        // TODO 임시
        if(bookLocalDataList.size() <= 0)
        {
            // 어플 실행 시 저장된 기본 책 데이터가 없을시 데이터를 채워줘야 할듯
            for(int temp_index = 1 ; temp_index <= 7 ; ++temp_index)
            {
                BookLocalData bookLocalData = new BookLocalData();
                bookLocalData.bookId = temp_index;

                bookLocalData.ImgIdx = R.drawable.book_1;

                bookLocalDataList.put(temp_index, bookLocalData);
            }
        }


        // 내정보 로드
        SharedPreferences pref = ViewContext.getSharedPreferences("MyData", Context.MODE_PRIVATE);
        myData.init(getSharedPreferences_String(pref, "SchoolCode"), getSharedPreferences_String(pref, "Nickname"));
    }

    public void saveMyData(Context ViewContext)
    {
        // 내정보 저장
        SharedPreferences pref = ViewContext.getSharedPreferences("MyData", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("SchoolCode", myData.schoolCode);
        editor.putString("Nickname", myData.nickName);
        editor.commit();
    }

    public void saveBookLocalData(Context ViewContext)
    {
        SharedPreferences book_pref = ViewContext.getSharedPreferences("BookData", Context.MODE_PRIVATE);
        SharedPreferences.Editor book_editor = book_pref.edit();

        int index = 1;
        for(Integer key : bookLocalDataList.keySet())
        {
            BookLocalData bookLocalData = bookLocalDataList.get(key);
            book_editor.putString("BookId_"+index, String.valueOf(bookLocalData.bookId));
            book_editor.putString("BookFile_"+index, bookLocalData.bookPDFFileName);
            book_editor.putString("BookImg_"+index, bookLocalData.bookImgURL);
        }

        book_editor.commit();
    }

    private String getSharedPreferences_String(SharedPreferences Pref, String key)
    {
        return Pref.getString(key, "");
    }



}
