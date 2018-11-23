package com.hantsch.rossana.mobike;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.HashMap;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, OnMapReadyCallback {

    private GoogleMap mMap;
    PlaceAutocompleteFragment placeAutoComplete;
    Button unlock;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        placeAutoComplete = (PlaceAutocompleteFragment) getFragmentManager().findFragmentById(R.id.place_autocomplete);
        unlock = (Button) findViewById(R.id.button);
        unlock.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                try {

                    Intent intent = new Intent("com.google.zxing.client.android.SCAN");
                    intent.putExtra("SCAN_MODE", "QR_CODE_MODE"); // "PRODUCT_MODE for bar codes

                    startActivityForResult(intent, 0);

                } catch (Exception e) {

                    Uri marketUri = Uri.parse("market://details?id=com.google.zxing.client.android");
                    Intent marketIntent = new Intent(Intent.ACTION_VIEW,marketUri);
                    startActivity(marketIntent);

                }
            }
        });
        placeAutoComplete.setHint("¿A dónde voy?");
        placeAutoComplete.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {

                Log.d("Maps", "Place selected: " + place.getName());
            }

            @Override
            public void onError(Status status) {
                Log.d("Maps", "An error occurred: " + status);
            }
        });

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                        HomeActivity.this);

                alertDialogBuilder.setTitle("Saldo");

                // set dialog message
                alertDialogBuilder
                        .setMessage("Su saldo es  : $ 10.500, calcular recorrido?")
                        .setCancelable(false)
                        .setPositiveButton("Confirmar",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                // if this button is clicked, close
                                // current activity
                                unlock.setVisibility(View.VISIBLE);
                                new DownloadDataFromServer().execute();
                            }
                        })
                        .setNegativeButton("Cancelar",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                // if this button is clicked, just close
                                // the dialog box and do nothing
                                dialog.cancel();
                            }
                        });

                // create alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();

                // show it
                alertDialog.show();



                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                //        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng santiago = new LatLng(-33.4472328, -70.5334336);
        mMap.addMarker(new MarkerOptions().position(santiago).title("La reina, Santiago"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(santiago));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(santiago,14));

        Marker bicileta1 = mMap.addMarker(new MarkerOptions()
                .position( new LatLng(-33.439200,-70.553510))
                .title("Bicicleta")
                .snippet("Disponible")
                .anchor(1, 1)
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_bike_round)));

        Marker bicileta2 = mMap.addMarker(new MarkerOptions()
                .position( new LatLng(-33.429400,-70.545730))
                .title("Bicicleta")
                .snippet("Disponible")
                .anchor(1, 1)
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_bike_round)));

        Marker bicileta3 = mMap.addMarker(new MarkerOptions()
                .position( new LatLng(-33.439530,-70.543130))
                .title("Bicicleta")
                .snippet("Disponible")
                .anchor(1, 1)
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_bike_round)));

        Marker bicileta4 = mMap.addMarker(new MarkerOptions()
                .position( new LatLng(-33.444248,-70.543457))
                .title("Bicicleta")
                .snippet("Disponible")
                .anchor(1, 1)
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_bike_round)));

        Marker bicileta5 = mMap.addMarker(new MarkerOptions()
                .position( new LatLng(-33.439870,-70.566600))
                .title("Bicicleta")
                .snippet("Disponible")
                .anchor(1, 1)
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_bike_round)));

        Marker bicileta6 = mMap.addMarker(new MarkerOptions()
                .position( new LatLng(-33.439380,-70.552070))
                .title("Bicicleta")
                .snippet("Disponible")
                .anchor(1, 1)
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_bike_round)));

        Marker bicileta7 = mMap.addMarker(new MarkerOptions()
                .position( new LatLng(-33.433418,-70.537918))
                .title("Bicicleta")
                .snippet("Disponible")
                .anchor(1, 1)
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_bike_round)));

        Marker bicileta8 = mMap.addMarker(new MarkerOptions()
                .position( new LatLng(-33.457170,-70.540820))
                .title("Bicicleta")
                .snippet("Disponible")
                .anchor(1, 1)
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_bike_round)));

        Marker bicileta9 = mMap.addMarker(new MarkerOptions()
                .position( new LatLng(-33.456440,-70.555923))
                .title("Bicicleta")
                .snippet("Disponible")
                .anchor(1, 1)
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_bike_round)));

        Marker bicileta10 = mMap.addMarker(new MarkerOptions()
                .position( new LatLng(-33.447280,-70.519630))
                .title("Bicicleta")
                .snippet("Disponible")
                .anchor(1, 1)
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_bike_round)));





    }

    private class DownloadDataFromServer extends AsyncTask<String, Integer, String> {
        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            // Instantiate ProgressDialog and Set style to STYLE_HORIZONTAL
            dialog = new ProgressDialog(HomeActivity.this);
            dialog.setIndeterminate(false);
            dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            dialog.setCancelable(false);
            dialog.setTitle("Calcular viaje");
            dialog.setMessage("Calculando tu recorrido ....");
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
                    HomeActivity.this);

            alertDialogBuilder.setTitle("Valor viaje");

            // set dialog message
            alertDialogBuilder
                    .setMessage("El valor de su recorrido es de : $ 1.500")
                    .setCancelable(false)
                    .setPositiveButton("Aceptar",new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog,int id) {
                            // if this button is clicked, close
                        }
                    });

            // create alert dialog
            AlertDialog alertDialog = alertDialogBuilder.create();

            // show it
            alertDialog.show();


        }
    }
}
