package bookviewer.bookviewer.com.bookviewer.Data;

import java.util.ArrayList;
import java.util.Map;

public class DataMgr {

    private static DataMgr _Instance;

    public static DataMgr getInstance() {
        if (_Instance == null)
            _Instance = new DataMgr();

        return _Instance;
    }

    public TempFireBaseData TempData;

    public UserData myData;

    private DataMgr()
    {
        TempData = new TempFireBaseData();
    }

    public void loadMyData()
    {
        // 저장된 데이터가 없으면 파이어베이스에 요청
        myData = new UserData("a");

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
}
