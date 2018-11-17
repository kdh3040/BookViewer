package bookviewer.bookviewer.com.bookviewer.View.BookBoard;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.ramotion.foldingcell.FoldingCell;

import java.util.ArrayList;

import bookviewer.bookviewer.com.bookviewer.Data.BookBoardData;
import bookviewer.bookviewer.com.bookviewer.Data.DataMgr;
import bookviewer.bookviewer.com.bookviewer.Data.TempFireBaseData;
import bookviewer.bookviewer.com.bookviewer.R;


public class BookBoardView extends Fragment {

    RecyclerView BookBoardRecyclerView;
    BookBoardViewAdapter BookBoardViewAdapter;
    View fragView;

    public BookBoardView() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragView = inflater.inflate(R.layout.fragment_book_board_view, container, false);
        ListView theListView = fragView.findViewById(R.id.board_list_view);

        final ArrayList<BookBoardData> items = DataMgr.getInstance().bookBoardDataList;
        final BookBoardViewAdapter adapter = new BookBoardViewAdapter(this.getContext(), items);

        // set elements to adapter
        theListView.setAdapter(adapter);
        // set on click event listener to list view
        theListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
                // toggle clicked cell state
                ((FoldingCell) view).toggle(false);
                // register in adapter that state for selected cell is toggled
                adapter.registerToggle(pos);
            }
        });


//        BookBoardRecyclerView = (RecyclerView)fragView.findViewById(R.id.recyclerview_bookboardlist);
//        BookBoardRecyclerView.setHasFixedSize(true);
//        BookBoardRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 1));
//
//        BookBoardViewAdapter = new BookBoardViewAdapter(getContext());
//        BookBoardRecyclerView.setAdapter(BookBoardViewAdapter);

        return fragView;
    }

}
