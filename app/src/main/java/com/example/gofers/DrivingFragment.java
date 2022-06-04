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
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;


public class DrivingFragment extends Fragment {

    ImageView licenceImage;

    private final int PICK_IMAGE_REQUEST = 25;
    private Uri filePath;
    Button SubmitBtn;
    EditText et_driving;

    FirebaseStorage storage;
    ProgressDialog dialog;
    StorageReference storageReference;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_driving, container, false);

        licenceImage = view.findViewById(R.id.licenceImageUpload);
        et_driving = view.findViewById(R.id.et_driving);
        SubmitBtn = view.findViewById(R.id.licenceSubmitBtn);
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        licenceImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage();
            }
        });

        SubmitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (et_driving.getText().toString().trim().length()!=16){
                    Toast.makeText(getContext(), "Enter Valid Adhar Card No.", Toast.LENGTH_SHORT).show();
                }else {
                    dialog = ProgressDialog.show(getContext(), "Loading", "Please Wait", true);
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
                    .child(
                            "images/"
                                    + "licenceimagenew");

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
                                    dialog.dismiss();
                                    Toast
                                            .makeText(getContext(),
                                                    "Image Uploaded!!",
                                                    Toast.LENGTH_SHORT)
                                            .show();
                                    // startActivity(new Intent(RegisterActivity.this,MainActivity.class));
                                }
                            })

                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e)
                        {

                            // Error, Image not uploaded
                            //progressDialog.dismiss();
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

                licenceImage.setImageBitmap(bitmap);


            } catch (IOException e) {
                // Log the exception
                e.printStackTrace();
            }
        }
    }
}