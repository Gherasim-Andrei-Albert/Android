package com.example.egames;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    final String[] games = {"The Witcher", "Batman: Arkham City", "Grand Theft Auto"};
    final int[] imgIDs = {R.drawable.the_witcher, R.drawable.batman_arkham_city, R.drawable.grand_theft_auto};
    final double[] prices = {193.36, 77.35, 121.38};
    private int currPosition = -1;

    public boolean dark;


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.setings:
                Intent intent = new Intent(this,PreferencesActivity.class);
                startActivity(intent);
                return true;
            case R.id.cart:
                intent = new Intent(this,CartActivity.class);
                startActivity(intent);
                return true;
            case R.id.sensors:
                intent = new Intent(this,SensorInfoActivity.class);
                startActivity(intent);
                return true;
            case R.id.location:
                intent = new Intent(this,LocationActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    void popUp(final Context ctx){

        ImageView img = new ImageView(ctx);
        img.setImageDrawable(ctx.getResources().getDrawable(imgIDs[currPosition]));
        new AlertDialog.Builder(ctx)
                .setMessage("price: "+prices[currPosition]+" lei")
                .setTitle(games[currPosition])
                .setView(img)
                .setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        currPosition = -1;
                    }
                })
                .setPositiveButton("add to cart", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        try {
                            FileOutputStream f = openFileOutput("cart",MODE_PRIVATE|MODE_APPEND);
                            f.write((games[currPosition]+"\n").getBytes());
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        currPosition = -1;
                        Toast.makeText(ctx,"Game added successfully!",Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        currPosition = -1;
                    }
                })
                .show();

    }


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
        setContentView(R.layout.activity_main);

        Log.d("EGames","onCreate");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, games);


        ListView listView = findViewById(R.id.gamesList);
        listView.setAdapter(adapter);


        final Activity activity = this;

        AdapterView.OnItemClickListener messageClickedHandler = new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView parent, View v, final int position, long id) {

                currPosition = position;
                popUp(activity);

            }
        };

        listView.setOnItemClickListener(messageClickedHandler);


    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("position",currPosition);
    }


    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        if (savedInstanceState != null)
            currPosition = savedInstanceState.getInt("position");

        if(currPosition!=-1){
            popUp(this);
        }

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.app_menu, menu);
        return true;
    }



    @Override
    protected void onStart() {

        SharedPreferences sharedPreferences =
                PreferenceManager.getDefaultSharedPreferences(this /* Activity context */);
        boolean dark = sharedPreferences.getBoolean("dark_mode", false);

        if(this.dark!=dark)
            recreate();

        super.onStart();
        Log.d("EGames","onStart");
    }

    @Override
    protected void onRestart() {

        SharedPreferences sharedPreferences =
                PreferenceManager.getDefaultSharedPreferences(this /* Activity context */);
        boolean dark = sharedPreferences.getBoolean("dark_mode", false);

        if(this.dark!=dark)
            recreate();

        super.onRestart();
        Log.d("EGames","onRestart");
    }

    @Override
    protected void onResume() {

        SharedPreferences sharedPreferences =
                PreferenceManager.getDefaultSharedPreferences(this /* Activity context */);
        boolean dark = sharedPreferences.getBoolean("dark_mode", false);


        if(this.dark!=dark)
            recreate();

        super.onResume();
        Log.d("EGames","onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("EGames","onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("EGames","onPause");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("EGames","onDestroy");
    }
}
