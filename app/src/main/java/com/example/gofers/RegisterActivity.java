package com.example.gofers;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import de.hdodenhof.circleimageview.CircleImageView;

public class RegisterActivity extends AppCompatActivity {

    FirebaseFirestore db;
    EditText et_phone;
    FirebaseUser user;

    //ImageView adharImage , panImage;





    ProgressDialog dialog;

    Button registerSubmitBtn, BtnSelectImage;

    private Uri filePath;
    private String imageid;


    // ProgressBar progressBar1;

    private FirebaseAuth mAuth;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;


    // request code
    private final int PICK_IMAGE_REQUEST = 22;

    FirebaseStorage storage;
    StorageReference storageReference;
    Button btn_upload;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);



        //BtnSelectImage = findViewById(R.id.btn_select_img);

        //adharImage = findViewById(R.id.adharImage);
        //panImage = findViewById(R.id.panImage);

        et_phone = (EditText) findViewById(R.id.et_phone);
        mAuth = FirebaseAuth.getInstance();
        if (mAuth.getCurrentUser()!=null){
            startActivity(new Intent(RegisterActivity.this,DetailsActivity.class));
        }


        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();



        //btn_upload = findViewById(R.id.btnUpload);



        registerSubmitBtn = findViewById(R.id.registerSubmitBtn);

        registerSubmitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (et_phone.getText().toString().trim().length()==10){
                    dialog = ProgressDialog.show(RegisterActivity.this, "Loading", "Please Wait", true);
                    otpSend();
                }else {
                    Toast.makeText(RegisterActivity.this, "Enter Valid Phone No.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        db = FirebaseFirestore.getInstance();













    }



    private void otpSend() {


        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            private PhoneAuthCredential credential;

            @Override
            public void onVerificationCompleted(PhoneAuthCredential credential) {

            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                //btnContinue.setVisibility(View.VISIBLE);
                //progressBar1.setVisibility(View.GONE);
                Toast.makeText(RegisterActivity.this, e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                Log.d("kkkkkk",e.getLocalizedMessage());
                dialog.dismiss();

            }

            @Override
            public void onCodeSent(@NonNull String verificationId,
                                   @NonNull PhoneAuthProvider.ForceResendingToken token) {
                dialog.dismiss();

                Intent intent = new Intent(RegisterActivity.this,VerificationActivity.class);
                intent.putExtra("phone",et_phone.getText().toString().trim());
                intent.putExtra("verificationId",verificationId);
                startActivity(intent);

            }


        };

        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber("+91"+et_phone.getText().toString().trim())       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)                 // Activity (for callback binding)
                        .setCallbacks(mCallbacks)          // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }


}