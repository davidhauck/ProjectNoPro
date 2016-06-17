package com.dhauck.projectnopro.classes;

import android.os.Bundle;
import android.util.Log;
import android.webkit.CookieManager;

import com.dhauck.projectnopro.Models.Message;
import com.dhauck.projectnopro.Models.TextMessage;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by david on 6/17/2016.
 */
public class WebService {
    final static String host = "https://microsoft-apiapp7ba89e03b5fa4c9b8f7e54324dddecb0.azurewebsites.net/";
    //final static String host = "http://localhost:51471/";

    public static void sendTextMessage(final int senderId, final String senderName, final String text) {
        Runnable r = new Runnable()
        {
            public void run()
            {
                try {
                    String token = LocalStorage.getAccessToken();
                    CookieManager cookieManager = CookieManager.getInstance();
                    String cookies = cookieManager.getCookie(host);
                    URL url = new URL(host + "/api/Text");
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setDoOutput(true);
                    conn.setRequestMethod("POST");
                    conn.setRequestProperty("Content-Type", "application/json");
                    conn.setRequestProperty("Authorization", "Bearer " + token);
                    conn.setRequestProperty("Cookie", cookies);
                    Gson gson = new Gson();
                    TextMessage m = new TextMessage();
                    m.senderId = senderId;
                    m.senderName = senderName;
                    m.text = text;
                    String input = gson.toJson(m);
                    OutputStream os = conn.getOutputStream();
                    os.write(input.getBytes());
                    os.flush();

                    if (conn.getResponseCode() != HttpURLConnection.HTTP_NO_CONTENT) {
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

    public static void sendNotification(final String title, final String text) {

        Runnable r = new Runnable()
        {
            public void run()
            {
                try {
                    String token = LocalStorage.getAccessToken();
                    CookieManager cookieManager = CookieManager.getInstance();
                    String cookies = cookieManager.getCookie(host);
                    URL url = new URL(host + "/api/Notification");
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setDoOutput(true);
                    conn.setRequestMethod("POST");
                    conn.setRequestProperty("Content-Type", "application/json");
                    conn.setRequestProperty("Authorization", "Bearer " + token);
                    conn.setRequestProperty("Cookie", cookies);
                    Gson gson = new Gson();
                    Message m = new Message();
                    m.text = text;
                    m.title = title;
                    String input = gson.toJson(m);
                    OutputStream os = conn.getOutputStream();
                    os.write(input.getBytes());
                    os.flush();

                    if (conn.getResponseCode() != HttpURLConnection.HTTP_NO_CONTENT) {
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
}
