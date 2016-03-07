package udacity.android_nanodegree.popularmovies_stage1;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class MovieDetailsFragment extends Fragment {
    private Movie movie;

    public MovieDetailsFragment() {
        // Required empty public constructor
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_movie_details, container, false);

        TextView originalTitle = (TextView) rootView.findViewById(R.id.original_title);
        originalTitle.setText(movie.getOriginalTitle());

        ImageView poster = (ImageView) rootView.findViewById(R.id.poster_image);
        Picasso.with(getContext()).load(movie.getPosterImagePath()).into(poster);

        TextView synopsis = (TextView) rootView.findViewById(R.id.synopsis);
        synopsis.setText(movie.getOverview());

        TextView userRating = (TextView) rootView.findViewById(R.id.user_rating);
        userRating.setText("User Rating: " + movie.getVoteAverage());

        TextView releaseDate = (TextView) rootView.findViewById(R.id.release_Date);
        releaseDate.setText("Release Date: " + movie.getReleaseDate());

        return rootView;
    }
}
