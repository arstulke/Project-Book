package arstulke.projectbook.activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.support.v7.widget.Toolbar;
import android.view.View;

import arstulke.projectbook.R;
import arstulke.projectbook.controller.Settings;
import arstulke.projectbook.utils.StyleManager;

public class SettingsActivity extends PreferenceActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Toolbar toolbar = (Toolbar) findViewById(R.id.settingsToolbar);
        toolbar.setTitleTextColor(Color.parseColor("#ffffff"));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        StyleManager.setColors(getWindow());
        StyleManager.setToolbarColor(toolbar);
        addPreferencesFromResource(R.xml.preferences);
    }

    @SuppressWarnings("unused")
    public static class SettingsFragment extends PreferenceFragment {
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.preferences);
        }
    }

    public static boolean getBooleanPreference(Settings.Preference preference, Context context) {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPref.getBoolean(preference.getKey(), (Boolean) preference.getDefaultValue());
    }

    public static String getStringPreference(Settings.Preference preference, Context context) {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPref.getString(preference.getKey(), (String) preference.getDefaultValue());
    }
}
