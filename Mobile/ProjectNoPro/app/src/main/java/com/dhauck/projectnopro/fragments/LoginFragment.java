package com.dhauck.projectnopro.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.CookieManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.dhauck.projectnopro.MainActivity;
import com.dhauck.projectnopro.Models.ExternalLoginResponse;
import com.dhauck.projectnopro.Models.UserCreationDto;
import com.dhauck.projectnopro.R;
import com.dhauck.projectnopro.classes.LocalStorage;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

/**
 * Created by david on 6/15/2016.
 */
public class LoginFragment extends Fragment {
    WebView webView;
    final String host = "https://microsoft-apiapp7ba89e03b5fa4c9b8f7e54324dddecb0.azurewebsites.net/";
    //final String host = "http://localhost:51471/";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.login_view, container, false);
        webView = (WebView)view.findViewById(R.id.webview);
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Boolean b = url.contains("access_token");
                if (!url.contains("access_token")) {
                    return false;
                }
                int start = url.indexOf("=", url.indexOf("access_token")) + 1;
                int end = url.indexOf("&", start);
                String token = url.substring(start, end);
                tokenObtained(token);
                return true; //Indicates WebView to NOT load the url;
            }
        });
        getLoginMethods();

        return view;
    }

    private void tokenObtained(String token) {
        LocalStorage.setAccessToken(token);
        String x = LocalStorage.getAccessToken();
        CookieManager cookieManager = CookieManager.getInstance();
        String cookies = cookieManager.getCookie(host);
        if (cookies.contains(".AspNet.Cookies")) {
            Runnable r = new Runnable() {
                public void run() {
                    ((MainActivity)getActivity()).switchToMainFragment();
                }
            };
            Thread t = new Thread(r);
            t.start();
        }
        else {
            registerNewAccount(token);
        }
    }

    private void registerNewAccount(final String token) {
        Runnable r = new Runnable()
        {
            public void run()
            {
                try {
                    CookieManager cookieManager = CookieManager.getInstance();
                    String cookies = cookieManager.getCookie(host);
                    URL url = new URL(host + "api/Account/RegisterExternal");
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setDoOutput(true);
                    conn.setRequestMethod("POST");
                    conn.setRequestProperty("Content-Type", "application/json");
                    conn.setRequestProperty("Authorization", "Bearer " + token);
                    conn.setRequestProperty("Cookie", cookies);
                    Gson gson = new Gson();
                    UserCreationDto dto = new UserCreationDto();
                    dto.Name = "n/a";
                    String input = gson.toJson(dto);
                    OutputStream os = conn.getOutputStream();
                    os.write(input.getBytes());
                    os.flush();

                    if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
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

    private void getLoginMethods() {
        Runnable r = new Runnable() {
            public void run() {
                try {
                    URL url = new URL(host + "api/Account/ExternalLogins?returnUrl=/&generateState=true");
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                    BufferedReader br = new BufferedReader(new InputStreamReader(
                            (conn.getInputStream())));

                    String output;
                    System.out.println("Output from Server .... \n");
                    String result = "";
                    while ((output = br.readLine()) != null) {
                        result += output;
                    }
                     Gson gson = new Gson();
                    Type listType = new TypeToken<List<ExternalLoginResponse>>(){}.getType();
                    List<ExternalLoginResponse> methods = gson.fromJson(result, listType);

                    setLoginOptions(methods);

                    conn.disconnect();
                } catch (Exception e) {
                    System.out.print(e.toString());
                }
            }
        };
        Thread t = new Thread(r);
        t.start();
    }

    private void setLoginOptions(List<ExternalLoginResponse> methods) {
        ExternalLoginResponse chosenMethod = null;
        for (ExternalLoginResponse method : methods) {
            if (method.Name.equals("Google")) {
                chosenMethod = method;
            }
        }
        if (chosenMethod == null)
            return;
        final String url = chosenMethod.Url;
        Runnable r = new Runnable() {
            public void run() {
                webView.loadUrl(host + url);
            }
        };

        this.getActivity().runOnUiThread(r);
    }
}

