package com.example.myapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

public class MainScreen extends AppCompatActivity {

    private TextView User_name;
    private TextView User_email;

    private FusedLocationProviderClient fusedLocationClient;

    public static Double latitude = 234.53;
    public static Double longitude = 434.54;
    @SuppressLint("MissingPermission")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("MainScreen","Open");
        setContentView(R.layout.activity_main_screen);

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        // Got last known location. In some rare situations this can be null.
                        if (location != null) {
                            System.out.println("lat: "+location.getLatitude());
                            latitude=location.getLatitude();
                            longitude=location.getLongitude();
                            // Logic to handle location object
                        }else{
                            System.out.println("Location not found");
                        }
                    }
                });

        User_name = findViewById(R.id.user_name_text);
        User_email = findViewById(R.id.user_email_text);

        User_name.setText(User.getInstance().getName());
        User_email.setText(User.getInstance().getEmail());

        getSupportFragmentManager().beginTransaction().add(R.id.fragment,new SearchCreateFragment()).commit();

    }


//    @Override
//    public void onBackPressed() {
//
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setMessage("Do you want to sign out?")
//                .setCancelable(false)
//                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//
//                    public void onClick(DialogInterface dialog, int id) {
//                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
//                        startActivity(intent);
//                        finish();
//                    }
//                })
//                .setNegativeButton("No", new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int id) {
//                        dialog.cancel();
//                    }
//                });
//        AlertDialog alert = builder.create();
//        alert.show();
//
//    }
}