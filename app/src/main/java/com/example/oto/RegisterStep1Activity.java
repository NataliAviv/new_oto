package com.example.oto;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.regex.Pattern;

public class RegisterStep1Activity extends AppCompatActivity {

    Button button_to_register_2;
    EditText textEmailInput;
    EditText textPasswordInput;
    EditText textConfirmPasswordInput;
    EditText textFirstName;
    EditText textLastName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_step1);


        button_to_register_2 = (Button) findViewById(R.id.buttonToRegisterStep2);
        button_to_register_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openRegisterStep2Activity(v);
            }
        });

        textEmailInput = findViewById(R.id.emailEditText);
        textPasswordInput = findViewById(R.id.passwordEditText);
        textConfirmPasswordInput = findViewById(R.id.confimPasswordEditText);
        textFirstName = findViewById(R.id.firstNameEditText);
        textLastName = findViewById(R.id.lastNameEditText);

    }

    public static String regex = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";

    public static final Pattern EMAIL_ADDRESS =
            Pattern.compile(regex);

    public boolean validateEmail() {
        String emailInput = textEmailInput.getText().toString().trim();

        if (emailInput.isEmpty()) {
            textEmailInput.setError("Field can't be empty");
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(emailInput).matches()) {
            textEmailInput.setError("Please enter a valid email address");
            return false;
        } else {
            textEmailInput.setError(null);
            return true;
        }
    }

    public static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^" +
                    "(?=.*[0-9])" +         //at least 1 digit
                    "(?=.*[a-z])" +         //at least 1 lower case letter
                    "(?=.*[A-Z])" +         //at least 1 upper case letter
                    "(?=.*[a-zA-Z])" +      //any letter
                    "(?=.*[@#$%^&+=])" +    //at least 1 special character
                    "(?=\\S+$)" +           //no white spaces
                    ".{4,}" +               //at least 4 characters
                    "$"
            );

    public boolean validatePassword() {
        String passwordInput = textPasswordInput.getText().toString().trim();

        if (passwordInput.isEmpty()) {
            textPasswordInput.setError("Field can't be empty");
            return false;
        } else if (!PASSWORD_PATTERN.matcher(passwordInput).matches()) {
            textPasswordInput.setError("Password too weak");
            return false;
        } else {
            textPasswordInput.setError(null);
            return true;
        }
    }

    private boolean validateFirstName() {
        String usernameInput = textFirstName.getText().toString().trim();

        if (usernameInput.isEmpty()) {
            textFirstName.setError("Field can't be empty");
            return false;
        } else {
            textFirstName.setError(null);
            return true;
        }
    }

    private boolean validateLastName() {
        String usernameInput = textLastName.getText().toString().trim();

        if (usernameInput.isEmpty()) {
            textLastName.setError("Field can't be empty");
            return false;
        } else {
            textLastName.setError(null);
            return true;
        }
    }

    private boolean validateConfirmPassword() {
        String confirmPasswordInput = textConfirmPasswordInput.getText().toString().trim();
        String passwordInput = textPasswordInput.getText().toString().trim();

        if (confirmPasswordInput.isEmpty()){
            textConfirmPasswordInput.setError("Field can't be empty");
            return false;
        } else if(confirmPasswordInput.compareTo(passwordInput) == 1){
            textConfirmPasswordInput.setError("Password isn't confirm");
            return false;
        }else{
            textConfirmPasswordInput.setError(null);
            return true;
        }
    }

    public void openRegisterStep2Activity(View v) {
        if(!validateFirstName() || !validateLastName() || !validateEmail() || !validatePassword() || !validateConfirmPassword()) {
            return;
        }
        Intent intent = new Intent(this, RegisterStep2Activity.class);
        startActivity(intent);
    }
}
