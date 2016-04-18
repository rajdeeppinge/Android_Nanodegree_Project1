package udacity.android_nanodegree.popularmovies_stage1;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.squareup.picasso.Picasso;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MovieDetailsFragment extends Fragment {
    private Movie movie;

    public SharedPreferences favouriteMovieStoredIds;
    public SharedPreferences.Editor favEditor;

    private View rootView;
    @Bind(R.id.original_title) TextView originalTitle;
    @Bind(R.id.poster_image) ImageView poster;
    @Bind(R.id.synopsis) TextView synopsis;
    @Bind(R.id.user_rating) TextView userRating;
    @Bind(R.id.release_date) TextView releaseDate;
    @Bind(R.id.mark_favourite_button) ToggleButton favouriteButton;

    @Bind(R.id.button_linear_layout) LinearLayout linearLayout;

    @Bind(R.id.review) TextView review;

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
        rootView = inflater.inflate(R.layout.fragment_movie_details, container, false);

        ButterKnife.bind(this, rootView);

        //originalTitle = (TextView) rootView.findViewById(R.id.original_title);
        originalTitle.setText(movie.getOriginalTitle());

        //poster = (ImageView) rootView.findViewById(R.id.poster_image);
        Picasso
                .with(getActivity())
                .load(movie.getPosterPath())
                .placeholder(R.drawable.dice1)
                .error(R.drawable.dice1)
                .into(poster);

        //synopsis = (TextView) rootView.findViewById(R.id.synopsis);
        synopsis.setText("Synopsis: " + movie.getOverview());

        //userRating = (TextView) rootView.findViewById(R.id.user_rating);
        userRating.setText("User Rating: " + movie.getVoteAverage());

        //releaseDate = (TextView) rootView.findViewById(R.id.release_Date);
        releaseDate.setText("Release Date: " + movie.getReleaseDate());

        final Button[] trailer = new Button[movie.getTrailerName().length];
        for(int i = 0; i < movie.getTrailerName().length; i++) {
            trailer[i] = new Button(getActivity());

            trailer[i].setText(movie.getTrailerName()[i]);

            linearLayout.addView(trailer[i]);

            final int finalI = i;
            trailer[i].setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v) {
                    // put code on click operation
                    Toast.makeText(getActivity(), "Showing Trailer", Toast.LENGTH_SHORT).show();

                    String trailerUrl = "https://www.youtube.com/watch?v=".concat(movie.getTrailerPath()[finalI]);

                    Intent videoIntent = new Intent(Intent.ACTION_VIEW);
                    videoIntent.setPackage("com.google.android.youtube");

                    Uri trailerUri = Uri.parse(trailerUrl);
                    videoIntent.setData(trailerUri);

                    startActivity(videoIntent);
                }
            });
        }

        review.setText("Reviews: " + movie.getReview());

        favouriteButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {


                if(isChecked) {

                    favouriteMovieStoredIds = getActivity().getSharedPreferences(MainActivity.MOVIE_IDS, Context.MODE_PRIVATE);
                    favEditor = favouriteMovieStoredIds.edit();
                    favEditor.putLong(movie.getOriginalTitle(), movie.getId());
                    favEditor.commit();

                    Toast.makeText(getActivity(), "The movie has been added to favourites", Toast.LENGTH_SHORT).show();
                }
                else {
                    favouriteMovieStoredIds = getActivity().getSharedPreferences(MainActivity.MOVIE_IDS, Context.MODE_PRIVATE);
                    favEditor = favouriteMovieStoredIds.edit();
                    favEditor.remove(movie.getOriginalTitle());
                    favEditor.commit();

                    Toast.makeText(getActivity(), "The movie has been removed from favourites", Toast.LENGTH_SHORT).show();
                }
                setRetainInstance(true);
            }
        });

/*
        OnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                favEditor.putLong(movie.getOriginalTitle(), movie.getId());
                favEditor.commit();

                Toast.makeText(getActivity(), "The movie has been added to favourites", Toast.LENGTH_SHORT).show();
            }
        });
*/
        return rootView;
    }
}
