package com.example.personal_safety_battery_management;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class Dashboard extends AppCompatActivity {
    TextView tv;
    Button alert;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        tv = findViewById(R.id.loc);
        alert = findViewById(R.id.send_alert);
        double currLat=MainActivity.currLat;
        double currLong=MainActivity.currLong;
        double homeLat=HomeLocation.homeLat;
        double homeLong=HomeLocation.homeLong;
        String diff = String.format("%.2f",distance(currLat,homeLat,currLong,homeLong));
        tv.setText("Distance between home and your current location is "+diff+" km");

        final String msg="Sent from SAFETY BATTERY ALERTZ. My battery is about to die. Current location is https://maps.google.com/?q="+currLat+","+currLong;
        alert.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                final ArrayList<String> phone= new ArrayList<>();
                if(Close_Friends.phone1!=null)
                    phone.add(Close_Friends.phone1);
                if(Close_Friends.phone2!=null)
                    phone.add(Close_Friends.phone2);
                if(Close_Friends.phone3!=null)
                    phone.add(Close_Friends.phone3);
                SMS.sendSMS(phone,msg);
                showMessageOKCancel("Message sent successfully to your trusted contacts. Stay safe \uD83D\uDE00");
            }
        });


        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        bottomNavigationView.setSelectedItemId(R.id.dashboard);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch(menuItem.getItemId()){
                    case R.id.dashboard:
                        return true;

                    case R.id.about:
                        startActivity(new Intent(getApplicationContext(),
                                MainActivity.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.settings:
                        startActivity(new Intent(getApplicationContext(),
                                Settings.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.description:
                        startActivity(new Intent(getApplicationContext(),
                                Description.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.close_friends:
                        startActivity(new Intent(getApplicationContext(),
                                Close_Friends.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });
    }

    public static double distance(double lat1,
                                  double lat2, double lon1,
                                  double lon2)
    {

        // The math module contains a function
        // named toRadians which converts from
        // degrees to radians.
        lon1 = Math.toRadians(lon1);
        lon2 = Math.toRadians(lon2);
        lat1 = Math.toRadians(lat1);
        lat2 = Math.toRadians(lat2);

        // Haversine formula
        double dlon = lon2 - lon1;
        double dlat = lat2 - lat1;
        double a = Math.pow(Math.sin(dlat / 2), 2)
                + Math.cos(lat1) * Math.cos(lat2)
                * Math.pow(Math.sin(dlon / 2),2);

        double c = 2 * Math.asin(Math.sqrt(a));

        // Radius of earth in kilometers. Use 3956
        // for miles
        double r = 6371;

        // calculate the result
        return(c * r);
    }

    private void showMessageOKCancel(String message) {
        new android.app.AlertDialog.Builder(Dashboard.this)
                .setMessage(message)
                .setPositiveButton("OK",null)
                .create()
                .show();
    }
}
