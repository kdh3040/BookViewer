package bookviewer.bookviewer.com.bookviewer.Data;

import java.util.ArrayList;

public class QuestionData {
    public int questionId;
    public int showPageCount;
    public String questionDesc;

    public Boolean multipleChoiceQuestion = false;

    public String correctStr;
    public int correctCount;
    public ArrayList<String> exampleStr = new ArrayList<String>();
}
