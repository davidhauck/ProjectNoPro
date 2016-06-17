package com.dhauck.projectnopro;

import android.os.AsyncTask;
import android.os.Bundle;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.util.Log;
import android.webkit.CookieManager;

import com.dhauck.projectnopro.Models.Message;
import com.dhauck.projectnopro.classes.LocalStorage;
import com.dhauck.projectnopro.classes.WebService;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by david on 6/10/2016.
 */
public class NotificationService extends NotificationListenerService
{
    final String host = "https://microsoft-apiapp7ba89e03b5fa4c9b8f7e54324dddecb0.azurewebsites.net/";
    //final String host = "http://localhost:51471/";

    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {
        onNot(sbn);
    }

    private void onNot(StatusBarNotification sbn) {
        Log.i("Msg", "Notify");
        final String pack = sbn.getPackageName();
        final String ticker = sbn.getNotification().tickerText.toString();
        final Bundle extras = sbn.getNotification().extras;
        final String title = extras.getCharSequence("android.title").toString();
        final String text = extras.getCharSequence("android.text").toString();

        WebService.sendNotification(title, text);
    }

    @Override
    public void onNotificationPosted(StatusBarNotification sbn, RankingMap rankingMap)
    {
        onNot(sbn);
    }

    @Override
    public void onNotificationRemoved(StatusBarNotification sbn) {
        Log.i("Msg", "Notification Removed");
    }
}
