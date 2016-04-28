package drfresh.securitywatch3;


import android.app.Fragment;
import android.app.FragmentManager;
import android.content.res.Configuration;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.MenuItem;
import android.view.View;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;


import drfresh.securitywatch3.fragments.AboutFragment;
import drfresh.securitywatch3.fragments.HistoricalViewFragment;
import drfresh.securitywatch3.fragments.PreferencesFragment;
import drfresh.securitywatch3.fragments.ViewLiveStreamFragment;

/**
 * MainActivity consists of a navication drawer containing navigation options,
 * and a main content panel which is used to swap fragments
 */
public class  MainActivity extends AppCompatActivity {
    public static final int LIVE_STREAM = 0;
    public static final int LIVE_STREAM2 = 1;
    public static final int HISTORICAL_VIEW = 2;
    public static final int SETTINGS = 3;
    public static final int ABOUT=4;
    public static final String POSITION = "positionNumber";
    private String[] navigationOptions = {"Live Stream (Backdoor)","Live Stream (FrontDoor)",
            "Historical Captures", "Settings", "About"};
    private DrawerLayout mDrawerLayout;
    private CharSequence mDrawerTitle;
    private ListView mDrawerList;
    private CharSequence mTitle;
    private ActionBarDrawerToggle mDrawerToggle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mTitle = mDrawerTitle = getTitle();

        setContentView(R.layout.activity_main);
        //initialize navigation drawer
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);

        //set adapter for list view in nav drawer
        mDrawerList.setAdapter(new ArrayAdapter<String>(this,
                R.layout.drawer_list_item, navigationOptions));

        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
        // enable ActionBar app icon to behave as action to toggle nav drawer
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        // ActionBarDrawerToggle ties together the the proper interactions
        // between the sliding drawer and the action bar app icon
        // ActionBarDrawerToggle ties together the the proper interactions
        // between the sliding drawer and the action bar app icon
        mDrawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                mDrawerLayout,         /* DrawerLayout object */
                R.drawable.ic_drawer,  /* nav drawer image to replace 'Up' caret */
                R.string.drawer_open,  /* "open drawer" description for accessibility */
                R.string.drawer_close  /* "close drawer" description for accessibility */
        ) {
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                getSupportActionBar().setTitle(mTitle);
            }

            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getSupportActionBar().setTitle(mDrawerTitle);

            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);


        //by default, load the first menu option as a fragment
        if (savedInstanceState == null) {
            selectItem(0);
        }
    }
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Pass the event to ActionBarDrawerToggle, if it returns
        // true, then it has handled the app icon touch event
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        // Handle your other action bar items...

        return super.onOptionsItemSelected(item);
    }




    /**
     * Swaps out fragments depending on selected item
     */
    private void selectItem(int position) {
        Fragment fragment = null;
        switch (position) {
            case LIVE_STREAM:
                fragment = new ViewLiveStreamFragment();
                break;
            case LIVE_STREAM2:
                fragment = new ViewLiveStreamFragment();
                break;
            case HISTORICAL_VIEW:
                fragment = new HistoricalViewFragment();
                break;
            case ABOUT:
                fragment = new AboutFragment();
                break;
            case SETTINGS:
                fragment = new PreferencesFragment();
                break;
            default:
                fragment = new ViewLiveStreamFragment();
                break;
        }

        Bundle args = new Bundle();
        args.putInt(POSITION, position);
        fragment.setArguments(args);
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
        // update selected item and title, then close the drawer
        mDrawerList.setItemChecked(position, true);
        setTitle(navigationOptions[position]);
        mDrawerLayout.closeDrawer(mDrawerList);

    }

    public class DrawerItemClickListener implements ListView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView parent, View view, int position, long id) {
            selectItem(position);
        }


    }
    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
       getSupportActionBar().setTitle(mTitle);
    }
}