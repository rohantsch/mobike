package com.hantsch.rossana.mobike;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;

import com.hantsch.rossana.mobike.adapters.CustomAdapter;
import com.hantsch.rossana.mobike.models.Bikes;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class MisBikesActivity extends AppCompatActivity {

    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mis_bikes);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MisBikesActivity.this, CrearBikeActivity.class);
                startActivity(intent);
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        listView = (ListView) findViewById(R.id.list);


        ArrayList<Bikes> misBikes = new ArrayList<Bikes>();

        misBikes.add(new Bikes("Marca : Mountain Bike", "Modelo : Kato", "Mountain","Rojo"));
        misBikes.add(new Bikes("Marca : Mountain Bike", "Modelo : Pulse", "Mountain","Rojo"));

        CustomAdapter adapter = new CustomAdapter(misBikes,getApplicationContext());
        listView.setAdapter(adapter);

    }

}