package com.example.gofers;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class DetailsActivity extends AppCompatActivity {

    CardView adhar_card,driving_card,rc_card,profile_card;

    //Button button,button2,button3;
    public static String selectionId = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        adhar_card = (CardView) findViewById(R.id.adhar_card);
        driving_card = (CardView) findViewById(R.id.driving_card);
        rc_card = (CardView) findViewById(R.id.rc_card);
        profile_card = (CardView) findViewById(R.id.profile_card);



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


    }
}