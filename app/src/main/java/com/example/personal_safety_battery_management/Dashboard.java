package com.example.personal_safety_battery_management;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Bundle;
import android.text.Editable;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class Dashboard extends AppCompatActivity {
    TextView tv;
    Button alert;
    static int battery_level=0;
    private TextView battery;
    double currLat,currLong;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);



        ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.appbar_background));

        //battery = (TextView)this.findViewById(R.id.text1);
        batterylevel();



        //-------------------SPEED MESSAGES--------------------
        Button button1 = findViewById(R.id.emergency);
        Button button2 = findViewById(R.id.help);
        Button button3 = findViewById(R.id.come_here_asap);
        Button button4 = findViewById(R.id.come_home_late);
        ImageButton button5 = findViewById(R.id.emergency_dial_button);
        final EditText message = findViewById(R.id.editText2);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                message.setText("Emergency!!!");
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                message.setText("Help Me!!!");
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                message.setText("Come here ASAP!!!");
            }
        });


        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                message.setText("I'll come home late");
            }
        });


        //------------------speed dial--------------------

        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSpeedDial();
            }
        });





        tv = findViewById(R.id.loc);
        alert = findViewById(R.id.send_alert);
        currLat=MainActivity.currLat;
        currLong=MainActivity.currLong;
        double homeLat=HomeLocation.homeLat;
        double homeLong=HomeLocation.homeLong;
        String diff = String.format("%.2f",distance(currLat,homeLat,currLong,homeLong));
       // tv.setText("Distance between home and your current location is "+diff+" km");




        alert.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                final ArrayList<String> phone= new ArrayList<>();
                final ArrayList<String> name= new ArrayList<>();

                DataBaseHelper dataBaseHelper = new DataBaseHelper(Dashboard.this);
                List<ContactModel> everyone = dataBaseHelper.getEveryone();
                System.out.println(everyone.toString());
                if(!everyone.isEmpty()) {
                    //-----Solved the problem of app crashing with less than 3 contacts saved
                    try {
                        for(int i=0; i<3; i++) {
                            if(everyone.size()>i) {
                                phone.add(everyone.get(i).getPhone());
                                name.add(everyone.get(i).getName());
                            }
                            else{
                                phone.add(null);
                                name.add(null);
                            }
                        }
//                        phone.add(everyone.get(1).getPhone());
//                        name.add(everyone.get(1).getName());
//                        phone.add(everyone.get(2).getPhone());
//                        name.add(everyone.get(2).getName());
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }

                String msg_temp="";
                String typed_msg = message.getText().toString();
                String loc = "https://maps.google.com/?q="+currLat+","+currLong;
                System.out.println("Typed msg: "+typed_msg);
                if(battery_level<=10)
                {
                    msg_temp="Sent from PERSONAL SAFETY ALERTZ." + typed_msg+" My battery is about to die (Automatic alert).\nBattery: "+battery_level+"%.\nCurrent location: "+loc;
                }
                else
                {
                    msg_temp="Sent from PERSONAL SAFETY ALERTZ." + typed_msg+" (Manual Alert).\nBattery: "+battery_level+"%.\nCurrent location: "+loc;
                }
                AlertModel alertModel = new AlertModel(-1,battery_level,loc,msg_temp,name.get(0),name.get(1),name.get(2),phone.get(0),phone.get(1),phone.get(2));
                boolean success = dataBaseHelper.addOneAlert(alertModel);
                String successMsg= success==true?"Added to database":"Error occurred";
                Toast.makeText(Dashboard.this,successMsg,Toast.LENGTH_SHORT).show();
                SMS.sendSMS(phone,msg_temp);
                showMessageOKCancel("Message sent successfully to your trusted contacts. Stay safe \uD83D\uDE00");
            }
        });






        //---------------------------------------------

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
                                About.class));
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

    private void batterylevel(){
        BroadcastReceiver mBatInfoReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                context.unregisterReceiver(this);
                int raw_level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL,-1);
                int scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE,-1);
                int level =-1;
                if(raw_level>=0 && scale>0){
                    level = (raw_level*100)/scale;
                }
                battery_level = level;
                //battery.setText(String.valueOf(level) + "%");
            }
        };
        IntentFilter batteryLevelFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        registerReceiver(mBatInfoReceiver, batteryLevelFilter);
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


    public void openSpeedDial()
    {
        Intent intent = new Intent(this, Emergency_dial.class);
        startActivity(intent);
    }
}
