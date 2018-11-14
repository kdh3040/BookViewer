package bookviewer.bookviewer.com.bookviewer.Data;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;

import bookviewer.bookviewer.com.bookviewer.CommonFunc;
import bookviewer.bookviewer.com.bookviewer.R;

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
        schoolData.schoolName = "구암초등학교";
        schoolData.schoolCurriculumIdList.add(1);
        schoolData.schoolCurriculumIdList.add(2);
        schoolData.schoolCurriculumIdList.add(3);
        schoolData.schoolCurriculumIdList.add(4);
        schoolDataList.put(schoolData.schoolCode, schoolData);


        for(int index = 1 ; index <= 10 ; ++index)
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
                case 3:
                    schoolCurriculumDataata.schoolCurriculumName = "중학교 1학년";
                    break;
                default:
                    schoolCurriculumDataata.schoolCurriculumName = "중학교 2학년";
                    break;
            }
            schoolCurriculumDataList.put(index, schoolCurriculumDataata);
        }

        // TODO 빼야댐
        String[] strBookName = {"동백꽃", "해리포터와 마법사의 돌", "톰소여의 모험", "습관의 재발견", "실전 모의고사", "언어의 온도", "보이는 세상",
                "역사의 역사", "열두 발자국", "한때 소중했던 것들", "죄의 목소리", "고양이 1편", "돌이킬 수 없는 약속", "어디서 살 것인가", "곰돌이 푸", "이웃집 커플" };

        for(int index = 1 ; index <= 20 ; index++)
        {

            Random random = new Random();
            int i = (int)(random.nextInt(16));

            BookData bookData = new BookData();
            bookData.bookId = index;
            bookData.bookName = strBookName[i];
            bookData.bookAuthor = CommonFunc.getInstance().randomFullName();
            if(index <= 10)
                schoolCurriculumDataList.get(1).bookIdList.add(index);
            else if(index <= 20)
                schoolCurriculumDataList.get(2).bookIdList.add(index);
            else if(index <= 30)
                schoolCurriculumDataList.get(3).bookIdList.add(index);
            else if(index <= 40)
                schoolCurriculumDataList.get(4).bookIdList.add(index);

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
            bookReport.nickName = CommonFunc.getInstance().randomFullName();
            bookReport.schoolName = CommonFunc.getInstance().randomLastName() + "초등학교";
            bookReport.title = "감상문 제목";
            bookReport.report = "사람이 본질적으로 선하다고 믿고 직원에게 투명하고 솔직하게 모든 것을 공개하는 것, 업무와 관련해서 직원이 자기 목소리를 내도록 권장하는 것을 의미했다. 구글의 중요한 문화 중 2가지는 투명성과 목소리였다.  회사의 정보를 투명하게 공개하는 것(회사의 모든 직원들이 현재 무슨 일이 일어나는 지 알게 된다는 것 의미), 직원에게 회사 운영방식을 실제로 얘기해주고 의견을 받는 것을 의미했다. 리더의 입장에서 투명성과 목소리를 유지하는 것이 쉽지 않은 선택이라고 본다. 투명성과 목소리는 리더의 읿장에서 보면 싫은 소리도 듣는다는 것이며, 조직의 어려운 부분(재정 상황 등)을 공개한다는 것이기 때문이다. 그러나 쉽지 않은 만큼 리더들이 용기낼 가치가 있다고 본다. 구성원의 입장에서는 조직의 상황을 잘 모르는 상황에서 오해를 할 수 도 있으며, 공정하지 않다고 판단하거나 음지에서 조직에 해가 되는 목소리들이 많이 나올 수 있기 때문이다.";
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
