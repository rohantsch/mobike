package com.hantsch.rossana.mobike;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

public class CrearBikeActivity extends AppCompatActivity {

    Spinner spinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_bike);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        spinner = (Spinner) findViewById(R.id.spinner);

        String[] colores = new String[]{"Rojo","Azul", "Amarillo", "Cafe", "Negro", "Blanco", "Morado", "Verde", "Naranjo"};
        ArrayAdapter<String> gameKindArray = new ArrayAdapter<String>(CrearBikeActivity.this,android.R.layout.simple_spinner_item, colores);
        gameKindArray.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(gameKindArray);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(CrearBikeActivity.this, "Bike registrada :)", Toast.LENGTH_LONG).show();
                finish();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

}
