package bookviewer.bookviewer.com.bookviewer.Data;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;

import bookviewer.bookviewer.com.bookviewer.CommonFunc;
import bookviewer.bookviewer.com.bookviewer.R;

public class TempFireBaseData {

    public Map<Integer, UserData> userDataList = new LinkedHashMap<>();
    public Map<String, SchoolData> schoolDataList = new LinkedHashMap<>();
    public Map<Integer, CurriculumData> curriculumDataList = new LinkedHashMap<>();
    public Map<Integer, BookData> bookDataList = new LinkedHashMap<>();
    public Map<Integer, QuestionData> questionDataList = new LinkedHashMap<>();
    public ArrayList<BookBoardData> bookBoardDataList = new ArrayList<>();

    private int userIdx = 0;
    private int bookBoardIdx = 0;

    public TempFireBaseData()
    {

        SchoolData schoolData = new SchoolData();
        schoolData.schoolCode = "a";
        schoolData.schoolName = "구암초등학교";
        schoolData.curriculumIdList.add(1);
        schoolData.curriculumIdList.add(2);
        schoolData.curriculumIdList.add(3);
        schoolData.curriculumIdList.add(4);
        schoolDataList.put(schoolData.schoolCode, schoolData);


        for(int index = 1 ; index <= 10 ; ++index)
        {
            CurriculumData curriculumDataata = new CurriculumData();
            curriculumDataata.curriculumId = index;
            switch (index)
            {
                case 1:
                    curriculumDataata.curriculumName = "초등학교 1학년";
                    break;
                case 2:
                    curriculumDataata.curriculumName = "초등학교 2학년";
                    break;
                case 3:
                    curriculumDataata.curriculumName = "중학교 1학년";
                    break;
                default:
                    curriculumDataata.curriculumName = "중학교 2학년";
                    break;
            }
            curriculumDataList.put(index, curriculumDataata);
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
            {
                curriculumDataList.get(1).bookIdList.add(index);
                curriculumDataList.get(2).bookIdList.add(index);
            }

            else if(index <= 20)
            {
                curriculumDataList.get(3).bookIdList.add(index);
                curriculumDataList.get(4).bookIdList.add(index);
            }

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

        // TODO 빼야댐
        String[] strReport = {"2019년은 기해년(己亥年) ‘황금돼지’의 해다. 돼지는 예로부터 행운과 재복을 상징하는 동물이어서 그런지 특별한 이유가 없어도 뭔가 기대를 걸게 되는 한 해다. 과학적인 근거는 없지만 한 집단이 공유하는 ‘마음의 버릇’은 소비에 큰 역할을 한다. 서로서로 좋은 해라고 덕담을 나누고, 결혼을 서둘러 하고, 돼지해에 맞춰 아이를 낳고, 이사를 하고 사업을 일으키면 결과적으로 경제에 긍정적인 영향을 줄 것이다. 황금돼지의 기운이 ‘자기실현적 예언’의 효과를 거두기는 마음에서 2019년의 키워드 두운을 ‘돼지꿈’인 PIGGY DREAM으로 맞췄다. 표지색 또한 아기돼지의 분홍빛 살색을 떠올리게 하는 ‘피치핑크’로 골라 전반적으로 독자들에게 따뜻함을 전하고자 했다. 2019년을 이끌어갈 10개 키워드의 내용을 살펴본다. ",
        "외상외과 의사 이국종 교수가 대한민국 중증외상 의료 현실에 대한 냉정한 보고서이자, 시스템이 기능하지 않는 현실 속에서도 생명을 지키려 애써온 사람들의 분투를 날 것 그대로 담아낸 『골든아워』 제1권. 2002년 지도교수의 권유로 외상외과에 발을 내딛으며 인생의 전환점을 맞이한 저자는 대한민국에 국제 표준의 중증외상 시스템을 정착하기 위해 지난한 싸움을 했고, 17년간 외상외과 의사로서 맞닥뜨린 냉혹한 현실, 고뇌와 사색, 의료 시스템에 대한 문제의식 등을 기록해왔다.",
        "생각이 많아지는 밤, 사랑하고 사랑받고 싶은 순간, 지친 하루의 끝에서 따스한 위로가 필요할 때, 누군가 나에게 꼭 해주었으면 싶었던 말을 가만히 들려준다. 그저 “힘 내.” “사랑해.”라는 표면적인 위로와 사랑이 아닌, 한순간도 당신을 놓지 않았다고, 매순간 당신이 아닌 적이 없었다고 온 마음을 담은 고백을 나직이 건네며 스스로 지난 순간들을 차분하게 돌아보게 하고 더 이상 흔들리지 않고, 나답게 꿋꿋하게 살 수 있는 힘을 전해준다.",
        "2009년 출간 이래 9년간 베스트셀러의 자리를 지키며 우리나라 에세이의 새로운 전범이 되어버린 산문집 『보통의 존재』. 이후 2015년 허구와 사실의 경계를 절묘히 넘나드는 이야기 산문집 『언제 들어도 좋은 말』로 또 한번 독자 대중들에게 커다란 사랑을 받은 이석원이 3년 만에 새 산문집으로 돌아왔다. 이번 산문집 『우리가 보낸 가장 긴 밤』에서는 삶과 죽음, 영원한 이별 등 삶의 거대한 주제들보다는 보다 작고 소소한 이야기들을 담고 싶었다고 저자는 말한다. 왜냐하면 스쳐가는 사소한 순간들에 생의 더 큰 진실이 있다고 믿기 때문이다. 그리하여 마치 사진을 찍듯 일상을 단면 단면 포착하여 써내려간 글들은 모두 8부로 구성되어 펼쳐지며, 이를 통해 독자는 각기 다른 색깔을 지닌 여덟 권의 에세이를 만나는 듯한 기분을 느끼게 될 것이다.",
        "그러던 어느 날, 버려버린 과거에서 도착한 한 통의 편지가 예전에 봉인한 기억을 되살린다. '그들은 지금 교도소에서 나왔습니다.' 편지지에는 그 한 줄만 적혀 있었다. 사람이 죄를 지으면 어떻게 그 대가를 치러야 할까? 죄를 한 번 저지르면 그 사람은 영원히 행복해질 수 없고 새로운 삶을 꿈꿔서도 안 되는 것일까? 한 번 죄를 저지른 사람은 새 삶을 꿈꿀 수 없는 것일까? 이처럼 궁극의 물음으로 내몰며 읽는 이의 목줄까지 죄어오는 이 소설은 저자 야쿠마루 가쿠가 새로운 한 걸음을 내디딘 기념비적 작품이라는 평가를 받는다.",
        "『곰돌이 푸, 행복한 일은 매일 있어』는 푸의 메시지와 삽화가 담긴 책이다. 어떤 상황에서든 여유와 미소를 잊지 않는 곰돌이 푸를 다시 기억하고 만나는 일은, 반복되는 삶 속에서 무엇이 나를 행복하게 하는지를 잊어가는 우리에게, 다시 한 번 행복에 관한 희망과 의미를 되새기게 해줄 것이다. 귀엽고 사랑스러운 모습부터 엉뚱한 모습까지 우리를 자꾸만 웃음 짓게 만드는 푸를, 진심 어린 말로 우리의 마음을 자꾸만 무장 해제시키는 푸를, 초기 삽화부터 우리에게 익숙한 만화영화 속 곰돌이 푸의 모습을 퀄리티 높은 디즈니의 삽화로 소장할 수 있는 것도 큰 즐거움이다.",
                "사람이 본질적으로 선하다고 믿고 직원에게 투명하고 솔직하게 모든 것을 공개하는 것, 업무와 관련해서 직원이 자기 목소리를 내도록 권장하는 것을 의미했다. 구글의 중요한 문화 중 2가지는 투명성과 목소리였다.  회사의 정보를 투명하게 공개하는 것(회사의 모든 직원들이 현재 무슨 일이 일어나는 지 알게 된다는 것 의미), 직원에게 회사 운영방식을 실제로 얘기해주고 의견을 받는 것을 의미했다. 리더의 입장에서 투명성과 목소리를 유지하는 것이 쉽지 않은 선택이라고 본다. 투명성과 목소리는 리더의 읿장에서 보면 싫은 소리도 듣는다는 것이며, 조직의 어려운 부분(재정 상황 등)을 공개한다는 것이기 때문이다. 그러나 쉽지 않은 만큼 리더들이 용기낼 가치가 있다고 본다. 구성원의 입장에서는 조직의 상황을 잘 모르는 상황에서 오해를 할 수 도 있으며, 공정하지 않다고 판단하거나 음지에서 조직에 해가 되는 목소리들이 많이 나올 수 있기 때문이다."};

        Random random = new Random();

        for(int index = 1; index < 20; ++index)
        {
            BookBoardData bookBoardData = new BookBoardData();

            bookBoardData.boardId = index;
            bookBoardData.nickName = CommonFunc.getInstance().randomFullName();
            bookBoardData.schoolName = CommonFunc.getInstance().randomLastName() + "초등학교";
            bookBoardData.title = "감상문 제목";
            bookBoardData.report = strReport[random.nextInt(7)];
            Random test = new Random();
            bookBoardData.bookId = test.nextInt(7) + 1;
            bookBoardData.likeCount = 0;
            bookBoardDataList.add(bookBoardData);
        }
    }

    public void addBookBoardData(int BookId, String Title, String Desc)
    {
        BookBoardData bookBoardData = new BookBoardData();
        bookBoardData.boardId = bookBoardDataList.size() + 1;
        bookBoardData.nickName = DataMgr.getInstance().myData.getUserNickname();
        bookBoardData.schoolName = DataMgr.getInstance().myData.getSchoolname();
        bookBoardData.title = Title;
        bookBoardData.report = Desc;
        bookBoardData.bookId = BookId;
        bookBoardData.likeCount = 0;
        bookBoardDataList.add(bookBoardData);
    }

    public ArrayList<CurriculumData> getCurriculumData(ArrayList<Integer> list)
    {
        ArrayList<CurriculumData> returnList = new ArrayList<CurriculumData>();
        for (int index = 0 ; index < list.size() ; ++index)
        {
            returnList.add(curriculumDataList.get(list.get(index)));
        }

        return returnList;
    }

    public BookData getBookData(int id)
    {
        return bookDataList.get(id);
    }

    public Map<Integer, BookData> getBookData(ArrayList<Integer> list)
    {
        Map<Integer, BookData> returnList = new LinkedHashMap<>();

        for(int index = 0 ; index < list.size() ; ++index)
        {
            returnList.put(list.get(index), bookDataList.get(list.get(index)));
        }

        return returnList;
    }

    public Map<Integer, QuestionData> getQuestionData(ArrayList<Integer> list)
    {
        Map<Integer, QuestionData> returnList = new LinkedHashMap<>();

        for(int index = 0 ; index < list.size() ; ++index)
        {
            returnList.put(list.get(index), questionDataList.get(list.get(index)));
        }

        return returnList;
    }

    public BookBoardData getBookBoardData(int BoardId)
    {
        for(int index = 0 ; index < bookBoardDataList.size() ; ++index)
        {
            if(bookBoardDataList.get(index).boardId == BoardId)
                return bookBoardDataList.get(index);
        }

        return null;
    }

    public int getNextUserIdx()
    {
        return ++userIdx;
    }

    public int getNextBookBoardIdx()
    {
        return ++bookBoardIdx;
    }

    public void addUserData(int UserIdx, String NickName, String SchoolCode)
    {
        if(userDataList.containsKey(UserIdx))
            return;

        // 임시 파이어베이스 데이터 추가
        UserData userData = new UserData();
        userData.userIdx = UserIdx;
        userData.schoolCode = "a";
        userData.nickName = NickName;
        userDataList.put(UserIdx, userData);
    }
}
