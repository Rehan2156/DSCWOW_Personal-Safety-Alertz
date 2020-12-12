package com.example.personal_safety_battery_management;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class History extends AppCompatActivity {

    ListView lv_customerList;
    DataBaseHelper dataBaseHelper;
    ArrayAdapter alertArrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        lv_customerList=findViewById(R.id.lv_custList);

        dataBaseHelper = new DataBaseHelper(History.this);
        ShowCustomersOnListView(dataBaseHelper);
    }

    private void ShowCustomersOnListView(DataBaseHelper dataBaseHelper) {
        alertArrayAdapter = new ArrayAdapter<AlertModel>(History.this, android.R.layout.simple_list_item_1, dataBaseHelper.getAllAlerts());
        if(alertArrayAdapter.isEmpty()){
            Toast.makeText(History.this,"No entries to show",Toast.LENGTH_SHORT).show();

        }
        else
        lv_customerList.setAdapter(alertArrayAdapter);
    }
}
