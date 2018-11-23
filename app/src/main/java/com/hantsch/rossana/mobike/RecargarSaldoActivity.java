package com.hantsch.rossana.mobike;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class RecargarSaldoActivity extends AppCompatActivity {

    Spinner monto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recargar_saldo);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        monto = (Spinner) findViewById(R.id.monto);

        String[] colores = new String[]{"Seleccionar monto","5.000", "10.000", "15.000", "20.000"};
        ArrayAdapter<String> gameKindArray = new ArrayAdapter<String>(RecargarSaldoActivity.this,android.R.layout.simple_spinner_item, colores);
        gameKindArray.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        monto.setAdapter(gameKindArray);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DownloadDataFromServer().execute();
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


    private class DownloadDataFromServer extends AsyncTask<String, Integer, String> {
        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            // Instantiate ProgressDialog and Set style to STYLE_HORIZONTAL
            dialog = new ProgressDialog(RecargarSaldoActivity.this);
            dialog.setIndeterminate(false);
            dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            dialog.setCancelable(false);
            dialog.setTitle("Carga remota");
            dialog.setMessage("Cargando saldo ....");
            dialog.show();

            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... urls) {

            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "";
        }

        protected void onProgressUpdate(Integer... progress) {
            // Update the progress
            dialog.setProgress(progress[0]);
        }

        @Override
        protected void onPostExecute(String result) {
            // My code
            dialog.dismiss();




            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                    RecargarSaldoActivity.this);

            alertDialogBuilder.setTitle("Carga remota");

            // set dialog message
            alertDialogBuilder
                    .setMessage("Su monto ha sido cargado, exitosamente. :)")
                    .setCancelable(false)
                    .setPositiveButton("Aceptar",new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog,int id) {
                            // if this button is clicked, close
                            finish();
                        }
                    });

            // create alert dialog
            AlertDialog alertDialog = alertDialogBuilder.create();

            // show it
            alertDialog.show();


        }
    }

}
