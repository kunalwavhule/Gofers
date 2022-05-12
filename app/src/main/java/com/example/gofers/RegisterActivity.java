package com.example.gofers;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class RegisterActivity extends AppCompatActivity {

    FirebaseFirestore db;

    ImageView profilePic;

    EditText firstName , lastName , adharNo ,drivingLicenceNo , vehicleNo ,
             registerPhoneNo , registerEmail , registerPassword, registerConfPassword ;

    Button registerSubmitBtn;

    private Uri filePath;


    // request code
    private final int PICK_IMAGE_REQUEST = 22;

    FirebaseStorage storage;
    StorageReference storageReference;
    Button btn_upload;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        profilePic = findViewById(R.id.profilePic);
        profilePic.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.ic_baseline_account_circle_24));

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        btn_upload = findViewById(R.id.btn_upload);
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

        profilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage();
            }
        });

        btn_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadImage();
            }
        });

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

    private void uploadImage() {

        if (filePath != null) {

            // Code for showing progressDialog while uploading
            /*ProgressDialog progressDialog
                    = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

             */

            // Defining the child of storageReference
            StorageReference ref
                    = storageReference
                    .child(
                            "images/"
                                    + UUID.randomUUID().toString());

            // adding listeners on upload
            // or failure of image
            ref.putFile(filePath)
                    .addOnSuccessListener(
                            new OnSuccessListener<UploadTask.TaskSnapshot>() {

                                @Override
                                public void onSuccess(
                                        UploadTask.TaskSnapshot taskSnapshot)
                                {

                                    // Image uploaded successfully
                                    // Dismiss dialog
                                    //progressDialog.dismiss();
                                    Toast
                                            .makeText(RegisterActivity.this,
                                                    "Image Uploaded!!",
                                                    Toast.LENGTH_SHORT)
                                            .show();
                                }
                            })

                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e)
                        {

                            // Error, Image not uploaded
                            //progressDialog.dismiss();
                            Toast
                                    .makeText(RegisterActivity.this,
                                            "Failed " + e.getMessage(),
                                            Toast.LENGTH_SHORT)
                                    .show();
                        }
                    })
                    .addOnProgressListener(
                            new OnProgressListener<UploadTask.TaskSnapshot>() {

                                // Progress Listener for loading
                                // percentage on the dialog box
                                @Override
                                public void onProgress(
                                        UploadTask.TaskSnapshot taskSnapshot)
                                {
                                    double progress
                                            = (100.0
                                            * taskSnapshot.getBytesTransferred()
                                            / taskSnapshot.getTotalByteCount());
                                    //progressDialog.setMessage(
                                      //      "Uploaded "
                                        //            + (int)progress + "%");
                                }
                            });
        }
    }

    private void selectImage() {

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(
                Intent.createChooser(
                        intent,
                        "Select Image from here..."),
                PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode,
                                    int resultCode,
                                    Intent data) {

        super.onActivityResult(requestCode,
                resultCode,
                data);

        // checking request code and result code
        // if request code is PICK_IMAGE_REQUEST and
        // resultCode is RESULT_OK
        // then set image in the image view
        if (requestCode == PICK_IMAGE_REQUEST
                && resultCode == RESULT_OK
                && data != null
                && data.getData() != null) {

            // Get the Uri of data
            filePath = data.getData();
            try {

                // Setting image on image view using Bitmap
                Bitmap bitmap = MediaStore
                        .Images
                        .Media
                        .getBitmap(
                                getContentResolver(),
                                filePath);
                profilePic.setImageBitmap(bitmap);
            } catch (IOException e) {
                // Log the exception
                e.printStackTrace();
            }
        }
    }
}