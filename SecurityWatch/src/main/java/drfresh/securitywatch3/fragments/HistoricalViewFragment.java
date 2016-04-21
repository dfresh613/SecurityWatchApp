package drfresh.securitywatch3.fragments;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import drfresh.securitywatch3.R;

/**
 * Created by derohde on 4/20/16.
 */
public class HistoricalViewFragment extends ListFragment{
    SimpleCursorAdapter mAdapter;
    //elements to show
    public String[] captures = new String[100];
    static final String SELECTION = "((" +
            ContactsContract.Data.DISPLAY_NAME + " NOTNULL) AND (" +
            ContactsContract.Data.DISPLAY_NAME + " != '' ))";
    static final String[] PROJECTION = new String[] {ContactsContract.Data._ID,
            ContactsContract.Data.DISPLAY_NAME};
    public String baseURL="http://10.0.2.2:8082/SecurityWatchServer";
    public String mocapDirQuery = baseURL+"/mocap-dirs";
    public String currDir= "";
    public ArrayList<String> dates;
    //Mapping of position to text
    public HashMap<Integer, String> posToDirMap = new HashMap<Integer,String>();
    public ArrayAdapter adapter;
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        /*
        ProgressBar progressBar = new ProgressBar(getView().getContext());
        progressBar.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT,
                FrameLayout.LayoutParams.WRAP_CONTENT, Gravity.CENTER));
        progressBar.setIndeterminate(true);
        getListView().setEmptyView(progressBar);
        String[] fromColumns = {ContactsContract.Data.DISPLAY_NAME};
        int[] toViews = {android.R.id.text1};

        mAdapter = new SimpleCursorAdapter(getView().getContext(),
                android.R.layout.simple_list_item_1, null,
                fromColumns, toViews, 0);
        setListAdapter(mAdapter);


        getLoaderManager().initLoader(0, null, this);
        */
        this.populate();
        adapter = new ArrayAdapter(
                getActivity(),android.R.layout.simple_list_item_1,
                dates);
        setListAdapter(adapter);

    }
    private ArrayList<String> populate(){
        return populate(mocapDirQuery);
    }
    private ArrayList<String> populate(String queryString) {
        dates = new ArrayList<String>();
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        posToDirMap = new HashMap<Integer,String>();

        try {
            URL url = new URL(queryString);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();
            BufferedReader bRead = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            //read through the json data
            String next;
            while((next = bRead.readLine()) != null) {
                JSONObject returnedData = new JSONObject(next);
                String date;
                JSONArray availableDates = returnedData.getJSONArray("fileNames");
                for(int i =0; i< availableDates.length(); i++) {
                     date = availableDates.getString(i);
                    dates.add(date);
                    posToDirMap.put(i,date);
                }
                currDir = returnedData.getString("currentDir");
            }
        }catch (MalformedURLException e) {
            e.printStackTrace();
        }catch (IOException e) {
            e.printStackTrace();
        }catch (JSONException e) {
            e.printStackTrace();
        }

        return dates;
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        //get the date the user clicked
        String date = posToDirMap.get(position);
        if(!date.contains("jpg")) {
            String queryDate = mocapDirQuery + "?subDir=" + date;
            populate(queryDate);
            adapter.clear();
            adapter.addAll(dates);
            adapter.notifyDataSetChanged();
        }else{
            String getImageQuery = baseURL +"/images?imageLoc="+currDir+"/"+date;
            Bundle savedImageState = new Bundle();
            savedImageState.putString("uriString", baseURL);
            Fragment imageFrag = new ImageViewFragment();
            imageFrag.setArguments(savedImageState);
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.replace(R.id.content_frame, imageFrag);
            transaction.addToBackStack(null);
            transaction.commit();
        }


    }
}

