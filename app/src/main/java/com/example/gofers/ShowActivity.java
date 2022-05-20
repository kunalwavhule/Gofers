package com.example.gofers;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

public class ShowActivity extends AppCompatActivity {

    EditText FirstName, LastName, Aadhar,Licence,VehicleNo,PhoneNo,Email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);

        FirstName = findViewById(R.id.firstName);
        LastName = findViewById(R.id.lastName);
        Aadhar = findViewById(R.id.adharNo);
        Licence = findViewById(R.id.drivingLicenceNo);
        VehicleNo = findViewById(R.id.vehicleNo);
        PhoneNo = findViewById(R.id.registerPhoneNo);
        Email = findViewById(R.id.registerEmail);

        Intent in = getIntent();
        Bundle bundle = in.getExtras();
        bundle.putString("firstName",FirstName.toString());

    }
}