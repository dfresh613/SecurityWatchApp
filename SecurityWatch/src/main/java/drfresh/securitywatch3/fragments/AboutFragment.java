package drfresh.securitywatch3.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;

import drfresh.securitywatch3.R;

public class AboutFragment extends Fragment {

    public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        // Configure the view that renders live video.
        View rView =  inflater.inflate(R.layout.about_layout, container, false);

        return rView;
    }

}
