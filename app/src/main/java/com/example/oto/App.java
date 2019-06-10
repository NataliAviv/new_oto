package com.example.oto;

import android.app.Application;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.toolbox.StringRequest;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;

import com.firebase.client.Firebase;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GetTokenResult;


public class App extends Application {

    //TODO: Create User class, Ride class, Car class, Review class.
    //TODO: Update the ip according to the wifi network. Later we will upload the server to the web and get URL.
    static final public String url = "http://192.168.1.19:8080/";

    static public FirebaseAuth mAuth;
    static public FirebaseAuth.AuthStateListener mAuthListener;

    static public String token;
    static public String first_name;
    static public String last_name;
    static public String gender = "Male";
    static public String birthday = "1993-06-19T21:00:00.000Z";
    static public String email;
    static public String password;
    static public String address;
    static public String phone;
    static public String city;
    static public String country;
    static public String carModel;
    static public String model;
    static public String carColor;
    static public String license;
    static public String UID;


    static public String date;
    static public String time;
    static public String source;
    static public String dest;
    static public String freePlaces;
    static public String driver;
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

    public static void setGender(String nGender){
        gender = nGender;
    }

    public static String getGender(){
        return gender;
    }

    public static void setBirthday(String nBirthday){
        birthday = nBirthday;
    }

    public static String getBirthday(){
        return birthday;
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

    public static void setPhone(String nPhone) {
        phone = nPhone;
    }

    public static String getPhone(){
        return phone;
    }

    public static void setCity(String nCity){
        city = nCity;
    }

    public static String getCity(){
        return city;
    }

    public static void setCountry(String nCounty){
        country = nCounty;
    }

    public static String getCountry(){
        return country;
    }

    public static void setCarModel(String nCarModel){
        carModel = nCarModel;
    }

    public static String getCarModel(){
        return carModel;
    }

    public static void setModel(String nModel){
        model = nModel;
    }

    public static String getModel(){
        return model;
    }

    public static void setCarColor(String nCarColor){
        carColor = nCarColor;
    }

    public static String getCarColor(){
        return carColor;
    }

    public static void setLicense(String nLicense){
        license = nLicense;
    }

    public static String getLicense(){
        return license;
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

    public static void conectTofireBase(String email, String password){

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete( Task<AuthResult> task) {
                if(!task.isSuccessful()) {

                } else {
                    FirebaseUser user = task.getResult().getUser();
                    setEmail(user.getEmail());
                    setPassword(App.password);
                    setUID(user.getUid());

                    FirebaseUser mUser = FirebaseAuth.getInstance().getCurrentUser();
                    mUser.getIdToken(true)
                            .addOnCompleteListener(new OnCompleteListener<GetTokenResult>() {
                                public void onComplete(@NonNull Task<GetTokenResult> task) {
                                    if (task.isSuccessful()) {
                                        String idToken = task.getResult().getToken();
                                        App.setToken(idToken);
                                        // Send token to your backend via HTTPS
                                        // ...
                                    } else {
                                        // Handle error -> task.getException();
                                    }
                                }
                            });
                    //Toast.makeText(LoginActivity.this, App.getToken(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}


