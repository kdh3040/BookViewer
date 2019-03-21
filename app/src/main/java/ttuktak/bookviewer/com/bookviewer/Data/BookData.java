package ttuktak.bookviewer.com.bookviewer.Data;

import java.util.ArrayList;

public class BookData {
    public int bookId;  // key
    public String bookName;
    public String bookAuthor;
    public ArrayList<Integer> questionIdList = new ArrayList<>();


    // localData
    public String bookFilePath;
    public String bookImg;
    public int bookAllPage = 0;
    public int recentPage = 0;
    public long recentReadTime = 0;

    // TODO 임시
    public int ImgIdx;


}
