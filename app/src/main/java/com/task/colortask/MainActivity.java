package com.task.colortask;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.widget.AdapterView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private AdapterSingleViewItem adapter; // Update the adapter reference to your custom adapter class
    private List<String> colorNames;
    private List<String> colorCodes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.rv_color);

        // Assuming you have already loaded the color data from the JSON file
        loadJson(); // Load the JSON data

        adapter = new AdapterSingleViewItem(colorNames, colorCodes); // Pass the color data to the adapter

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    private void loadJson() {
        try {
            // Load JSON from the "data.json" file in the assets folder
            InputStream inputStream = getAssets().open("data.json");
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();

            // Parse the JSON data
            String json = new String(buffer, StandardCharsets.UTF_8);
            JSONArray jsonArray = new JSONArray(json);
            int max = jsonArray.length();

            colorNames = new ArrayList<>();
            colorCodes = new ArrayList<>();

            for (int i = 0; i < max; i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String colorName = jsonObject.getString("color_name");
                String colorCode = jsonObject.getString("code");
                colorNames.add(colorName);
                colorCodes.add(colorCode);
            }

        } catch (Exception e) {
            Log.d("json error", String.valueOf(e));
        }
    }
}
