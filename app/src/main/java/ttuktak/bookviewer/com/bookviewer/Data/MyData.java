package ttuktak.bookviewer.com.bookviewer.Data;

import android.util.Pair;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class MyData
{
    public UserData userData = new UserData();
    private SchoolData schoolData = new SchoolData();
    private Map<Integer, BookData> bookDataList = new LinkedHashMap<>();
    private Map<Integer, QuestionData> questionDataList = new LinkedHashMap<>();
    public Set<Integer> bookBoardLikeDataList = new HashSet<>();
    public ArrayList<CurriculumData> curriculumData = new ArrayList<>();
    public int nowCurriculumIndex = 0;
    public ArrayList<Integer> nowCurriculumBookList = new ArrayList<>();

    public void init(int UserIdx, String Nickname, String SchoolCode)
    {
        userData.userIdx = UserIdx;
        userData.nickName = Nickname;
        userData.schoolCode = SchoolCode;
    }

    public void loadEnd()
    {
        nowCurriculumIndex = 0;
        nowCurriculumBookList = curriculumData.get(nowCurriculumIndex).bookIdList;
    }

    public boolean isJoin() {return userData.userIdx > 0; }

    public void setUserData(UserData data) {userData = data;}
    public void setSchoolData(SchoolData data) {schoolData = data;}
    public void setCurriculumData(ArrayList<CurriculumData> list){curriculumData = list;}
    public void setBookData(Map<Integer, BookData> data) {bookDataList = data;}
    public void setQuestionData(Map<Integer, QuestionData> data) {questionDataList = data;}

    public String getUserNickname(){ return userData.nickName;}
    public String getSchoolname(){return schoolData.schoolName;}

    public BookData getBookData(int BookId) {return bookDataList.get(BookId);}

    public void addBookBoardLike(int BoardId)
    {
        bookBoardLikeDataList.add(BoardId);
    }

    public ArrayList<Integer> getCurriculumList()
    {
        return schoolData.curriculumIdList;
    }
    public ArrayList<Integer> getBookIdList()
    {
        ArrayList<Integer> list = new ArrayList<>();

        for(int index = 0 ; index < curriculumData.size() ; ++index)
        {
            list.addAll(curriculumData.get(index).bookIdList);
        }

        return list;
    }

    public ArrayList<Integer> getQuestionIdList()
    {
        ArrayList<Integer> list = new ArrayList<>();

        for(Integer key : bookDataList.keySet())
        {
            list.addAll(bookDataList.get(key).questionIdList);
        }

        return list;
    }

    public boolean isBookBoardLike(int BoardId)
    {
        return bookBoardLikeDataList.contains(BoardId);
    }

    public void clickBookBoardLike(int BoardId)
    {
        if(isBookBoardLike(BoardId))
            bookBoardLikeDataList.remove(BoardId);
        else
            bookBoardLikeDataList.add(BoardId);
    }

    public ArrayList<BookData> getRecentReadBookData()
    {
        ArrayList<BookData> returnValue = new ArrayList<>();

        final ArrayList<Pair<Integer, BookData>> tempList = new ArrayList<Pair<Integer, BookData>>();
        for(Integer key: bookDataList.keySet())
        {
            BookData data = bookDataList.get(key);
            tempList.add(new Pair<>(data.bookId, data));
        }

        Collections.sort(tempList, new Comparator<Pair<Integer, BookData>>() {

            public int compare(Pair<Integer, BookData> o1, Pair<Integer, BookData> o2) {
                return o1.second.recentReadTime > o2.second.recentReadTime ? -1 : 1;
            }
        });

        for(int index = 0 ; index < tempList.size() ; ++index)
        {
            if(tempList.get(index).first <= 0)
                continue;
            returnValue.add(tempList.get(index).second);
        }

        return returnValue;
    }
}
