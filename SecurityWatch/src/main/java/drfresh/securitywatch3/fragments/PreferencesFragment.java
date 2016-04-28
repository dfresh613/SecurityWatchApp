package drfresh.securitywatch3.fragments;

import android.os.Bundle;
import android.preference.PreferenceFragment;

import drfresh.securitywatch3.R;

public class PreferencesFragment extends PreferenceFragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Load the preferences from an XML resource
        addPreferencesFromResource(R.xml.settings_layout);
    }

}