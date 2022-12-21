package com.example.elaislami.Activity;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;

import com.example.elaislami.Adapter.AzkarAdapter;
import com.example.elaislami.Utils.Utils;
import com.example.elaislami.Model.AzkarModel;
import com.example.elaislami.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

/*
 * Here is azkar statistics activity that is triggered when we choose azkar option from home fragment
 */
public class AzkarActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    // Assign drop down list values
    private String[] azkar = {"Morning Azkar", "Night Azkar"};

    /*
     * Edit texts, image buttons, recycler view and spinners used
     */
    private Toolbar toolbar;
    private ImageButton imgBackBtn;
    private RecyclerView rvAzkar;
    private Spinner spin;

    /*
     * To get Azkar Json
     */
    private String jsonFileString;
    private Gson gson;
    private Type listAzkarType;

    // Declare adapter
    private AzkarAdapter azkarAdapter;

    // Declare list to be passed to adapter
    private List<AzkarModel> azkarModelList;

    /*
     * Here is on create function
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_azkar);

        /*
         * Assign elements in layout
         */
        spin = findViewById(R.id.azkar_spinner);
        toolbar = findViewById(R.id.tool_bar_azkar);
        rvAzkar =findViewById(R.id.AzkarRV);
        imgBackBtn = findViewById(R.id.back_btn);

        // Set listener on spinner (drop diwn list)
        spin.setOnItemSelectedListener(this);

        // Set adapter to spinner
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, azkar);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(arrayAdapter);

        // Set toolbar
        setSupportActionBar(toolbar);

        // Set layout to recycler view
        rvAzkar.setLayoutManager(new LinearLayoutManager(AzkarActivity.this));

        // Manage json file
        gson = new Gson();
        listAzkarType = new TypeToken<List<AzkarModel>>() { }.getType();

        /*
         * Handle when user clicks back
         */
        imgBackBtn.setOnClickListener(view -> {
            Intent i = new Intent(AzkarActivity.this, MainActivity.class);
            i.putExtra("back_state", 0);
            startActivity(i);
        });
    }

    /*
     * When new item is selected from drop down list
     */
    @Override
    public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {

        // If morning azkar is pressed
        if(position == 0){

            // Get morning list and assign it to adapter
            jsonFileString = Utils.getJsonFromAssets(getApplicationContext(), "morningazkar.json");
            azkarModelList = gson.fromJson(jsonFileString, listAzkarType);
            azkarAdapter =new AzkarAdapter(azkarModelList);
            rvAzkar.setAdapter(azkarAdapter);
        }

        // If evening azkar is pressed
        else if(position==1){

            // Get morning list and assign it to adapter
            jsonFileString = Utils.getJsonFromAssets(getApplicationContext(), "nightazkar.json");
            azkarModelList = gson.fromJson(jsonFileString, listAzkarType);
            azkarAdapter =new AzkarAdapter(azkarModelList);
            rvAzkar.setAdapter(azkarAdapter);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {

    }
}