package com.example.oto;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;

import java.util.regex.Pattern;

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
        String passwordInput = this.passwordInput.getText().toString().trim();

        if (passwordInput.isEmpty()) {
            this.passwordInput.setError("Field can't be empty");
            return false;
        } else if (!PASSWORD_PATTERN.matcher(passwordInput).matches()) {
            this.passwordInput.setError("Password too weak");
            return false;
        } else {
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

        if (confirmPasswordInput.isEmpty()){
            this.confirmPasswordInput.setError("Field can't be empty");
            return false;
        } else if(confirmPasswordInput.compareTo(passwordInput) == 1){
            this.confirmPasswordInput.setError("Password isn't confirm");
            return false;
        }else{
            this.confirmPasswordInput.setError(null);
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
