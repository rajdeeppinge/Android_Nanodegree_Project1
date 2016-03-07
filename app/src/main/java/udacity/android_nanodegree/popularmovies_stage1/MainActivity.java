package udacity.android_nanodegree.popularmovies_stage1;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

public class MainActivity extends AppCompatActivity {

    private MainActivityFragment mainActivityFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FragmentManager mFragmentManager = getSupportFragmentManager();

        //in case the fragment exists take that directly
        mainActivityFragment = (MainActivityFragment) mFragmentManager.findFragmentByTag(this.getString(R.string.main_activity_fragment_tag));

        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();

        //if fragment does not exist, create a new one
        if(mainActivityFragment == null) {
            mainActivityFragment = new MainActivityFragment();
            fragmentTransaction.add(R.id.fragment_main, mainActivityFragment, this.getString(R.string.main_activity_fragment_tag));
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }

    }

}
