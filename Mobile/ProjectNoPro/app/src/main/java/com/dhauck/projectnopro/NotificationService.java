package com.dhauck.projectnopro;

import android.os.Bundle;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.util.Log;

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
    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {
        Log.i("Msg", "notification receieved");

        final String pack = sbn.getPackageName();
        final String ticker = sbn.getNotification().tickerText.toString();
        final Bundle extras = sbn.getNotification().extras;
        final String title = extras.getString("android.title");
        final String text = extras.getCharSequence("android.text").toString();

        Runnable r = new Runnable()
        {
            public void run()
            {
                try {
                    URL url = new URL("https://microsoft-apiapp7ba89e03b5fa4c9b8f7e54324dddecb0.azurewebsites.net/api/values");
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setDoOutput(true);
                    conn.setRequestMethod("POST");
                    conn.setRequestProperty("Content-Type", "application/json");
                    Gson gson = new Gson();
                    Message m = new Message();
                    m.value = text;
                    String input = gson.toJson(m);
                    OutputStream os = conn.getOutputStream();
                    os.write(input.getBytes());
                    os.flush();

                    if (conn.getResponseCode() != HttpURLConnection.HTTP_CREATED) {
                        throw new RuntimeException("Failed : HTTP error code : "
                                + conn.getResponseCode());
                    }

                    BufferedReader br = new BufferedReader(new InputStreamReader(
                            (conn.getInputStream())));

                    String output;
                    System.out.println("Output from Server .... \n");
                    while ((output = br.readLine()) != null) {
                        System.out.println(output);
                    }

                    conn.disconnect();
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        Thread t = new Thread(r);
        t.start();
    }

    @Override
    public void onNotificationPosted(StatusBarNotification sbn, RankingMap rankingMap)
    {
        Log.i("Msg", "Notify");
        final String pack = sbn.getPackageName();
        final String ticker = sbn.getNotification().tickerText.toString();
        final Bundle extras = sbn.getNotification().extras;
        final String title = extras.getString("android.title");
        final String text = extras.getCharSequence("android.text").toString();

        Runnable r = new Runnable()
        {
            public void run()
            {
                try {
                    URL url = new URL("https://microsoft-apiapp7ba89e03b5fa4c9b8f7e54324dddecb0.azurewebsites.net/api/values");
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setDoOutput(true);
                    conn.setRequestMethod("POST");
                    conn.setRequestProperty("Content-Type", "application/json");
                    Gson gson = new Gson();
                    Message m = new Message();
                    m.value = text;
                    String input = gson.toJson(m);
                    OutputStream os = conn.getOutputStream();
                    os.write(input.getBytes());
                    os.flush();

                    if (conn.getResponseCode() != HttpURLConnection.HTTP_CREATED) {
                        throw new RuntimeException("Failed : HTTP error code : "
                                + conn.getResponseCode());
                    }

                    BufferedReader br = new BufferedReader(new InputStreamReader(
                            (conn.getInputStream())));

                    String output;
                    System.out.println("Output from Server .... \n");
                    while ((output = br.readLine()) != null) {
                        System.out.println(output);
                    }

                    conn.disconnect();
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        Thread t = new Thread(r);
        t.start();
    }

    @Override
    public void onNotificationRemoved(StatusBarNotification sbn) {
        Log.i("Msg", "Notification Removed");
    }
}
