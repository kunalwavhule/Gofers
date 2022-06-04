package com.example.gofers;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


import android.os.Bundle;

public class FragmentActivity extends AppCompatActivity {



    FragmentTransaction fm = getSupportFragmentManager().beginTransaction();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);

        if (DetailsActivity.selectionId == "adhar"){
            fm.add(R.id.fragment_container,new AdharFragment()).commit();

        }else if (DetailsActivity.selectionId == "RC"){

            fm.add(R.id.fragment_container,new RCFragment()).commit();

        }else{
            fm.add(R.id.fragment_container,new DrivingFragment()).commit();
        }






    }
}