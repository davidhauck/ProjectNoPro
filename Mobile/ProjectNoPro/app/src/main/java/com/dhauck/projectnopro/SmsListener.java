package com.dhauck.projectnopro;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.provider.Telephony;
import android.telephony.SmsMessage;
import android.util.Log;

import com.dhauck.projectnopro.Models.Message;
import com.dhauck.projectnopro.classes.WebService;

import java.security.MessageDigestSpi;

/**
 * Created by david on 6/10/2016.
 */
public class SmsListener extends BroadcastReceiver
{
    private SharedPreferences preferences;

    @Override
    public void onReceive(Context context, Intent intent)
    {
        Log.i("Msg", "SMS Received");
        if(intent.getAction().equals("android.provider.Telephony.SMS_RECEIVED")){
            Bundle bundle = intent.getExtras();           //---get the SMS message passed in---
            Object [] pdus = (Object[]) bundle.get("pdus");

            SmsMessage[] messages = new SmsMessage[pdus.length];

            String strMessage = "";
            for (int i = 0; i < messages.length; i++)
            {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    String format = bundle.getString("format");
                    messages[i] = SmsMessage.createFromPdu((byte[]) pdus[i], format);
                }
                else {
                    messages[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
                }

                strMessage += messages[i].getMessageBody();
                strMessage += "\n";
            }

            String from = messages[0].getDisplayOriginatingAddress();
            String from2 = messages[0].getOriginatingAddress();
            String from3 = messages[0].getDisplayMessageBody();

            WebService.sendTextMessage(0, from, strMessage);
        }
    }
}
