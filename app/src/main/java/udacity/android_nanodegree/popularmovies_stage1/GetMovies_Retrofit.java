package udacity.android_nanodegree.popularmovies_stage1;

/* This class has not been used in the app due to some difficulties */

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 4/15/16.
 */
public class GetMovies_Retrofit {
    List<Movie> movies;
    public static final String DATE_FORMAT = "yyyy-MM-dd";

    public GetMovies_Retrofit() {
        movies = new ArrayList<Movie>();
    }

    public static GetMovies_Retrofit parseJSON(String response) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES);
        gsonBuilder.setDateFormat(DATE_FORMAT);

        Gson gson = gsonBuilder.create();

        GetMovies_Retrofit movieList = gson.fromJson(response, GetMovies_Retrofit.class);
        return movieList;
    }
}
