package com.setmate.setmate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.setmate.setmate.databinding.ActivityPhoneAuthenticationPageBinding;

import java.util.concurrent.TimeUnit;

public class phone_authentication_page extends AppCompatActivity {


    private ActivityPhoneAuthenticationPageBinding phoneAuthenticationBinding;
    private String PhoneNumber;

    private  String otp_id ;

    private FirebaseAuth mAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth=FirebaseAuth.getInstance();
        phoneAuthenticationBinding = ActivityPhoneAuthenticationPageBinding.inflate(getLayoutInflater());
        setContentView(phoneAuthenticationBinding.getRoot());


        current_status();


        phoneAuthenticationBinding.privacyPolicies.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String policy = "https://setmate.blogspot.com/2023/04/terms-and-conditions-of-setmate-app.html";
                Uri uri = Uri.parse(policy);
                startActivity(new Intent(Intent.ACTION_VIEW,uri));

            }
        });


        phoneAuthenticationBinding.getOtpBtn.setOnClickListener(v -> {

            String phone_number = phoneAuthenticationBinding.phoneText.getText().toString();

            if (phone_number.isEmpty() ){

                phoneAuthenticationBinding.phoneText.setError("Please Enter Your Phone Number...");
                return;

            }
            if (phone_number.length()!=10){
                phoneAuthenticationBinding.phoneText.setError("Phone Number must be 10 digit");
                return;
            }

            phoneAuthenticationBinding.getOtpBtn.setText("VERIFY OTP");
            PhoneNumber= phone_number;
            phoneAuthenticationBinding.phoneText.setText("");
            phoneAuthenticationBinding.phoneText.setHint("Enter Your OTP...");

            Toast.makeText(this, "Verify Captcha Please...", Toast.LENGTH_SHORT).show();
            phone_authentication();


            Toast.makeText(this, "Wait a second for OTP", Toast.LENGTH_LONG).show();

            phoneAuthenticationBinding.getOtpBtn.setOnClickListener(v1 -> {

                String otp = phoneAuthenticationBinding.phoneText.getText().toString();

                if (otp.isEmpty()){
                    phoneAuthenticationBinding.phoneText.setError("Please Enter Your OTP");
                    return;
                }
                if (otp.length()!=6){

                    phoneAuthenticationBinding.phoneText.setError("OTP LENGTH MUST BE SIX ");
                    return;
                }




                PhoneAuthCredential authCredential = PhoneAuthProvider.getCredential(otp_id,String.valueOf(phoneAuthenticationBinding.phoneText.getText()));
                signInWithPhoneAuthCredential(authCredential);





            });

        });



    }

    private void phone_authentication(){
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber("+91"+PhoneNumber)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)                 // Activity (for callback binding)
                        .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

                            @Override
                            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                otp_id =s;
                            }

                            @Override
                            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

                                signInWithPhoneAuthCredential(phoneAuthCredential);
                            }

                            @Override
                            public void onVerificationFailed(@NonNull FirebaseException e) {

                                Toast.makeText(phone_authentication_page.this,"Something Went Wrong", Toast.LENGTH_SHORT).show();

                            }
                        })          // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(phone_authentication_page.this, "Verified Successfully", Toast.LENGTH_LONG).show();
                            startActivity(new Intent(phone_authentication_page.this,MainActivity.class));
                            finish();
                        } else {
                            Toast.makeText(phone_authentication_page.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void current_status(){
        if (mAuth.getCurrentUser()!=null){
            startActivity(new Intent(phone_authentication_page.this,MainActivity.class));
            finish();
        }
    }


}