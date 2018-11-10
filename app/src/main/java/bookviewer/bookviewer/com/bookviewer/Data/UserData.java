package bookviewer.bookviewer.com.bookviewer.Data;

import android.provider.ContactsContract;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

public class UserData {
    public String nickName;
    public String schoolCode = "";

    public String schoolName;

    public ArrayList<SchoolCurriculumData> schoolCurriculumDataList = new ArrayList<SchoolCurriculumData>();
    public Map<Integer, BookData> bookDataList = new LinkedHashMap<Integer, BookData>();
    public Map<Integer, QuestionData> questionDataList = new LinkedHashMap<Integer, QuestionData>();

    public int viewSchoolCurriculumIndex = 0;
    public ArrayList<Integer> viewBookIdList = new ArrayList<>();

    public void init(String SchoolCode, String NickName)
    {
        if(SchoolCode.isEmpty() || NickName.isEmpty())
            return;

        nickName = NickName;
        schoolCode = SchoolCode;
        addData();

        // TODO 임시
        viewSchoolCurriculumIndex = 0;
        viewBookIdList = schoolCurriculumDataList.get(viewSchoolCurriculumIndex).bookIdList;
    }

    public boolean isJoin()
    {
        return schoolCode.isEmpty() == false;
    }

    public void addData()
    {
        addSchoolCurriculumData();
        addBookData();
        addQuestionData();
    }
    public void addSchoolCurriculumData()
    {
        schoolCurriculumDataList = DataMgr.getInstance().getSchoolCurriculumDataList(schoolCode);
    }

    public void addBookData()
    {
        for(int index = 0 ; index < schoolCurriculumDataList.size() ; ++index)
        {
            ArrayList<BookData> TempBookDataList = DataMgr.getInstance().getBookDataList(schoolCurriculumDataList.get(index).schoolCurriculumId);
            for(int index_1 = 0 ; index_1 < TempBookDataList.size() ; ++index_1)
            {
                BookData TempData = TempBookDataList.get(index_1);
                bookDataList.put(TempData.bookId, TempData);
            }
        }
    }

    public BookData getBookData(int BookId)
    {
        return bookDataList.get(BookId);
    }

    public void addQuestionData()
    {
        for( Integer key : bookDataList.keySet() ){
            BookData bookData = bookDataList.get(key);
            for(int index = 0 ; index < bookData.questionIdList.size() ; ++index)
            {
                QuestionData questionData = DataMgr.getInstance().getQuestionData(bookData.questionIdList.get(index));
                questionDataList.put(questionData.questionId, questionData);
            }
        }
    }
}
