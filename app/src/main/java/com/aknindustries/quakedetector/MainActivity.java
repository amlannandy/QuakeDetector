package com.aknindustries.quakedetector;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements ListItemClicked {

    ProgressBar progressBar;
    RecyclerView recyclerView;
    EarthquakeAdapter earthquakeAdapter;
    ArrayList<Earthquake> earthquakes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressBar = findViewById(R.id.progress_bar);
        recyclerView = findViewById(R.id.custom_list);
        earthquakes = new ArrayList<>();
        fetchEarthquakeData();
    }

    private void fetchEarthquakeData() {
        progressBar.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.INVISIBLE);
        String url = "https://earthquake.usgs.gov/fdsnws/event/1/query?starttime=2020-02-15&endtime=2020-02-22&minmagnitude=4.5&format=geojson";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
            response -> {
                try {
                    JSONArray earthquakeData = response.getJSONArray("features");
                    for (int i = 0; i < earthquakeData.length(); i++) {
                        JSONObject object = (JSONObject) earthquakeData.get(i);
                        JSONObject properties = object.getJSONObject("properties");
                        String location = properties.getString("place");
                        double magnitude = properties.getDouble("mag");
                        long timestamp = properties.getLong("time");
                        String earthquakeUrl = properties.getString("url");

                        String color;
                        if (magnitude > 5.5) {
                            color = "#f85959"; //Red
                        } else if (magnitude > 5) {
                            color = "#feff89";
                        } else {
                            color = "#a7ff83";
                        }

                        Earthquake earthquake = new Earthquake(location, magnitude, timestamp, color, earthquakeUrl);
                        earthquakes.add(earthquake);
                    }
                    progressBar.setVisibility(View.INVISIBLE);
                    recyclerView.setVisibility(View.VISIBLE);
                    recyclerView.setLayoutManager(new LinearLayoutManager(this));
                    earthquakeAdapter = new EarthquakeAdapter(earthquakes, this);
                    recyclerView.setAdapter(earthquakeAdapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }, error -> Log.d("Error", error.getMessage())
        );
        VolleySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest);
    }

    @Override
    public void onClick(String url) {
        Intent intent = new Intent(this, WebActivity.class);
        intent.putExtra("url", url);
        startActivity(intent);
    }
}