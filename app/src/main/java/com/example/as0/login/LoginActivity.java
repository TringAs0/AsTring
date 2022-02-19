package com.example.as0.login;

import com.chaos.view.PinView;
import com.example.as0.R;
import com.example.as0.VActivity;
import com.example.as0.firebase.FireStoreAccess;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.concurrent.TimeUnit;

public class LoginActivity extends AppCompatActivity
{
    private Context context;
    private FirebaseAuth mAuth;

    private EditText editText_PhoneNumber;
    private Button btn_OK;

    private PinView pinView_Otp;
    private Button btn_Verify;

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    private String phoneNumber;
    private String mVerificationId;
    String otp;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        context=this;
        mAuth=FirebaseAuth.getInstance();

        editText_PhoneNumber=findViewById(R.id.loginactivity_edit_phone_number);
        pinView_Otp=findViewById(R.id.loginactivity_pin_otp);
        pinView_Otp.setVisibility(View.GONE);
        mCallbacks=  new PhoneAuthProvider.OnVerificationStateChangedCallbacks()
        {
            @Override
            public void onCodeSent(@NonNull String verificationId, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken)
            {
                super.onCodeSent(verificationId, forceResendingToken);
                Log.d("loginactivity_tag", "onCodeSent:" + verificationId);

                mVerificationId = verificationId;
                //mResendToken = token;
            }

            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential credential)
            {
                Log.d("loginactivity_tag", "credential_getSmsCode:" + credential.getSmsCode());
            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e)
            {

            }
        };

        btn_OK=findViewById(R.id.loginactivity_btn_ok);
        btn_OK.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                phoneNumber= editText_PhoneNumber.getText().toString();

                if (phoneNumber.length()<5 || phoneNumber.isEmpty())
                    Toast.makeText(context, "올바른 전화번호를 입력해주세요.", Toast.LENGTH_SHORT).show();
                else
                {
                    sendOTP(phoneNumber);
                    pinView_Otp.setVisibility(View.VISIBLE);
                    btn_Verify.setVisibility(View.VISIBLE);
                }
            }
        });

        btn_Verify=findViewById(R.id.loginactivity_btn_verify);
        btn_Verify.setVisibility(View.GONE);
        btn_Verify.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                otp = pinView_Otp.getText().toString();
                Log.d("loginactivity","otp: "+otp);
                verifyOTP(otp);
            }

        });
    }

    private void sendOTP(String phoneNumber)
    {
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber("+1"+phoneNumber)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(LoginActivity.this)                 // Activity (for callback binding)
                        .setCallbacks(mCallbacks)          // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }

    private void verifyOTP(String OTP)
    {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId,OTP);

        FireStoreAccess.Auth.login(context, credential);
    }
}