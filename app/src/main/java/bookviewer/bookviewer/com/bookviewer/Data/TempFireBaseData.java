package bookviewer.bookviewer.com.bookviewer.Data;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;

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
        public String bookAuthor;
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
        public int reportId;
        public String nickName;
        public String schoolName;
        public String title;
        public String report;
        public int likeCount;
        public int bookId;

        public BookReport()
        {

        }
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
            bookData.bookAuthor = "작가_" + index;
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

        for(int index = 1; index < 20; ++index)
        {
            BookReport bookReport = new BookReport();
            bookReport.reportId = index;
            bookReport.nickName = "독후감쟁이_"+ index;
            bookReport.schoolName = "동네초등학교";
            bookReport.title = "책 무지 잼있다.";
            bookReport.report = "눈에 수 긴지라 이상이 생의 이것이다. 예수는 풀밭에 피가 물방아 힘있다. 몸이 피부가 만천하의 것이다. 원대하고, 이상의 찾아다녀도, 것이 이상이 거친 위하여서. 갑 귀는 일월과 바이며, 이상은 더운지라 피는 청춘의 아니더면, 약동하다. 보이는 이상 꾸며 있을 있으며, 할지니, 끓는다. 전인 부패를 피어나기 그림자는 봄날의 얼마나 보라. 스며들어 그들은 하는 속에서 이상은 들어 피가 열락의 철환하였는가? 예수는 사랑의 끓는 생생하며, 인생에 그것은 목숨이 피어나기 부패뿐이다. 풀이 소담스러운 품으며, 길지 이상의 그들의 하였으며, 영원히 아름답고 쓸쓸하랴?";
            Random test = new Random();
            bookReport.bookId = test.nextInt(7) + 1;
            bookReport.likeCount = 0;
            bookReportDataList.add(bookReport);
        }
    }

    public void addBookBoardData(int BookId, String Title, String Desc)
    {
        BookReport bookReport = new BookReport();
        bookReport.reportId = bookReportDataList.size() + 1;
        bookReport.nickName = DataMgr.getInstance().myData.nickName;
        bookReport.schoolName = DataMgr.getInstance().myData.schoolName;
        bookReport.title = Title;
        bookReport.report = Desc;
        bookReport.bookId = BookId;
        bookReport.likeCount = 0;
        bookReportDataList.add(bookReport);
    }
}
