package com.example.gofers;

import static android.app.Activity.RESULT_OK;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class RCFragment extends Fragment {

    ImageView rcImage;

    private final int PICK_IMAGE_REQUEST = 24;
    private Uri filePath;
    Button SubmitBtn;
    EditText et_rc;

    FirebaseAuth mAuth;
    FirebaseFirestore db;

    FirebaseStorage storage;
    ProgressDialog dialog;
    StorageReference storageReference;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_r_c, container, false);

        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        rcImage = view.findViewById(R.id.rcImageUpload);
        et_rc = view.findViewById(R.id.et_rc);
        SubmitBtn = view.findViewById(R.id.rcSubmitBtn);
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        rcImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                selectImage();
            }
        });

        SubmitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (et_rc.getText().toString().trim().length()==0){
                    Toast.makeText(getContext(), "Enter Valid RC No.", Toast.LENGTH_SHORT).show();
                }else {
                    dialog = ProgressDialog.show(getContext(), "Uploading", "Please Wait", true);

                    uploadImage();
                }
            }
        });
        return view;
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

    private void uploadImage() {

        if (filePath != null) {

            // Code for showing progressDialog while uploading
            //ProgressDialog progressDialog
            //        = new ProgressDialog(this);
            // progressDialog.setTitle("Uploading...");
            // progressDialog.show();



            // Defining the child of storageReference
            StorageReference ref
                    = storageReference
                    .child("Driver").child(mAuth.getCurrentUser().getPhoneNumber()).child("rc");

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

                                    Map<String,Object> map = new HashMap<>();
                                    map.put("RC",et_rc.getText().toString().trim());
                                    db.collection("Driver").document(mAuth.getCurrentUser().getPhoneNumber()).update(map);
                                    dialog.dismiss();
                                    Toast
                                            .makeText(getContext(),
                                                    "Image Uploaded!!",
                                                    Toast.LENGTH_SHORT)
                                            .show();
                                    startActivity(new Intent(getActivity(),DetailsActivity.class));
                                    // startActivity(new Intent(RegisterActivity.this,MainActivity.class));
                                }
                            })

                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e)
                        {

                            // Error, Image not uploaded
                            //progressDialog.dismiss();
                            Map<String,Object>map = new HashMap<>();
                            map.put("rStatus","uploaded");
                            db.collection("Driver").document(mAuth.getCurrentUser().getPhoneNumber())
                                    .update(map);
                            dialog.dismiss();
                            Toast
                                    .makeText(getContext(),
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
        else{
            dialog.dismiss();
            Toast.makeText(getContext(), "You need to select image", Toast.LENGTH_SHORT).show();
        }
    }



    @Override
    public void onActivityResult(int requestCode,
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
                                getActivity().getContentResolver(),
                                filePath);

                rcImage.setImageBitmap(bitmap);


            } catch (IOException e) {
                // Log the exception
                e.printStackTrace();
            }
        }
    }
}