package com.example.suhanshu.newsapp;

import android.content.SharedPreferences;
import android.preference.EditTextPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.prefs.PreferenceChangeEvent;
import java.util.prefs.PreferenceChangeListener;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);
    }
    public static class NewsPreferenceFragment extends PreferenceFragment implements Preference.OnPreferenceChangeListener{
        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.settings_main);
            Preference edit_preference=findPreference(getString(R.string.search_key));
            edit_preference.setOnPreferenceChangeListener(this);
            Preference list_preference=findPreference(getString(R.string.orderkey));
            list_preference.setOnPreferenceChangeListener(this);
            bindPreferenceSummaryToValue(edit_preference);
            bindPreferenceSummaryToValue(list_preference);
        }

        private void bindPreferenceSummaryToValue(Preference preference) {
            SharedPreferences sharedPreferences= PreferenceManager.getDefaultSharedPreferences(preference.getContext());
            String value=sharedPreferences.getString(preference.getKey()," ");
            onPreferenceChange(preference,value);

        }

        @Override
        public boolean onPreferenceChange(Preference preference, Object newValue) {
            String stringValue=newValue.toString();
            if(!(preference instanceof ListPreference)){
                EditTextPreference editTextPreference= (EditTextPreference) preference;
                editTextPreference.setSummary(newValue.toString());
            }
            else if(preference instanceof ListPreference){
                ListPreference listPreference = (ListPreference) preference;
                int prefIndex = listPreference.findIndexOfValue(stringValue);
                if (prefIndex >= 0) {
                    CharSequence[] labels = listPreference.getEntries();
                    preference.setSummary(labels[prefIndex]);
                }
            }
            return true;
        }


    }

}
