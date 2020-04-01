package com.example.egames;

import androidx.appcompat.app.AppCompatActivity;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SensorInfoActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager mSensorManager;
    ListView lst;
    class SensorInfo{
        public String name;
        public String values;
    }
    ArrayList<String> sensorsData = new ArrayList<>();
    Map<String,String> printedSensors = new HashMap<>();
    ArrayAdapter<String> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor_info);
        mSensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        lst = findViewById(R.id.sensorList);
        sensorsData = new ArrayList<String>(printedSensors.values());
        adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, sensorsData);
        lst.setAdapter(adapter);
    }

    protected void onResume() {
        super.onResume();
        List<Sensor> sensors = mSensorManager.getSensorList(Sensor.TYPE_ALL);
        for (Sensor sensor: sensors){
            mSensorManager.registerListener(this,sensor,SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
    }

    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    public void onSensorChanged(SensorEvent event) {
        if(!printedSensors.containsKey(event.sensor.getName())){
            String values = event.sensor.getName()+": ";
            for(float value: event.values){
                values+= value + "; ";
            }
            printedSensors.put(event.sensor.getName(),values);
            sensorsData = new ArrayList<String>(printedSensors.values());
            adapter = new ArrayAdapter<>(this,
                    android.R.layout.simple_list_item_1, sensorsData);
            lst.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }
    }
}
