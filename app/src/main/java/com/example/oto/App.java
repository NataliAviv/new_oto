package com.example.oto;

import android.app.Application;
import android.content.Context;

public class App extends Application {

    //TODO: Create User class, Ride class, Car class, Review class.
    //TODO: Update the ip according to the wifi network. Later we will upload the server to the web and get URL.
    static final public String url = "http://192.168.1.128:8080/";
    static public String token;
    static public String first_name;
    static public String last_name;
    static public String email;
    static public String password;
    static public String address;
    static public String city;
    static public String country;
    static public String phone;

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

    public static void setFirstName(String nFirstName) {
        first_name = nFirstName;
    }

    public static String getFirstName() {
        return first_name;
    }

    public static void setLastName(String nLastName) {
        last_name = nLastName;
    }

    public static String getLastName() {
        return last_name;
    }

    public static void setEmail(String mEmail) {
        email = mEmail;
    }

    public static String getEmail() {
        return email;
    }

    public static void setPassword(String mPassword) {
        password = mPassword;
    }

    public static String getPassword() {
        return password;
    }

    public static void setAddress(String nAddress){
        address=nAddress;
    }

    public static String getAddress(){
        return address;
    }

    public static void setCity(String nCity){
        city=nCity;
    }

    public static String getCity(){
        return city;
    }

    public static void setCountry(String nCountry){
        country=nCountry;
    }

    public static String getCountry(){
        return country;
    }

    public static void setPhone(String nPhone){
        phone=nPhone;
    }

    public static String getPhone(){
        return phone;
    }

}
