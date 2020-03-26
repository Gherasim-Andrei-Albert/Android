package com.example.egames;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;

/*
!!!!!!!!!!!!!!!!!!!!!!!!
########################
LABORATOR 5 :
PreferenceActivity care are un switch pentru dark mode
layoutul pentru preferences se gaseste re/xml/preferences.xml
metodele de lyfecycle asigura setare temei corespunzatoare cu preferences
un listener asigura schimbarea temei imediat ce switchul dark_mode e schimbata de utilizator
*/


public class PreferencesActivity extends AppCompatActivity implements
        SharedPreferences.OnSharedPreferenceChangeListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        SharedPreferences sharedPreferences =
                PreferenceManager.getDefaultSharedPreferences(this);
        boolean dark = sharedPreferences.getBoolean("dark_mode", false);
        if(dark){
            setTheme(R.style.Theme_AppCompat);
        }else{
            setTheme(R.style.Theme_AppCompat_DayNight);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.settings, new SettingsFragment())
                .commit();
        final Activity activity = this;
        PreferenceManager.getDefaultSharedPreferences(this).registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if(key.equals("dark_mode")){
            recreate();
        }
    }


    public static class SettingsFragment extends PreferenceFragmentCompat {
        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            setPreferencesFromResource(R.xml.preferences, rootKey);
        }
    }
}

/*############################*/