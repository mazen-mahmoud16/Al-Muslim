package com.example.elaislami.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.elaislami.Adapter.AzkarAdapter;
import com.example.elaislami.Model.AzkarModel;
import com.example.elaislami.R;
import com.example.elaislami.Utils.Utils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.List;

/*
 * Here is Tasbeeh activity class that is triggered when we open choose tasbeeh option from the home fragment
 */
public class TasabehActivity extends AppCompatActivity {

    /*
     * Recycler view and image button
     */
    private ImageButton imgBackBtn;
    private RecyclerView tasabehRV;

    /*
     * To get data from tasbeeh JSON file
     */
    private String jsonFileString;
    private Gson gson;
    private Type listAzkarType;

    // List of tasbeeh
    private List<AzkarModel> tasabeehModelList;

    // Adapter
    private AzkarAdapter tasabeehAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*
         * To create the toolbar
         */
        setContentView(R.layout.activity_tasabeh);

        Toolbar toolbar = findViewById(R.id.tool_bar_tasabeh);

        setSupportActionBar(toolbar);

        /*
         * Assign image button and recycler view
         */
        tasabehRV = findViewById(R.id.TasabehRV);
        imgBackBtn =findViewById(R.id.back_btn);

        /*
         * Fired to handle when user clicks to back button to go back to home fragment in the main activity
         */
        imgBackBtn.setOnClickListener(view -> {
            Intent i=new Intent(TasabehActivity.this,MainActivity.class);
            i.putExtra("back_state",0);
            startActivity(i);

        });

        /*
         * To get data from tasbeeh JSON file
         */
        gson = new Gson();
        listAzkarType = new TypeToken<List<AzkarModel>>() {
        }.getType();
        jsonFileString = Utils.getJsonFromAssets(getApplicationContext(), "tasabeeh.json");
        tasabeehModelList = gson.fromJson(jsonFileString, listAzkarType);

        // Initialize the adapter
        tasabeehAdapter = new AzkarAdapter(tasabeehModelList);

        // set adapter layout
        tasabehRV.setLayoutManager(new LinearLayoutManager(TasabehActivity.this));

        // set adapter
        tasabehRV.setAdapter(tasabeehAdapter);

    }

}