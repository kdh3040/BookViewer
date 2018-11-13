package bookviewer.bookviewer.com.bookviewer.Data;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Pair;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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

    public String getSchoolName(String SchoolCode)
    {
        TempFireBaseData.SchoolData userGroupData = TempData.schoolDataList.get(SchoolCode);
        return userGroupData.schoolName;
    }

    public BookLocalData getBookLocalData(int BookId)
    {
        return bookLocalDataList.get(BookId);
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
            bookData.bookAuthor = TempbookData.bookAuthor;
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

    public ArrayList<Integer> getRecentBookLocalData()
    {
        ArrayList<Integer> returnValue = new ArrayList<>();
        final ArrayList<Pair<Integer, Long>> tempList = new ArrayList<Pair<Integer, Long>>();
        for(Integer key: bookLocalDataList.keySet())
        {
            BookLocalData bookLocalData = bookLocalDataList.get(key);
            tempList.add(new Pair<>(bookLocalData.bookId, bookLocalData.recentReadTime));
        }

        Collections.sort(tempList, new Comparator<Pair<Integer, Long>>() {

            public int compare(Pair<Integer, Long> o1, Pair<Integer, Long> o2) {
                return o1.second > o2.second ? -1 : 1;
            }
        });

        for(int index = 0 ; index < tempList.size() ; ++index)
        {
            if(tempList.get(index).second <= 0)
                continue;
            returnValue.add(tempList.get(index).first);
        }

        return returnValue;
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
            bookLocalData.bookAllPage = getSharedPreferences_Int(book_pref, "BookAllPage_"+index);
            bookLocalData.recentPage = getSharedPreferences_Int(book_pref, "BookRecentPage_"+index);
            bookLocalData.recentReadTime = getSharedPreferences_Long(book_pref, "BookReadTime_"+index);
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
                bookLocalData.recentReadTime = temp_index;
                switch (temp_index)
                {
                    case 1: bookLocalData.ImgIdx = R.drawable.book_1; break;
                    case 2: bookLocalData.ImgIdx = R.drawable.book_2; break;
                    case 3: bookLocalData.ImgIdx = R.drawable.book_3; break;
                    case 4: bookLocalData.ImgIdx = R.drawable.book_4; break;
                    case 5: bookLocalData.ImgIdx = R.drawable.book_5; break;
                    case 6: bookLocalData.ImgIdx = R.drawable.book_6; break;
                    case 7: bookLocalData.ImgIdx = R.drawable.book_7; break;
                    default: bookLocalData.ImgIdx = R.drawable.book_1; break;
                }

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
            book_editor.putInt("BookAllPage_"+index, bookLocalData.bookAllPage);
            book_editor.putInt("BookRecentPage_"+index, bookLocalData.recentPage);
            book_editor.putLong("BookReadTime_"+index, bookLocalData.recentReadTime);
        }

        book_editor.commit();
    }

    private String getSharedPreferences_String(SharedPreferences Pref, String key)
    {
        return Pref.getString(key, "");
    }

    private int getSharedPreferences_Int(SharedPreferences Pref, String key)
    {
        return Pref.getInt(key, 0);
    }

    private long getSharedPreferences_Long(SharedPreferences Pref, String key)
    {
        return Pref.getLong(key, 0);
    }



}
