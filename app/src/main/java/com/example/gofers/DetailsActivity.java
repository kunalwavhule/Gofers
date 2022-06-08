package com.example.gofers;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.Objects;

public class DetailsActivity extends AppCompatActivity {


    FirebaseFirestore db;
    FirebaseAuth mAuth;
    Button adhar_card,driving_card,rc_card,profile_card;
    public static String selectionId = null;

    Global global;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        db.collection("Driver").document(mAuth.getCurrentUser().getPhoneNumber())
        .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()){
                    DocumentSnapshot documentSnapshot = task.getResult();
                    if (documentSnapshot.exists()){
                        global = documentSnapshot.toObject(Global.class);
                        checks();
                    }else {
                        profile_card.setEnabled(false);
                        profile_card.setBackgroundResource(R.drawable.disable_button);
                        profile_card.setTextColor(getResources().getColor(R.color.grey));

                        driving_card.setEnabled(false);
                        driving_card.setBackgroundResource(R.drawable.disable_button);
                        driving_card.setTextColor(getResources().getColor(R.color.grey));

                        rc_card.setEnabled(false);
                        rc_card.setBackgroundResource(R.drawable.disable_button);
                        rc_card.setTextColor(getResources().getColor(R.color.grey));
                        Toast.makeText(DetailsActivity.this, "all buttons enabled", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        adhar_card =  findViewById(R.id.adhar_card);
        driving_card =  findViewById(R.id.driving_card);
        rc_card =  findViewById(R.id.rc_card);
        profile_card =  findViewById(R.id.profile_card);





        adhar_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectionId = "adhar";
                startActivity(new Intent(DetailsActivity.this,FragmentActivity.class));
            }
        });

        rc_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectionId = "RC";
                startActivity(new Intent(DetailsActivity.this,FragmentActivity.class));
            }
        });

        driving_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectionId = "driving";
                startActivity(new Intent(DetailsActivity.this,FragmentActivity.class));
            }
        });
        profile_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectionId = "profile";
                startActivity(new Intent(DetailsActivity.this,FragmentActivity.class));
            }
        });


    }

    private void checks() {
        if (global.getIsVerified()==null) {
            if (global.getAdhar() != null) {
                adhar_card.setEnabled(false);
                if (global.getLicence() != null) {
                    driving_card.setEnabled(false);
                    if (global.getRC() != null) {
                        rc_card.setEnabled(false);
                        if (global.getEmail() != null) {
                            Toast.makeText(DetailsActivity.this, "all process done", Toast.LENGTH_SHORT).show();
                        } else {
                            rc_card.setEnabled(false);
                            rc_card.setBackgroundResource(R.drawable.disable_button);
                            rc_card.setTextColor(getResources().getColor(R.color.grey));

                            driving_card.setEnabled(false);
                            driving_card.setBackgroundResource(R.drawable.disable_button);
                            driving_card.setTextColor(getResources().getColor(R.color.grey));

                            adhar_card.setEnabled(false);
                            adhar_card.setBackgroundResource(R.drawable.disable_button);
                            adhar_card.setTextColor(getResources().getColor(R.color.grey));
                            Toast.makeText(DetailsActivity.this, "complete email", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(DetailsActivity.this, "complete rc", Toast.LENGTH_SHORT).show();
                        profile_card.setEnabled(false);
                        profile_card.setBackgroundResource(R.drawable.disable_button);
                        profile_card.setTextColor(getResources().getColor(R.color.grey));

                        driving_card.setEnabled(false);
                        driving_card.setBackgroundResource(R.drawable.disable_button);
                        driving_card.setTextColor(getResources().getColor(R.color.grey));

                        adhar_card.setEnabled(false);
                        adhar_card.setBackgroundResource(R.drawable.disable_button);
                        adhar_card.setTextColor(getResources().getColor(R.color.grey));

                    }
                } else {
                    Toast.makeText(DetailsActivity.this, "complete licence", Toast.LENGTH_SHORT).show();
                    profile_card.setEnabled(false);
                    profile_card.setBackgroundResource(R.drawable.disable_button);
                    profile_card.setTextColor(getResources().getColor(R.color.grey));

                    rc_card.setEnabled(false);
                    rc_card.setBackgroundResource(R.drawable.disable_button);
                    rc_card.setTextColor(getResources().getColor(R.color.grey));

                    adhar_card.setEnabled(false);
                    adhar_card.setBackgroundResource(R.drawable.disable_button);
                    adhar_card.setTextColor(getResources().getColor(R.color.grey));


                }
            } else {
                Toast.makeText(DetailsActivity.this, "complete adhar", Toast.LENGTH_SHORT).show();
                profile_card.setEnabled(false);

                profile_card.setBackgroundResource(R.drawable.disable_button);
                profile_card.setTextColor(getResources().getColor(R.color.grey));

                rc_card.setEnabled(false);

                rc_card.setBackgroundResource(R.drawable.disable_button);
                rc_card.setTextColor(getResources().getColor(R.color.grey));

                driving_card.setEnabled(false);

                driving_card.setBackgroundResource(R.drawable.disable_button);
                driving_card.setTextColor(getResources().getColor(R.color.grey));

            }
        }else if (Objects.equals(global.getIsVerified(), "false")){
            startActivity(new Intent(DetailsActivity.this,UnderVerificationActivity.class));
        }else {
            startActivity(new Intent(DetailsActivity.this,MainActivity.class));
        }
    }
}