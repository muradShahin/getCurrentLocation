package com.muradit.gettingcurrentlocationa;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private GoogleApiClient googleApiClient;
    public static int permission_flag = 444;
    private LocationHelper locationHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


      locationHelper = new LocationHelper.Builder(this)
                .setInterval(5000)
                .setFastestInterval(2000)
                //.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setListener(new LocationHelper.EasyLocationCallback() {
                    @Override
                    public void onGoogleAPIClient(GoogleApiClient googleApiClient, String message) {
                        Log.i("EasyLocationProvider","onGoogleAPIClient: "+message);
                    }

                    @Override
                    public void onLocationUpdated(double latitude, double longitude) throws IOException {
                        Log.i("EasyLocationProvider","onLocationUpdated:: "+ "Latitude: "+latitude+" Longitude: "+longitude);

                        getLocationInfo(latitude,longitude);
                    }

                    @Override
                    public void onLocationUpdateRemoved() {
                        Log.i("EasyLocationProvider","onLocationUpdateRemoved");
                    }
                }).build();

        getLifecycle().addObserver(locationHelper);

       // startProcess();
    }

    private void startProcess() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                requestPermissions(new String[]{
                        Manifest.permission.ACCESS_FINE_LOCATION}, permission_flag);

            }


        }else{

        }


    }







    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if(requestCode ==permission_flag){

            if(grantResults.length>0 && grantResults[0] ==PackageManager.PERMISSION_GRANTED){

            }

        }



        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }


    private void getLocationInfo(double lat,double lng) throws IOException {

        Geocoder geocoder;
        List<Address> addresses;
        geocoder = new Geocoder(this, Locale.getDefault());

        addresses = geocoder.getFromLocation(lat, lng, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5

        String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
        String city = addresses.get(0).getLocality();
        String state = addresses.get(0).getAdminArea();
        String country = addresses.get(0).getCountryName();
        String postalCode = addresses.get(0).getPostalCode();
        String knownName = addresses.get(0).getFeatureName();

        Log.i("info",city +" // "+ knownName +" // "+address +"///" + country);

    }
}