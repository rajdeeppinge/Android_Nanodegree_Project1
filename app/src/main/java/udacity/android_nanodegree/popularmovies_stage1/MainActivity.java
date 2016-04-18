package udacity.android_nanodegree.popularmovies_stage1;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

public class MainActivity extends AppCompatActivity {

    private MainActivityFragment mainActivityFragment;

    public static final String MOVIE_IDS = "FAV_MOVIE_IDS_shared_preferences";
    public SharedPreferences favouriteMovieStoredIds;

    private Boolean tabletMode = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if(findViewById(R.id.movie_detail_tab_frag) != null) {
            tabletMode = true;
        }


        FragmentManager mFragmentManager = this.getFragmentManager();
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();

        //in case the fragment exists take that directly
        mainActivityFragment = (MainActivityFragment) mFragmentManager.findFragmentByTag(this.getString(R.string.main_activity_fragment_tag));



        //if fragment does not exist, create a new one
        if (mainActivityFragment == null) {
            mainActivityFragment = new MainActivityFragment();

            Bundle bundle = new Bundle();
            bundle.putBoolean("isTablet", tabletMode);
            mainActivityFragment.setArguments(bundle);

            fragmentTransaction.add(R.id.movie_detail_frag, mainActivityFragment, this.getString(R.string.main_activity_fragment_tag));
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }

        favouriteMovieStoredIds = getApplicationContext().getSharedPreferences(MOVIE_IDS, Context.MODE_PRIVATE);

    }

    @Override
    public void onBackPressed() {
        if(this.getFragmentManager().getBackStackEntryCount() != 0) {
            this.getFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
    }

}
