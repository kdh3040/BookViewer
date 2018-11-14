package bookviewer.bookviewer.com.bookviewer.Data;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Pair;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;
import java.util.Set;

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
    public Map<Integer, BookReportData> bookReportLocalDataList = new LinkedHashMap<>();
    public Set<String> bookReportLikeDataList = new HashSet<>();

    public ArrayList<BookReportData> bookReportDataList = new ArrayList<>();
    public UserData myData = new UserData();

    private DataMgr()
    {
        TempData = new TempFireBaseData();
        getFirebaseData();
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
    public boolean isBookReportLike(int ReportId)
    {
        String id = String.valueOf(ReportId);
        return bookReportLikeDataList.contains(id);
    }

    public void getFirebaseData()
    {
        getBookReportData();
    }

    public void getBookReportData()
    {
        for(int index = 0; index < TempData.bookReportDataList.size() ; ++index)
        {
            TempFireBaseData.BookReport TempReportData = TempData.bookReportDataList.get(index);
            BookReportData reportData = new BookReportData();

            reportData.reportId = TempReportData.reportId;
            reportData.nickName = TempReportData.nickName;
            reportData.schoolName = TempReportData.schoolName;
            reportData.title = TempReportData.title;
            reportData.report = TempReportData.report;
            reportData.likeCount = TempReportData.likeCount;
            reportData.bookId = TempReportData.bookId;

            bookReportDataList.add(reportData);
        }
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

    public TempFireBaseData.BookData getBookData(int BookId)
    {
        return TempData.bookDataList.get(BookId);
    }

    public TempFireBaseData.BookReport getBookReportData(int ReportId)
    {
        for(int index = 0 ; index < TempData.bookReportDataList.size() ; ++index)
        {
            if(TempData.bookReportDataList.get(index).reportId == ReportId)
                return TempData.bookReportDataList.get(index);
        }

        return null;
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

    public void clickBookReportLike(Context ViewContext, int ReportId)
    {
        if(isBookReportLike(ReportId))
        {
            bookReportLikeDataList.remove(String.valueOf(ReportId));
            getBookReportData(ReportId).likeCount--;
        }
        else
        {
            bookReportLikeDataList.add(String.valueOf(ReportId));
            getBookReportData(ReportId).likeCount++;
        }


        saveBookReportLikeLocalData(ViewContext);
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
            for(int temp_index = 1 ; temp_index <= 20 ; ++temp_index)
            {
                BookLocalData bookLocalData = new BookLocalData();
                bookLocalData.bookId = temp_index;
                bookLocalData.recentReadTime = temp_index;
                Random test = new Random();
                switch (test.nextInt(7) + 1)
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

        SharedPreferences report_like_pref = ViewContext.getSharedPreferences("BookReportLikeData", Context.MODE_PRIVATE);
        bookReportLikeDataList = report_like_pref.getStringSet("BookReportLike", new HashSet<>());

        SharedPreferences report_pref = ViewContext.getSharedPreferences("BookReportData", Context.MODE_PRIVATE);

        index = 1;
        while (true)
        {
            String reportIdStr = getSharedPreferences_String(report_pref, "ReportId_"+index);
            if(reportIdStr.isEmpty())
                break;

            BookReportData bookReportLocalData = new BookReportData();
            bookReportLocalData.reportId = Integer.parseInt(reportIdStr);
            bookReportLocalData.nickName = getSharedPreferences_String(report_pref, "ReportNickname_"+index);
            bookReportLocalData.schoolName = getSharedPreferences_String(report_pref, "ReportSchoolname_"+index);
            bookReportLocalData.title = getSharedPreferences_String(report_pref, "ReportTitle_"+index);
            bookReportLocalData.report = getSharedPreferences_String(report_pref, "ReportReport_"+index);
            bookReportLocalData.bookId = getSharedPreferences_Int(report_pref, "ReportBookId_"+index);
            bookReportLocalDataList.put(bookReportLocalData.reportId, bookReportLocalData);
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

    public void saveBookReportLikeLocalData(Context ViewContext)
    {
        SharedPreferences report_pref = ViewContext.getSharedPreferences("BookReportLikeData", Context.MODE_PRIVATE);
        SharedPreferences.Editor report_editor = report_pref.edit();

        report_editor.putStringSet("BookReportLike", bookReportLikeDataList);

        report_editor.commit();
    }

    public void saveBookReportLocalData(Context ViewContext)
    {
        SharedPreferences report_pref = ViewContext.getSharedPreferences("BookReportData", Context.MODE_PRIVATE);
        SharedPreferences.Editor report_editor = report_pref.edit();

        int index = 1;
        for(Integer key : bookReportLocalDataList.keySet())
        {
            BookReportData bookReportLocalData = bookReportLocalDataList.get(key);
            report_editor.putString("ReportId_"+index, String.valueOf(bookReportLocalData.reportId));
            report_editor.putString("ReportNickname_"+index, bookReportLocalData.nickName);
            report_editor.putString("ReportSchoolname_"+index, bookReportLocalData.schoolName);
            report_editor.putString("ReportTitle_"+index, bookReportLocalData.title);
            report_editor.putString("ReportReport_"+index, bookReportLocalData.report);
            report_editor.putInt("ReportBookId_"+index, bookReportLocalData.bookId);
        }

        report_editor.commit();
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
