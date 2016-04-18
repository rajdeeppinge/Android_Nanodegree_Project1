package udacity.android_nanodegree.popularmovies_stage1;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.AsyncTask;

import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.Collection;

/**
 * Created by root on 4/18/16.
 */
public class GetFavouriteMovies_AsyncTask extends AsyncTask<Object[], Void, Void> {
    private Movie[] movies /*= new Movie[20]*/;
    private ImageAdapter imageAdapter;
    private GridView gridView;
    private Context context;
    static final String TAG = "AsyncTask";
    private boolean internet;

    private Boolean tabletMode;

 //   private GetMovies_AsyncTask gmov;

    String[] trailerName;
    String[] trailerPath;

    String reviews = new String();

    public GetFavouriteMovies_AsyncTask(GridView gridView, Context context, Boolean tabletMode) {
        this.gridView = gridView;
        this.context = context;
        this.tabletMode = tabletMode;
        internet = true;
    }

    @Override
    protected Void doInBackground(Object[]... params) {
        Object[] ids = params[0];

        movies = new Movie[ids.length];

        fetchMoviesById(ids);

        if(movies != null) {
            for (int i = 0; i < movies.length; i++) {
                fetchTrailers(movies[i].getId());
                movies[i].setTrailerName(trailerName);
                movies[i].setTrailerPath(trailerPath);

                fetchReview(movies[i].getId());
                movies[i].setReview(reviews);
            }
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void v) {
        Log.d(TAG, "onPostExecute");

        if(!internet) {
            Toast toast = Toast.makeText(context, "No Internet Access!", Toast.LENGTH_LONG);
            toast.show();
            return;
        }

        imageAdapter = new ImageAdapter(context, Arrays.asList(movies));

        // Get a reference to the ListView, and attach this adapter to it.
        imageAdapter.notifyDataSetChanged();
        gridView.setAdapter(imageAdapter);
/*
        if(imageAdapter.isEmpty()) {
            Toast.makeText(context, "There are no favourite movies", Toast.LENGTH_SHORT).show();
            return;
        }
*/
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Movie imageClick = movies[position];

                FragmentManager fragmentManager = ((FragmentActivity) context).getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                MovieDetailsFragment movieDetailsFragment = new MovieDetailsFragment();
                movieDetailsFragment.setMovie(imageClick);


              //  fragmentTransaction.replace(R.id.movie_grid_frag, movieDetailsFragment, context.getString(R.string.movie_details_fragment_tag));
                if(!tabletMode)
                    fragmentTransaction.replace(R.id.movie_detail_frag, movieDetailsFragment, context.getString(R.string.movie_details_fragment_tag));
                else
                    fragmentTransaction.replace(R.id.movie_detail_tab_frag, movieDetailsFragment, context.getString(R.string.movie_details_fragment_tag));


                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

                imageAdapter.notifyDataSetChanged();
            }
        });
        return;
    }



    private void fetchMoviesById(Object[] ids) {
        HttpURLConnection httpURLConnection = null;
        BufferedReader bufferedReader = null;

        //JSON array tag
//        final String JSON_ARRAY_TAG = "results";


        //url details
        final String TMDB_BASE_URL = context.getString(R.string.base_url_tmdb);
//        final String SORT_BY_TAG = "sort_by";
        final String API_KEY_TAG = "api_key";
        final String API_KEY = context.getString(R.string.my_api_key);


        //image url details
        final String IMAGE_BASE_URL = context.getString(R.string.poster_image_base_url);
        final String IMAGE_SIZE = "w342";
        final String ORIGINAL_TITLE_TAG = "original_title";
        final String POSTER_IMAGE_PATH_TAG = "poster_path";
        final String VOTE_AVERAGE_TAG = "vote_average";
        final String RELEASE_DATE_TAG = "release_date";
        final String OVERVIEW_TAG = "overview";
        final String ID_TAG = "id";


        for(int i = 0; i < ids.length; i++) {
            try {
                URL url = new URL(TMDB_BASE_URL + ids[i].toString() + "?" + API_KEY_TAG + "=" + API_KEY);

                httpURLConnection = (HttpURLConnection) url.openConnection();
    /*            httpURLConnection.setReadTimeout(10000);
                httpURLConnection.setConnectTimeout(15000);*/
                httpURLConnection.setRequestMethod("GET");
    /*            httpURLConnection.setDoInput(true);*/
                httpURLConnection.connect();


                // Read the input stream into a String
                InputStream inputStream = httpURLConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    // Nothing to do.
                    return;
                }

                bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                    // But it makes debugging easier
                    buffer.append(line + "\n");
                }

                if (buffer.length() == 0) {
                    // Stream was empty.  No point in parsing.
                    return;
                }

                String rawJsonData = buffer.toString();


                // parsing the string and extracting the data
                try {
                    JSONObject jsonObject = new JSONObject(rawJsonData);
                    //                JSONArray jsonArray = jsonObject.getJSONArray(JSON_ARRAY_TAG);

 //                   movies = new Movie[ids.size()];

  //                  for (int i = 0; i < ids.size(); i++) {
  //                      JSONObject movie = jsonArray.getJSONObject(i);

                        movies[i] = new Movie(jsonObject.get(ORIGINAL_TITLE_TAG).toString(),
                                IMAGE_BASE_URL + IMAGE_SIZE + jsonObject.get(POSTER_IMAGE_PATH_TAG).toString(),
                                jsonObject.get(OVERVIEW_TAG).toString(),
                                jsonObject.get(VOTE_AVERAGE_TAG).toString(),
                                jsonObject.get(RELEASE_DATE_TAG).toString(),
                                jsonObject.getLong(ID_TAG));
 //                   }

 //                   return movies;
                } catch (JSONException e) {
                    Log.e(TAG, " JSON exception ", e);
                    internet = false;
                    return;
                }
            } catch (IOException e) {
                Log.e(TAG, " Problem in opening connection ", e);
                internet = false;
                return;
            }


            //at the end, close the connection and buffered reader
            finally {
                if (httpURLConnection != null) {
                    httpURLConnection.disconnect();
                }
                if (bufferedReader != null) {
                    try {
                        bufferedReader.close();
                    } catch (final IOException e) {
                        Log.e(TAG, " Error in closing input stream ", e);
                    }
                }
            }
        }
    }

    public String fetchReview(long id) {
//        String reviews = new String();

        HttpURLConnection httpURLConnection = null;
        BufferedReader bufferedReader = null;

        //JSON array tag
        final String JSON_ARRAY_TAG = "results";

        //url details
        final String TMDB_BASE_URL = context.getString(R.string.base_url_tmdb);
        final String REVIEW_TAG = "reviews";
        final String API_KEY_TAG = "api_key";
        final String API_KEY = context.getString(R.string.my_api_key);

        final String AUTHOR_TAG = "author";
        final String CONTENT_TAG = "content";

        try {
            URL url = new URL(TMDB_BASE_URL + id + "/" + REVIEW_TAG + "?" + API_KEY_TAG + "=" + API_KEY);

            httpURLConnection = (HttpURLConnection) url.openConnection();
/*            httpURLConnection.setReadTimeout(10000);
            httpURLConnection.setConnectTimeout(15000);*/
            httpURLConnection.setRequestMethod("GET");
/*            httpURLConnection.setDoInput(true);*/
            httpURLConnection.connect();


            // Read the input stream into a String
            InputStream inputStream = httpURLConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {
                // Nothing to do.
                return null;
            }

            bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                // But it makes debugging easier
                buffer.append(line + "\n");
            }

            if (buffer.length() == 0) {
                // Stream was empty.  No point in parsing.
                return null;
            }

            String rawJsonData = buffer.toString();


            // parsing the string and extracting the data
            try {
                JSONObject jsonObject = new JSONObject(rawJsonData);
                JSONArray jsonArray = jsonObject.getJSONArray(JSON_ARRAY_TAG);

                for(int i=0; i<jsonArray.length(); i++) {
                    JSONObject review = jsonArray.getJSONObject(i);

                    reviews += "\n\nAuthor: " + review.get(AUTHOR_TAG) + "\n" + review.get(CONTENT_TAG);
                }

                return reviews;
            }
            catch (JSONException e) {
                Log.e(TAG, " JSON exception ", e);
                internet = false;
                return null;
            }
        }

        catch (IOException e) {
            Log.e(TAG, " Problem in opening connection ", e);
            internet = false;
            return null;
        }


        //at the end, close the connection and buffered reader
        finally{
            if (httpURLConnection != null) {
                httpURLConnection.disconnect();
            }
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (final IOException e) {
                    Log.e(TAG, " Error in closing input stream ", e);
                }
            }
        }
    }

    public String[] fetchTrailers(long id) {
//        String[] trailers;

        HttpURLConnection httpURLConnection = null;
        BufferedReader bufferedReader = null;

        //JSON array tag
        final String JSON_ARRAY_TAG = "results";

        //url details
        final String TMDB_BASE_URL = context.getString(R.string.base_url_tmdb);
        final String VIDEO_TAG = "videos";
        final String API_KEY_TAG = "api_key";
        final String API_KEY = context.getString(R.string.my_api_key);

        final String NAME_TAG = "name";
        final String PATH_TAG = "key";

        try {
            URL url = new URL(TMDB_BASE_URL + id + "/" + VIDEO_TAG + "?" + API_KEY_TAG + "=" + API_KEY);

            httpURLConnection = (HttpURLConnection) url.openConnection();
/*            httpURLConnection.setReadTimeout(10000);
            httpURLConnection.setConnectTimeout(15000);*/
            httpURLConnection.setRequestMethod("GET");
/*            httpURLConnection.setDoInput(true);*/
            httpURLConnection.connect();


            // Read the input stream into a String
            InputStream inputStream = httpURLConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {
                // Nothing to do.
                return null;
            }

            bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                // But it makes debugging easier
                buffer.append(line + "\n");
            }

            if (buffer.length() == 0) {
                // Stream was empty.  No point in parsing.
                return null;
            }

            String rawJsonData = buffer.toString();


            // parsing the string and extracting the data
            try {
                JSONObject jsonObject = new JSONObject(rawJsonData);
                JSONArray jsonArray = jsonObject.getJSONArray(JSON_ARRAY_TAG);

                trailerName = new String[jsonArray.length()];
                trailerPath = new String[jsonArray.length()];

                for(int i=0; i<jsonArray.length(); i++) {
                    JSONObject trailer = jsonArray.getJSONObject(i);

                    trailerName[i] = trailer.get(NAME_TAG).toString();

                    trailerPath[i] = trailer.get(PATH_TAG).toString();
                }

                return trailerName;
            }
            catch (JSONException e) {
                Log.e(TAG, " JSON exception ", e);
                internet = false;
                return null;
            }
        }

        catch (IOException e) {
            Log.e(TAG, " Problem in opening connection ", e);
            internet = false;
            return null;
        }


        //at the end, close the connection and buffered reader
        finally{
            if (httpURLConnection != null) {
                httpURLConnection.disconnect();
            }
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (final IOException e) {
                    Log.e(TAG, " Error in closing input stream ", e);
                }
            }
        }
    }
}
