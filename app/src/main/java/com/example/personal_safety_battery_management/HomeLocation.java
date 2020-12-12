package com.example.personal_safety_battery_management;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;

public class HomeLocation extends AppCompatActivity {
    Button btPicker;
    TextView textview;
    int PLACE_PICKER_REQUEST = 1;
    public static double homeLong=0,homeLat=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_location);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.appbar_background));

        btPicker = findViewById(R.id.bt_picker);
        textview = findViewById(R.id.text_view);

        btPicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
                try {
                    startActivityForResult(builder.build(HomeLocation.this),
                            PLACE_PICKER_REQUEST);
                } catch (GooglePlayServicesRepairableException e) {
                    e.printStackTrace();
                } catch (GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                }
                System.out.println("Location hai"+MainActivity.currLat);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode==PLACE_PICKER_REQUEST){
            if(resultCode==RESULT_OK){
                Place place = PlacePicker.getPlace(data, this);
//                StringBuilder stringBuilder = new StringBuilder();
                double latitude = (place.getLatLng().latitude);
                double longitude = (place.getLatLng().longitude);
                this.homeLat=latitude;
                this.homeLong=longitude;
//                stringBuilder.append("LATITUDE: ");
//                stringBuilder.append(latitude);
//                stringBuilder.append("\nLONGITUDE: ");
//                stringBuilder.append(longitude);
                textview.setText("LATITUDE IS "+latitude+". LONGITUDE IS "+longitude);
                showMessageOKCancel("ARE YOU SURE?");


            }
        }

    }
    private void showMessageOKCancel(String message) {
        new android.app.AlertDialog.Builder(HomeLocation.this)
                .setMessage(message)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent myIntent = new Intent(getApplicationContext(), Dashboard.class);
                        startActivityForResult(myIntent, 0);
                    }
                })
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent myIntent = new Intent(getApplicationContext(), Dashboard.class);
        startActivityForResult(myIntent, 0);
    }
}
