package drfresh.securitywatch3.fragments;

import android.app.Fragment;
import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;

import java.util.HashMap;
import java.util.Map;

import drfresh.securitywatch3.R;

/**
 * Fragment utilizes surface view to stream from a live ip camera
 */
public class ViewLiveStreamFragment extends Fragment implements MediaPlayer.OnPreparedListener,
        SurfaceHolder.Callback {
    final static String STREAM_URL = "http://192.168.1.9:8083/";
    final static String USERNAME = null;
    final static String PASSWORD = null;

    private MediaPlayer _mediaPlayer;
    private SurfaceHolder _surfaceHolder;


    public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        // Configure the view that renders live video.
        SurfaceView surfaceView = (SurfaceView) inflater.inflate(R.layout.fragment_stream, container, false);
        //Or extend FragmentActivity, and get the view

        _surfaceHolder = surfaceView.getHolder();
        _surfaceHolder.addCallback(this);
        _surfaceHolder.setFixedSize(320, 240);
        getActivity().setTitle("Live Stream");
        return surfaceView;
    }

    /* SurfaceHolder.Callback */
    @Override
    public void surfaceChanged(
            SurfaceHolder sh, int f, int w, int h) {}

    @Override
    public void surfaceCreated(SurfaceHolder sh) {
        _mediaPlayer = new MediaPlayer();
        _mediaPlayer.setDisplay(_surfaceHolder);

        Context context = getView().getContext();

        Uri source = Uri.parse(STREAM_URL);

        try {
            // Specify the IP camera's URL and auth headers(auth headers currently removed).
            _mediaPlayer.setDataSource(context, source);

            // Begin the process of setting up a video stream.
            _mediaPlayer.setOnPreparedListener(this);
            _mediaPlayer.prepareAsync();
        }
        catch (Exception e) {}
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder sh) {
        _mediaPlayer.release();
    }

    /**
     * Method to be used if credentials are required to authenticate
     * @return connection headers
     */
    private Map<String, String> getRtspHeaders() {
        Map<String, String> headers = new HashMap<>();
        String basicAuthValue = getBasicAuthValue(USERNAME, PASSWORD);
        headers.put("Authorization", basicAuthValue);
        return headers;
    }

    /**
     * Converts user readable username and password to base64 encoded headers
     * @param usr
     * @param pwd
     * @return base64 encoded username
     */
    private String getBasicAuthValue(String usr, String pwd) {
        String credentials = usr + ":" + pwd;
        int flags = Base64.URL_SAFE | Base64.NO_WRAP;
        byte[] bytes = credentials.getBytes();
        return "Basic " + Base64.encodeToString(bytes, flags);
    }

    /* MediaPlayer.OnPreparedListener */
    @Override
    public void onPrepared(MediaPlayer mp) {
        _mediaPlayer.start();
    }

}
