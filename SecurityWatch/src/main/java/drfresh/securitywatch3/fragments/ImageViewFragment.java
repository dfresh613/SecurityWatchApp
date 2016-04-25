package drfresh.securitywatch3.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;


import java.util.ArrayList;

import drfresh.securitywatch3.R;


/**
* Views an image from a URL
 */
public class ImageViewFragment extends Fragment {
    public ArrayList<String> uriString;
    public static final String URI_STRING_KEY = "uriString";
    //current picture position
    public ImageView imageView;
    int currPosition = 0;
    Button nextButton;
    Button previousButton;
    public TextView tv;
    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        this.uriString = getArguments().getStringArrayList(URI_STRING_KEY);
        //pull the image view from the relative layout
        RelativeLayout im=(RelativeLayout)inflater.inflate(R.layout.image_view, container, false);
        imageView =(ImageView)im.findViewById(R.id.imageView1);
        tv = (TextView)im.findViewById(R.id.textView);

        nextButton = (Button)im.findViewById(R.id.but2);
        nextButton.setOnClickListener(new View.OnClickListener() {
            //if nextbutton is clicked increment picture position, otherwise wrap around
            @Override
            public void onClick(View view) {
                if(currPosition!= (uriString.size()-1)){
                    currPosition ++;
                }else{
                    currPosition=0;
                }
                loadPicture(currPosition);
            }
        });

        previousButton = (Button)im.findViewById(R.id.button);
        previousButton.setOnClickListener(new View.OnClickListener() {
            //if prev is clicked subtract picture position, otherwise wrap around
            @Override
            public void onClick(View view) {
                if(currPosition!= 0){
                    currPosition --;
                }else{
                    currPosition=uriString.size()-1;
                }
                loadPicture(currPosition);

            }
        });
        getActivity().setTitle("Historical captures");
        loadPicture(currPosition);
        return im;
    }

    private void loadPicture(int position){
        tv.setText((position+1)+"/"+(uriString.size()));
        Picasso.with(getActivity()).load(uriString.get(position)).into(imageView);
    }



}
