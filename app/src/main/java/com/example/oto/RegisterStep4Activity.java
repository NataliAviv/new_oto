package com.example.oto;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

public class RegisterStep4Activity extends AppCompatActivity {
    Button btn_to_main_page;
    CheckBox checkBox_terms;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_step4);
        checkBox_terms = findViewById(R.id.reg4_check_box_terms);
        btn_to_main_page = findViewById(R.id.reg4_btn_to_main_page);
        btn_to_main_page.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMainPageActivity();
            }
        });
    }

    public void openMainPageActivity(){
        if(checkBox_terms.isChecked()) {
            Toast.makeText(RegisterStep4Activity.this, "Checked!", Toast.LENGTH_LONG).show();
            Intent intent=new Intent(this, MainPageActivity.class);
            startActivity(intent);
        } else {
            Toast.makeText(RegisterStep4Activity.this, "Not Checked!", Toast.LENGTH_LONG).show();
            return;
        }
    }

}
