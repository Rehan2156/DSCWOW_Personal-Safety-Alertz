package com.example.personal_safety_battery_management;

import android.telephony.SmsManager;
import android.widget.Toast;

import java.util.ArrayList;

public class SMS {

    public static void sendSMS(ArrayList<String> a,String msg) {
        String phone_Num = null;
        for (String num : a) {
            if(num != null)
                phone_Num = num;
            String send_msg = msg;
            System.out.println("Phone number is "+phone_Num);
            try {
                SmsManager sms = SmsManager.getDefault(); // using android SmsManager
                sms.sendTextMessage(phone_Num, null, send_msg, null, null); // adding number and text
            } catch (Exception e) {
                Toast.makeText(null, "Sms not Send", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        }
    }
}
