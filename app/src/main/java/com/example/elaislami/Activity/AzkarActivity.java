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
import android.widget.Toast;

import com.example.elaislami.Adapter.AzkarAdapter;
import com.example.elaislami.Model.AzkarModel;
import com.example.elaislami.R;

import java.util.ArrayList;

import es.dmoral.toasty.Toasty;


public class AzkarActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    String[] azkar = {"Morning Azkar", "Night Azkar"};


    ImageButton back_btn;
    ArrayList<AzkarModel> azkarModels=new ArrayList<>();
    RecyclerView azkarRV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_azkar);

        Spinner spin = (Spinner) findViewById(R.id.azkar_spinner);
        spin.setOnItemSelectedListener(this);

        ArrayAdapter azkarAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, azkar);
        azkarAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(azkarAdapter);

        Toolbar toolbar = findViewById(R.id.tool_bar_azkar);

        setSupportActionBar(toolbar);

        back_btn = findViewById(R.id.back_btn);

        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(AzkarActivity.this, MainActivity.class);
                i.putExtra("back_state", 0);
                startActivity(i);

            }
        });

        azkarRV =findViewById(R.id.AzkarRV);
        azkarModels.add(new AzkarModel("بسـم الله الـذي لا يضر مع اسمه شيء في الأرض ولا في السماء وهو السميع العليم", 100));
        azkarModels.add(new AzkarModel("بسـم الله الـذي لا يضر مع اسمه شيء في الأرض ولا في السماء وهو السميع العليم", 100));
        azkarModels.add(new AzkarModel("بسـم الله الـذي لا يضر مع اسمه شيء في الأرض ولا في السماء وهو السميع العليم", 100));
        azkarModels.add(new AzkarModel("بسـم الله الـذي لا يضر مع اسمه شيء في الأرض ولا في السماء وهو السميع العليم", 100));
        azkarModels.add(new AzkarModel("بسـم الله الـذي لا يضر مع اسمه شيء في الأرض ولا في السماء وهو السميع العليم", 100));
        azkarModels.add(new AzkarModel("بسـم الله الـذي لا يضر مع اسمه شيء في الأرض ولا في السماء وهو السميع العليم", 100));
        azkarModels.add(new AzkarModel("بسـم الله الـذي لا يضر مع اسمه شيء في الأرض ولا في السماء وهو السميع العليم", 100));
        azkarModels.add(new AzkarModel("بسـم الله الـذي لا يضر مع اسمه شيء في الأرض ولا في السماء وهو السميع العليم", 100));


        AzkarAdapter azkarAdapterList=new AzkarAdapter(azkarModels);
        azkarRV.setLayoutManager(new LinearLayoutManager(AzkarActivity.this));
        azkarRV.setAdapter(azkarAdapterList);

    }

    @Override
    public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
        Toasty.info(AzkarActivity.this, azkar[position], Toasty.LENGTH_LONG, true).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }
}