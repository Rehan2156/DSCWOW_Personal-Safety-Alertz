package com.example.personal_safety_battery_management;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Close_Friends extends AppCompatActivity {
    Button add_c;
    EditText add_no;
    Cursor cursor = null;
    static String phone1,phone2,phone3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_close__friends);

        onClickAdd1();
        onClickAdd2();
        onClickAdd3();

        //------------Bottom-Navigation---------
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        bottomNavigationView.setSelectedItemId(R.id.close_friends);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch(menuItem.getItemId()){
                    case R.id.dashboard:
                        startActivity(new Intent(getApplicationContext(),
                                MainActivity.class));
                        overridePendingTransition(0,0);
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
                        return true;
                }
                return false;
            }
        });
    }

    public void onClickAdd1()
    {
        add_c = (Button)findViewById(R.id.addContact1);

        //-----This opens up a list of contacts as a new activity
        try {
            add_c.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(Intent.ACTION_PICK);
                    i.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE);
                    startActivityForResult(i, 1);
                }
            });
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void onClickAdd2()
    {
        add_c = (Button)findViewById(R.id.addContact2);

        //-----This opens up a list of contacts as a new activity
        try {
            add_c.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(Intent.ACTION_PICK);
                    i.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE);
                    startActivityForResult(i, 2);
                }
            });
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void onClickAdd3()
    {
        add_c = (Button)findViewById(R.id.addContact3);

        //-----This opens up a list of contacts as a new activity
        try {
            add_c.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(Intent.ACTION_PICK);
                    i.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE);
                    startActivityForResult(i, 3);
                }
            });
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        //--------if addContact1 button is clicked
        if((requestCode==1) && (resultCode==RESULT_OK))
        {
            try{
                Uri uri = data.getData();
                cursor = getContentResolver().query(uri, new String[] {ContactsContract.CommonDataKinds.Phone.NUMBER}, null, null, null);
                if(cursor != null && cursor.moveToNext())
                {
                    String phone = cursor.getString(0);
                    add_no = (EditText)findViewById(R.id.phone1);
                    add_no.setText(phone);
                    this.phone1=phone;
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }

        //--------if addContact2 button is clicked
        if((requestCode==2) && (resultCode==RESULT_OK))
        {
            try{
                Uri uri = data.getData();
                cursor = getContentResolver().query(uri, new String[] {ContactsContract.CommonDataKinds.Phone.NUMBER}, null, null, null);
                if(cursor != null && cursor.moveToNext())
                {
                    String phone = cursor.getString(0);
                    add_no = (EditText)findViewById(R.id.phone2);
                    add_no.setText(phone);
                    this.phone2=phone;
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }

        //--------if addContact3 button is clicked
        if((requestCode==3) && (resultCode==RESULT_OK))
        {
            try{
                Uri uri = data.getData();
                cursor = getContentResolver().query(uri, new String[] {ContactsContract.CommonDataKinds.Phone.NUMBER}, null, null, null);
                if(cursor != null && cursor.moveToNext())
                {
                    String phone = cursor.getString(0);
                    add_no = (EditText)findViewById(R.id.phone3);
                    add_no.setText(phone);
                    this.phone3=phone;
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }
}