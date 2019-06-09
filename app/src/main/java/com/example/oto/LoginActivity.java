package com.example.oto;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.firebase.client.Firebase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.iid.FirebaseInstanceId;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class LoginActivity extends AppCompatActivity {
    CallbackManager callbackManager;
    TextView txtEmail, txtBirthday, txtFriends;
    ProgressDialog mDialog;
    ImageView imgAvatar;
    static final int FACEBOOK_SIGN = 456;
    static final int GOOGLE_SIGN = 123;

    //FirebaseAuth mAuth;
    //FirebaseAuth.AuthStateListener mAuthListener;
    Button btn_login;
    EditText email_login;
    EditText password_login;


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        /*//Google Login
        if (requestCode == GOOGLE_SIGN) {

        }
        //Facebook Login
        else {
            callbackManager.onActivityResult(requestCode, resultCode, data);
        }*/
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        FirebaseApp.initializeApp(this);
        App.mAuth = FirebaseAuth.getInstance();

        App.mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(firebaseAuth.getCurrentUser() != null){
                    openMainPageActivity();
                }
            }
        };

        btn_login = findViewById(R.id.login_button);
        email_login = findViewById(R.id.email_login);
        password_login = findViewById(R.id.password_login);

        callbackManager = CallbackManager.Factory.create();

        txtEmail = (TextView) findViewById(R.id.txtEmail);

        /*txtBirthday = (TextView) findViewById(R.id.txtBirthday);
        txtFriends = (TextView) findViewById(R.id.txtFriends);
        imgAvatar = (ImageView) findViewById(R.id.avatar);*/

        LoginButton loginFacebookButton = (LoginButton) findViewById(R.id.login_facebook);
        loginFacebookButton.setReadPermissions(Arrays.asList("public_profile", "email", "user_birthday", "user_friends"));
        loginFacebookButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                mDialog = new ProgressDialog(LoginActivity.this);
                mDialog.setMessage("Retrieving data...");
                mDialog.show();

                //String accesstoken = loginResult.getAccessToken().getToken();

                GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        mDialog.dismiss();
                        Log.d("response", response.toString());
                        getData(object);
                    }
                });
                //Request Graph API
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,email,birthday,friends");
                request.setParameters(parameters);
                request.executeAsync();
                openMainPageActivity();
            }

            @Override
            public void onCancel() {
            }

            @Override
            public void onError(FacebookException error) {
            }
        });

        //If already login
        if (AccessToken.getCurrentAccessToken() != null) {
            txtEmail.setText(AccessToken.getCurrentAccessToken().getUserId());
        }
        //printKeyHash();

        /*   Start communication with server   */

        Toast.makeText(this, App.url, Toast.LENGTH_LONG).show();

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startSignIn();
            }
        });





        /*   Finish communication with server   */
    }

    private void getData(JSONObject object) {
        try {
            URL profile_picture = new URL("https://graph.facebook.com/" + object.getString("id") + "/picture?width=250&height=250");
            txtEmail.setText(object.getString("email"));
            /*Picasso.with(this).load(profile_picture.toString()).into(imgAvatar);
            txtBirthday.setText(object.getString("birthday"));
            txtFriends.setText("Friends: " + object.getJSONObject("friends").getJSONObject("summary").getString("total_count"));*/
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void printKeyHash() {
        try {
            PackageInfo info = getPackageManager().getPackageInfo("com.example.oto", PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    public void openMainPageActivity() {
        Intent intent = new Intent(this, MainPageActivity.class);
        startActivity(intent);
    }

    public void openRegisterStep1Activity() {
        Intent intent = new Intent(this, RegisterStep1Activity.class);
        startActivity(intent);
    }

    public  void loginAlertDialog() {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Suggestion");
        alert.setMessage("You'r Not Signed In.\n" + "Do You Want To Register");
        alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                openRegisterStep1Activity();
            }
        });
        alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        alert.show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        App.mAuth.addAuthStateListener(App.mAuthListener);
    }

    public void startSignIn() {
        String email = email_login.getText().toString();
        final String password = password_login.getText().toString();
        if(TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
            Toast.makeText(LoginActivity.this, "Fields Are Empty", Toast.LENGTH_LONG).show();
        } else {
            App.mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(!task.isSuccessful()) {
                        Toast.makeText(LoginActivity.this, "Auth Problem, Sign In Problem", Toast.LENGTH_LONG).show();
                    } else {
                        FirebaseUser user = task.getResult().getUser();
                        App.setEmail(user.getEmail());
                        App.setPassword(password);
                        App.setUID(user.getUid());
                        App.setToken(FirebaseInstanceId.getInstance().getToken());
                        Toast.makeText(LoginActivity.this, App.getToken(), Toast.LENGTH_LONG).show();
                        openMainPageActivity();
                    }
                }
            });
        }
    }
}
