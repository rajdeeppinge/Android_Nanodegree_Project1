package udacity.android_nanodegree.popularmovies_stage1;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

public class MainActivityFragment extends Fragment {
    private GridView gridView;
    private GetMovies_AsyncTask gmov;

    public MainActivityFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);

        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        gridView = (GridView) rootView.findViewById(R.id.grid_view);

        // by default, sort by popularity
        gmov = new GetMovies_AsyncTask(gridView,getContext());
        gmov.execute(getContext().getString(R.string.sort_by_popularity));

        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Inflate the menu; this adds items to the action bar if it is present.
        super.onCreateOptionsMenu(menu, inflater);;
        inflater.inflate(R.menu.menu_main, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.most_popular) {
            gmov = new GetMovies_AsyncTask(gridView,getContext());
            gmov.execute(getContext().getString(R.string.sort_by_popularity));
        }

        if (id == R.id.highest_rated) {
            gmov = new GetMovies_AsyncTask(gridView,getContext());
            gmov.execute(getContext().getString(R.string.sort_by_rating));
        }

        return true;
    }
}