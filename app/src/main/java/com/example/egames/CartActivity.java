package com.example.egames;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;


public class CartActivity extends AppCompatActivity {

    public boolean dark;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences sharedPreferences =
                PreferenceManager.getDefaultSharedPreferences(this);
        boolean dark = sharedPreferences.getBoolean("dark_mode", false);
        this.dark = dark;
        if(dark){
            setTheme(R.style.Theme_AppCompat);
        }else{
            setTheme(R.style.Theme_AppCompat_DayNight);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        FileInputStream fis = null;
        try {
            fis = openFileInput("cart");
            InputStreamReader inputStreamReader =
                    new InputStreamReader(fis);
            StringBuilder stringBuilder = new StringBuilder();

            BufferedReader reader = new BufferedReader(inputStreamReader);
            ArrayList<String> games = new ArrayList<>();
            String line = reader.readLine();
            games.add(line);
            while (line != null) {;
                games.add(line);
                Log.d("EGames",line);
                line = reader.readLine();
            }

            ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                    android.R.layout.simple_list_item_1, games);


            ListView listView = findViewById(R.id.cartList);
            listView.setAdapter(adapter);
        } catch (IOException e) {
            // Error occurred when opening raw file for reading.
        }
    }

    @Override
    protected void onStart() {
        SharedPreferences sharedPreferences =
                PreferenceManager.getDefaultSharedPreferences(this /* Activity context */);
        boolean dark = sharedPreferences.getBoolean("dark_mode", false);


        if(this.dark!=dark)
            recreate();

        super.onStart();
    }

    @Override
    protected void onRestart() {
        SharedPreferences sharedPreferences =
                PreferenceManager.getDefaultSharedPreferences(this /* Activity context */);
        boolean dark = sharedPreferences.getBoolean("dark_mode", false);

        if(this.dark!=dark)
            recreate();

        super.onRestart();
    }

    @Override
    protected void onResume() {
        SharedPreferences sharedPreferences =
                PreferenceManager.getDefaultSharedPreferences(this /* Activity context */);
        boolean dark = sharedPreferences.getBoolean("dark_mode", false);


        if(this.dark!=dark)
            recreate();

        super.onResume();
    }
}