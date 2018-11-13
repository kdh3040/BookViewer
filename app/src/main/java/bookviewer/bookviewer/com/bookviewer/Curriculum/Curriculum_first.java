package bookviewer.bookviewer.com.bookviewer.Curriculum;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import bookviewer.bookviewer.com.bookviewer.BookListAdapter;
import bookviewer.bookviewer.com.bookviewer.MainActivity;
import bookviewer.bookviewer.com.bookviewer.R;

public class Curriculum_first extends Fragment {

    RecyclerView BookRecyclerView;
    BookListAdapter BookListViewAdapter;
    View fragView;

    public Curriculum_first() {
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
        fragView = inflater.inflate(R.layout.fragment_curriculum_first,container,false);

        BookRecyclerView = (RecyclerView)fragView.findViewById(R.id.recyclerview_booklist);
        BookRecyclerView.setHasFixedSize(true);
        BookRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 1));

        BookListViewAdapter = new BookListAdapter(getContext());
        BookRecyclerView.setAdapter(BookListViewAdapter);

        return fragView;
    }

}
