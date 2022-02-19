package com.example.as0.login;

import com.chaos.view.PinView;
import com.example.as0.R;
import com.example.as0.firebase.FireStoreAccess;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.concurrent.TimeUnit;

public class VerifyOTPActivity extends AppCompatActivity
{
    private Context context;
    private FirebaseAuth mAuth;

    private PinView pinView_Otp;
    private Button btn_OK;

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    private String phoneNumber;
    private String mVerificationId;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_o_t_p);

        context=this;
        mAuth=FirebaseAuth.getInstance();

        phoneNumber= getIntent().getStringExtra("phoneNumber");

        pinView_Otp=findViewById(R.id.verifyotpactivity_pin_otp);




        mCallbacks= new PhoneAuthProvider.OnVerificationStateChangedCallbacks()
        {
            @Override
            public void onCodeSent(@NonNull String verificationId, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken)
            {
                super.onCodeSent(verificationId, forceResendingToken);
                Log.d("TAG", "onCodeSent:" + verificationId);

                mVerificationId = verificationId;
                //mResendToken = token;
            }

            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential credential)
            {
                FireStoreAccess.Auth.login(context, credential);
            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e)
            {

            }
        };

        sendOTP(phoneNumber);

        btn_OK=findViewById(R.id.verifyotpactivity_btn_ok);
        btn_OK.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String otp = pinView_Otp.getText().toString();
                PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId,otp);
                FireStoreAccess.Auth.login(context, credential);
            }

        });
    }

    private void sendOTP(String phoneNumber)
    {
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber("+82"+phoneNumber)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(VerifyOTPActivity.this)                 // Activity (for callback binding)
                        .setCallbacks(mCallbacks)          // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }
}