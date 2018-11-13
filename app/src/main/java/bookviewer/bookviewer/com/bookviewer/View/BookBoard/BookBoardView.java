package bookviewer.bookviewer.com.bookviewer.View.BookBoard;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
        // Inflate the layout for this fragment
        fragView =inflater.inflate(R.layout.fragment_book_board_view, container, false);

        BookBoardRecyclerView = (RecyclerView)fragView.findViewById(R.id.recyclerview_bookboardlist);
        BookBoardRecyclerView.setHasFixedSize(true);
        BookBoardRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 1));

        BookBoardViewAdapter = new BookBoardViewAdapter(getContext());
        BookBoardRecyclerView.setAdapter(BookBoardViewAdapter);

        return fragView;
    }

}
