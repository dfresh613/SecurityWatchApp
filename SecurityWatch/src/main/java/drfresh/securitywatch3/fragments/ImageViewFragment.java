package drfresh.securitywatch3.fragments;

import android.app.Fragment;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.io.InputStream;
import java.net.URL;

import drfresh.securitywatch3.R;


/**
* Views an image from a URL
 */
public class ImageViewFragment extends Fragment {
    public String uriString;
    public static final String URI_STRING_KEY = "uriString";

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        this.uriString = getArguments().getString(URI_STRING_KEY);

        ImageView imageView;
        imageView = (ImageView) inflater.inflate(R.layout.image_view, container, false);
        Picasso.with(getActivity()).load(uriString).into(imageView);
        getActivity().setTitle("Historical captures");
        return imageView;
    }


}
