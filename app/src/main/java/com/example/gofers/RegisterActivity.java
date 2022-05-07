package com.example.gofers;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    FirebaseFirestore db;

    EditText firstName , lastName , adharNo ,drivingLicenceNo , vehicleNo ,
             registerPhoneNo , registerEmail , registerPassword, registerConfPassword ;

    Button registerSubmitBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        firstName = findViewById(R.id.firstName);
        lastName = findViewById(R.id.lastName);
        adharNo = findViewById(R.id.adharNo);
        drivingLicenceNo = findViewById(R.id.drivingLicenceNo);
        vehicleNo = findViewById(R.id.vehicleNo);
        registerPhoneNo = findViewById(R.id.registerPhoneNo);
        registerEmail = findViewById(R.id.registerEmail);
        registerPassword = findViewById(R.id.registerPassword);
        registerConfPassword = findViewById(R.id.registerConfPassword);

        registerSubmitBtn = findViewById(R.id.registerSubmitBtn);

        db = FirebaseFirestore.getInstance();

        registerSubmitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (TextUtils.isEmpty(firstName.getText().toString().trim())){
                    Toast.makeText(RegisterActivity.this, "First Name Required", Toast.LENGTH_SHORT).show();
                }else if (TextUtils.isEmpty(lastName.getText().toString().trim())){
                    Toast.makeText(RegisterActivity.this, "Last name is Required", Toast.LENGTH_SHORT).show();
                }else if (TextUtils.isEmpty(adharNo.getText().toString().trim())){
                     Toast.makeText(RegisterActivity.this, "Adhar No. is Required", Toast.LENGTH_SHORT).show();
                }else if (TextUtils.isEmpty(drivingLicenceNo.getText().toString().trim())){
                     Toast.makeText(RegisterActivity.this, "Driving Licence Id is Required", Toast.LENGTH_SHORT).show();
                }else if (TextUtils.isEmpty(vehicleNo.getText().toString().trim())){
                     Toast.makeText(RegisterActivity.this, "Vehicle No. is Required", Toast.LENGTH_SHORT).show();
                }else if (TextUtils.isEmpty(registerPhoneNo.getText().toString().trim())){
                    Toast.makeText(RegisterActivity.this, "Phone No. is Required", Toast.LENGTH_SHORT).show();
                }else if (TextUtils.isEmpty(registerEmail.getText().toString().trim())){
                     Toast.makeText(RegisterActivity.this, "Email is Required", Toast.LENGTH_SHORT).show();
                }else if (TextUtils.isEmpty(registerPassword.getText().toString().trim())){
                     Toast.makeText(RegisterActivity.this, "password is Required", Toast.LENGTH_SHORT).show();
                }else if (TextUtils.isEmpty(registerConfPassword.getText().toString().trim())){
                    Toast.makeText(RegisterActivity.this, "Please confirm Your Password", Toast.LENGTH_SHORT).show();
                }
                else {
                    Map<String,Object>map = new HashMap<>();
                    map.put("FirstName",firstName.getText().toString().trim());
                    map.put("LastName",lastName.getText().toString().trim());
                    map.put("AdharNo.",adharNo.getText().toString().trim());
                    map.put("DrivingLicence",drivingLicenceNo.getText().toString().trim());
                    map.put("VehicleNo",vehicleNo.getText().toString().trim());
                    map.put("PhoneNo.",firstName.getText().toString().trim());
                    map.put("Email",firstName.getText().toString().trim());


                    db.collection("New Driver").document(registerEmail.getText().toString())
                            .set(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(RegisterActivity.this, "Document Submited", Toast.LENGTH_LONG).show();
                            }else {
                                Toast.makeText(RegisterActivity.this, "Something error", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }
}