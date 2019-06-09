package com.example.oto;

import android.app.Application;
import android.content.Context;

import com.firebase.client.Firebase;
import com.google.firebase.auth.FirebaseAuth;


public class App extends Application {

    //TODO: Create User class, Ride class, Car class, Review class.
    //TODO: Update the ip according to the wifi network. Later we will upload the server to the web and get URL.
    static final public String url = "http://192.168.1.19:8080/";

    static public FirebaseAuth mAuth;
    static public FirebaseAuth.AuthStateListener mAuthListener;

    static public String token;
    static public String first_name;
    static public String last_name;
    static public String email;
    static public String password;
    static public String address;
    static public String phone;
    static public String UID;

    static public String date;
    static public String time;
    static public String  source;
    static public String dest;
    static public String freePlaces;
    static public String  driver;
    static public String id_;


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
        Firebase.setAndroidContext(this);
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

    public static void setAddress(String nAddress) {
        address = nAddress;
    }

    public static String getAddress() {
        return address;
    }

    public static void setPhone(String nPhone) {
        phone = nPhone;
    }

    public static String getPhone() {
        return phone;
    }

    public static void setUID(String nUID) {
        UID = nUID;
    }

    public static String getUID() {
        return UID;
    }

    public static void setDate(String adate){ date=adate; }

    public static String getDate(){
        return date;
    }
    public static void setTime(String atime){ time=atime; }

    public static String getTime(){
        return time;
    }

    public static void setDest(String adest){ dest=adest; }

    public static String getDest(){
        return dest;
    }

    public static void setSource(String asource){ source=asource; }
    public static String getSource(){
        return source;
    }

    public static String getFreePlaces(){
        return freePlaces;
    }
    public static void setFreePlaces(String afree){ freePlaces=afree; }
}
