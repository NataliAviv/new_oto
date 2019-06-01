package com.example.oto;

import android.app.Application;
import android.content.Context;

public class App extends Application {

    //TODO: Create User class, Ride class, Car class, Review class.
    //TODO: Update the ip according to the wifi network. Later we will upload the server to the web and get URL.
    static final public String url = "http://192.168.1.19:8080/";
    static public String token;

    private static Application sApplication;

    public static Application getApplication() {
        return sApplication;
    }

    public static Context getContext() {
        return getApplication().getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sApplication = this;
    }

    public static void setToken(String nToken) {
        token = nToken;
    }

    public static String getToken() {
        return token;
    }

}
