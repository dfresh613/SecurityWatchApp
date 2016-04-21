package drfresh.securitywatch3.fragments;

import android.app.Fragment;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import drfresh.securitywatch3.R;

/**
 * Created by derohde on 4/21/16.
 */
public class ImageViewFragment extends Fragment {
    public String uriString;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        this.uriString = getArguments().getString("uriString");
        Uri imgUri= Uri.parse(uriString);

        ImageView imageView = new ImageView(getActivity());
        imageView.setImageURI(null);
        imageView.setImageURI(imgUri);

    }
}
