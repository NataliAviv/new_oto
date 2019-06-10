package com.example.oto;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class ProfileFragment extends Fragment {

    EditText profileName;
    TextView profilePhone;
    TextView profileEmail;
    TextView profileAddress;

    ImageView edit;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile,container,false);

        profileName = view.findViewById(R.id.profile_name);
        profilePhone = view.findViewById(R.id.profile_phone);
        profileEmail = view.findViewById(R.id.profile_email);
        profileAddress = view.findViewById(R.id.profile_address);

        edit = view.findViewById(R.id.pen);

        /*profileName.setText(App.getFirstName()+" "+App.getLastName());
        profilePhone.setText(App.getPhone());
        profileEmail.setText(App.getEmail());
        profileAddress.setText(App.getAddress());*/

        profileName.setText("Zivnd vdskvlm");
        profilePhone.setText(App.getPhone());
        profileEmail.setText(App.getEmail());
        profileAddress.setText(App.getAddress());

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                profileName.setEnabled(true);
            }
        });

        return view;
    }
}
