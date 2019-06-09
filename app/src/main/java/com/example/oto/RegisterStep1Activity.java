package com.example.oto;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.regex.Pattern;

import static com.example.oto.App.mAuth;

public class RegisterStep1Activity extends AppCompatActivity {

    Button button_to_register_2;
    EditText emailInput;
    EditText passwordInput;
    EditText confirmPasswordInput;
    EditText firstNameInput;
    EditText lastNameInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_step1);

        emailInput = findViewById(R.id.reg1_email);
        passwordInput = findViewById(R.id.reg1_password);
        confirmPasswordInput = findViewById(R.id.reg1_confirm_password);
        firstNameInput = findViewById(R.id.reg1_first_name);
        lastNameInput = findViewById(R.id.reg1_last_name);

        button_to_register_2 = (Button) findViewById(R.id.buttonToRegisterStep2);
        button_to_register_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                App.setFirstName(firstNameInput.getText().toString());
                App.setLastName(lastNameInput.getText().toString());
                App.setEmail(emailInput.getText().toString());
                App.setPassword(passwordInput.getText().toString());
                openRegisterStep2Activity(v);
            }
        });


    }

    public static String regex = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";

    public static final Pattern EMAIL_ADDRESS =
            Pattern.compile(regex);

    public boolean validateEmail() {
        String emailInput = this.emailInput.getText().toString().trim();

        if (emailInput.isEmpty()) {
            this.emailInput.setError("Field can't be empty");
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(emailInput).matches()) {
            this.emailInput.setError("Please enter a valid email address");
            return false;
        } else {
            this.emailInput.setError(null);
            return true;
        }
    }

    public static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^" +
                    //"(?=.*[0-9])" +         //at least 1 digit
                    "(?=.*[a-z])" +         //at least 1 lower case letter
                    "(?=.*[A-Z])" +         //at least 1 upper case letter*/
                    "(?=.*[a-zA-Z0-9])" +      //any letter
                    //"(?=.*[@#$%^&+=])" +    //at least 1 special character*/
                    "(?=\\S+$)" +           //no white spaces
                    ".{6,}" +               //at least 4 characters
                    "$"
            );

    public boolean validatePassword() {
        String passwordInput = this.passwordInput.getText().toString().trim();

        if (passwordInput.isEmpty()) {
            this.passwordInput.setError("Field can't be empty");
            return false;
        }/* else if (!PASSWORD_PATTERN.matcher(passwordInput).matches()) {
            this.passwordInput.setError("Password too weak");
            return false;
        }*/ else {
            this.passwordInput.setError(null);
            return true;
        }
    }

    private boolean validateFirstName() {
        String usernameInput = firstNameInput.getText().toString().trim();

        if (usernameInput.isEmpty()) {
            firstNameInput.setError("Field can't be empty");
            return false;
        } else {
            firstNameInput.setError(null);
            return true;
        }
    }

    private boolean validateLastName() {
        String usernameInput = lastNameInput.getText().toString().trim();

        if (usernameInput.isEmpty()) {
            lastNameInput.setError("Field can't be empty");
            return false;
        } else {
            lastNameInput.setError(null);
            return true;
        }
    }

    private boolean validateConfirmPassword() {
        String confirmPasswordInput = this.confirmPasswordInput.getText().toString().trim();
        String passwordInput = this.passwordInput.getText().toString().trim();

        if (confirmPasswordInput.isEmpty()) {
            this.confirmPasswordInput.setError("Field can't be empty");
            return false;
        } else if (confirmPasswordInput.compareTo(passwordInput) != 0) {
            this.confirmPasswordInput.setError("Password isn't confirm");
            return false;
        } else {
            this.confirmPasswordInput.setError(null);
            return true;
        }
    }

    public void openRegisterStep2Activity(View v) {
        if (validateFirstName() && validateLastName() && validateEmail() && validatePassword() && validateConfirmPassword()) {
            String email = App.getEmail();
            String password = App.getPassword();
            //Toast.makeText(RegisterStep1Activity.this,email,Toast.LENGTH_LONG).show();
            App.mAuth = FirebaseAuth.getInstance();
            App.mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                FirebaseUser user = App.mAuth.getCurrentUser();
                                App.setUID(user.getUid());
                                openNextRegStep();
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w("RegisterStep1", "createUserWithEmail:failure", task.getException());
                                Toast.makeText(RegisterStep1Activity.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                            }

                            // ...
                        }
                    });


        }
        return;
    }

    public void openNextRegStep() {
        Intent intent = new Intent(this, RegisterStep2Activity.class);
        startActivity(intent);
    }
}
