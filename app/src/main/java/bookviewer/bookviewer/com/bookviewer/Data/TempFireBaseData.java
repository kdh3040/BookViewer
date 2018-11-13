package bookviewer.bookviewer.com.bookviewer.Data;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

public class TempFireBaseData {

    public class SchoolData{
        public String schoolCode; // key
        public String schoolName;
        public ArrayList<Integer> schoolCurriculumIdList = new ArrayList<>();
    }

    public class SchoolCurriculumData{
        public int schoolCurriculumId;
        public String schoolCurriculumName;
        public ArrayList<Integer> bookIdList = new ArrayList<>();
    }

    public class BookData{
        public int bookId;
        public String bookName;
        public ArrayList<Integer> questionIdList = new ArrayList<>();
    }

    public class QuestionData{
        public int questionId;
        public int showPageCount;
        public String questionDesc;

        public Boolean multipleChoiceQuestion = false;

        public String correctStr;
        public int correctCount;
        public ArrayList<String> exampleStr = new ArrayList<String>();
    }

    public class BookReport{
      public String nickName;
      public String schoolName;
      public String report;
    }

    public Map<String, SchoolData> schoolDataList = new LinkedHashMap<String, SchoolData>();
    public Map<Integer, SchoolCurriculumData> schoolCurriculumDataList = new LinkedHashMap<Integer, SchoolCurriculumData>();
    public Map<Integer, BookData> bookDataList = new LinkedHashMap<Integer, BookData>();
    public Map<Integer, QuestionData> questionDataList = new LinkedHashMap<Integer, QuestionData>();
    public ArrayList<BookReport> bookReportDataList = new ArrayList<>();


    public TempFireBaseData()
    {
        // 임시 파이어베이스 데이터 추가
        SchoolData schoolData = new SchoolData();
        schoolData.schoolCode = "a";
        schoolData.schoolName = "동네 초등학교";
        schoolData.schoolCurriculumIdList.add(1);
        schoolData.schoolCurriculumIdList.add(2);
        schoolDataList.put(schoolData.schoolCode, schoolData);


        for(int index = 1 ; index <= 2 ; ++index)
        {
            SchoolCurriculumData schoolCurriculumDataata = new SchoolCurriculumData();
            schoolCurriculumDataata.schoolCurriculumId = index;
            switch (index)
            {
                case 1:
                    schoolCurriculumDataata.schoolCurriculumName = "초등학교 1학년";
                    break;
                case 2:
                    schoolCurriculumDataata.schoolCurriculumName = "초등학교 2학년";
                    break;
                default:
                    schoolCurriculumDataata.schoolCurriculumName = "미정";
                    break;
            }
            schoolCurriculumDataList.put(index, schoolCurriculumDataata);
        }

        for(int index = 1 ; index <= 20 ; index++)
        {
            BookData bookData = new BookData();
            bookData.bookId = index;
            bookData.bookName = "책_" + index;
            if(index <= 10)
                schoolCurriculumDataList.get(1).bookIdList.add(index);
            else if(index <= 20)
                schoolCurriculumDataList.get(2).bookIdList.add(index);
            bookDataList.put(index, bookData);
        }

        for(int index = 1 ; index <= 40 ; index++)
        {
            QuestionData questionData = new QuestionData();
            questionData.questionId = index;
            questionData.questionDesc = "문제_" + index;
            questionData.correctStr = "정답";

            if(index == 1)
                bookDataList.get(1).questionIdList.add(index);
            else
                bookDataList.get(index / 2).questionIdList.add(index);

            questionDataList.put(index, questionData);
        }

        BookReport bookReport = new BookReport();
        bookReport.nickName = "독후감쟁이";
        bookReport.schoolName = "동네초등학교";
        bookReport.report = "정말 잼있었다.";
        bookReportDataList.add(bookReport);
    }



}
