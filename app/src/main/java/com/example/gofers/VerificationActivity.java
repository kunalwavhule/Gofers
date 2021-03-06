package com.example.gofers;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

public class VerificationActivity extends AppCompatActivity {

    TextView show_no;
    private String verificationId;
    Button btn_submit;
    ProgressBar progressBar2;
    EditText etxt_verification;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification);
        btn_submit = findViewById(R.id.btn_submit);
        etxt_verification = findViewById(R.id.etxt_verification);
        show_no = findViewById(R.id.show_no);
        progressBar2 = findViewById(R.id.progressBar2);

        show_no.setText(String.format(
                "+91-%s",getIntent().getStringExtra("phone")
        ));

        verificationId = getIntent().getStringExtra("verificationId");

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (verificationId !=null){
                    btn_submit.setVisibility(View.INVISIBLE);
                    progressBar2.setVisibility(View.VISIBLE);
                    String code = etxt_verification.getText().toString();

                    PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
                    FirebaseAuth.getInstance().signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                Intent intent =new Intent(VerificationActivity.this,DetailsActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                            }else {
                                Toast.makeText(VerificationActivity.this, "Invalid OTP", Toast.LENGTH_SHORT).show();
                                btn_submit.setVisibility(View.VISIBLE);
                                progressBar2.setVisibility(View.GONE);
                            }
                        }
                    });
                }
            }
        });
    }
}