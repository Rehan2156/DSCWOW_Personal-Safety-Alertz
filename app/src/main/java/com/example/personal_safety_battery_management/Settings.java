package com.example.personal_safety_battery_management;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.material.bottomnavigation.BottomNavigationView;

  public class Settings extends AppCompatActivity {

      Button btPicker;
      TextView textview;
      int PLACE_PICKER_REQUEST = 1;

      @Override
      protected void onCreate(Bundle savedInstanceState) {
          super.onCreate(savedInstanceState);
          setContentView(R.layout.activity_settings);

          //----------------LOCATION PICKER---------------------

          btPicker = findViewById(R.id.bt_picker);
          textview = findViewById(R.id.text_view);

          btPicker.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
                  try {
                      startActivityForResult(builder.build(Settings.this),
                              PLACE_PICKER_REQUEST);
                  } catch (GooglePlayServicesRepairableException e) {
                      e.printStackTrace();
                  } catch (GooglePlayServicesNotAvailableException e) {
                      e.printStackTrace();
                  }

              }
          });


          //-----------------------BOTTOM NAVIAGATION---------------------------

          BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

          bottomNavigationView.setSelectedItemId(R.id.settings);

          bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
              @Override
              public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                  switch (menuItem.getItemId()) {
                      case R.id.dashboard:
                          startActivity(new Intent(getApplicationContext(),
                                  MainActivity.class));
                          overridePendingTransition(0, 0);
                          return true;

                      case R.id.about:
                          startActivity(new Intent(getApplicationContext(),
                                  About.class));
                          overridePendingTransition(0, 0);
                          return true;

                      case R.id.settings:
                          return true;

                      case R.id.description:
                          startActivity(new Intent(getApplicationContext(),
                                  Description.class));
                          overridePendingTransition(0, 0);
                          return true;

                      case R.id.close_friends:
                          startActivity(new Intent(getApplicationContext(),
                                  Close_Friends.class));
                          overridePendingTransition(0, 0);
                          return true;
                  }
                  return false;
              }
          });
      }

      @Override
      protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
          if(requestCode==PLACE_PICKER_REQUEST){
              if(resultCode==RESULT_OK){
                  Place place = PlacePicker.getPlace(data, this);
                  StringBuilder stringBuilder = new StringBuilder();
                  String latitude = String.valueOf(place.getLatLng().latitude);
                  String longitude = String.valueOf(place.getLatLng().longitude);
                  stringBuilder.append("LATITUDE: ");
                  stringBuilder.append(latitude);
                  stringBuilder.append("\nLONGITUDE: ");
                  stringBuilder.append(longitude);
                  textview.setText(stringBuilder.toString());




              }
          }
      }
  }

