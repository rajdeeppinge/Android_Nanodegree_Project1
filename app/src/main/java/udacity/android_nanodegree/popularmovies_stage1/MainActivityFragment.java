package udacity.android_nanodegree.popularmovies_stage1;

import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.Toast;

import java.util.Collection;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivityFragment extends Fragment {
    @Bind(R.id.grid_view) GridView gridView;
    private GetMovies_AsyncTask gmov;

    private GetFavouriteMovies_AsyncTask getfav;

    public SharedPreferences favouriteMovieStoredIds;
    public Map<String, Long> favouriteMovies;

    private Boolean tabletMode = false;

//    private MovieDetailsFragment mdf;

    public MainActivityFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        Bundle args = getArguments();
        tabletMode = args.getBoolean("isTablet");

        setHasOptionsMenu(true);

        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        ButterKnife.bind(this, rootView);

        // by default, sort by popularity
        gmov = new GetMovies_AsyncTask(gridView, getActivity(), tabletMode);
        gmov.execute(getActivity().getString(R.string.sort_by_popularity));

//        mdf = new MovieDetailsFragment();

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
            gmov = new GetMovies_AsyncTask(gridView, getActivity(), tabletMode);
            gmov.execute(getActivity().getString(R.string.sort_by_popularity));
        }

        if (id == R.id.highest_rated) {
            gmov = new GetMovies_AsyncTask(gridView, getActivity(), tabletMode);
            gmov.execute(getActivity().getString(R.string.sort_by_rating));
        }

        if (id == R.id.favourites) {
            // fetch ids from sharedpreferences
            favouriteMovieStoredIds = getActivity().getSharedPreferences(MainActivity.MOVIE_IDS, Context.MODE_PRIVATE);
            favouriteMovies = (Map<String, Long>) favouriteMovieStoredIds.getAll();
            ;
            //mdf.favouriteMovieStoredIds;

            if(favouriteMovies.size() == 0) {
                Toast.makeText(getActivity(), "There are no favourite movies", Toast.LENGTH_SHORT).show();
            }
            //call asynctask to get movies with endpoint movie/id
            else {
                Object[] ids = favouriteMovies.values().toArray();

                getfav = new GetFavouriteMovies_AsyncTask(gridView, getActivity(), tabletMode);
                //   for (int i = 0; i < ids.length; i++) {
                getfav.execute(ids);
                // }
            }
        }

        return true;
    }
}