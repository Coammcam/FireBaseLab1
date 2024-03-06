package poly.hungnvph40917.firebase;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthActionCodeException;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class Activity_loginwithphonenum extends AppCompatActivity {

    private FirebaseAuth mAuth;
    protected PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    private String mVerificationId;
    Button getOTP, loginWithOPT;
    EditText edtPhone, edtOTP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginwithphonenum);

        getOTP = findViewById(R.id.getOPT_button);
        loginWithOPT = findViewById(R.id.loginWPN_button);
        edtPhone = findViewById(R.id.edtPhoneNum);
        edtOTP = findViewById(R.id.edtOTP);

        mAuth = FirebaseAuth.getInstance();

        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                edtOTP.setText(phoneAuthCredential.getSmsCode());
            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {
                Toast.makeText(Activity_loginwithphonenum.this, "Đã vượt quá số lượt gửi mã. vui lòng thử lại sau!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
//                super.onCodeSent(s, forceResendingToken);
                mVerificationId = s;
            }
        };

        getOTP.setOnClickListener(view -> {
            String phoneNumber = edtPhone.getText().toString().trim();

            if (phoneNumber.isEmpty()) {
                Toast.makeText(Activity_loginwithphonenum.this, "Enter phone number", Toast.LENGTH_SHORT).show();
            } else if (!phoneNumber.matches("[0-9]+")) {
                Toast.makeText(Activity_loginwithphonenum.this, "Phone number just NAN", Toast.LENGTH_SHORT).show();
            } else {
                getOTP(phoneNumber);
            }
        });

        loginWithOPT.setOnClickListener(view -> {
            String userOTP = edtOTP.getText().toString();

            if (userOTP.isEmpty()) {
                Toast.makeText(Activity_loginwithphonenum.this, "Enter OTP!", Toast.LENGTH_SHORT).show();
                return;
            }

            // Gọi hàm verifyOTP để xác nhận OTP
            verifyOTP(userOTP);
        });
    }

    private void getOTP(String phoneNumber) {
        PhoneAuthOptions options = PhoneAuthOptions
                .newBuilder(mAuth)
                .setPhoneNumber("+84" + phoneNumber)
                .setActivity(this)
                .setTimeout(60L, TimeUnit.SECONDS)
                .setCallbacks(mCallbacks)
                .build();
//        Toast.makeText(Activity_loginwithphonenum.this, "123", Toast.LENGTH_SHORT).show();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }

    private void verifyOTP(String code) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, code);
        signInWithPhoneAuthCredential(credential);
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(Activity_loginwithphonenum.this, "Login successfully", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(Activity_loginwithphonenum.this, Activity_signout.class));

                            FirebaseUser user = task.getResult().getUser();

                        } else {
//                            Log.w(TAG, "signInWithCredential: failed", task.getException());
                            edtOTP.setError("OPT not correct");
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {

                            }
                        }
                    }
                });
    }
}