package udacity.android_nanodegree.popularmovies_stage1;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import java.util.List;

import com.squareup.picasso.Picasso;

/**
 * Created by root on 22/2/16.
 */
public class ImageAdapter extends ArrayAdapter<Movie>{
    private Context mContext;
    private List<Movie> movieList;

    public ImageAdapter(Context context, List<Movie> movieList) {
        super(context, 0, movieList);
        mContext = context;
        this.movieList = movieList;
    }

    public int getCount() {
        return movieList.size();
    }

    public Movie getItem(int position) {
        return movieList.get(position);
    }

    // create a new ImageView for each item referenced by the Adapter
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;

        //if no view present, create new image view
        if (convertView == null) {
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new GridView.LayoutParams(355, 550));
            imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);

        }
        else {
            imageView = (ImageView) convertView;
        }

        final Movie movie = getItem(position);

        Picasso.with(mContext).load(movie.getPosterImagePath()).into(imageView);

        return imageView;
    }
}