package poly.hungnvph40917.firebase;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Activity_login extends AppCompatActivity {

    EditText SI_Email, SI_Pass;
    Button LogIn;
    TextView signUpRedirectText, forgotPassword, LogInWithPhoneNum;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        SI_Email = findViewById(R.id.edtEmail);
        SI_Pass = findViewById(R.id.edtPass);
        LogIn = findViewById(R.id.login_button);
        LogInWithPhoneNum = findViewById(R.id.signInPhoneNum);

        signUpRedirectText =  findViewById(R.id.signupRedirectText);
        forgotPassword = findViewById(R.id.forgotPassword);

        mAuth = FirebaseAuth.getInstance();



        LogIn.setOnClickListener(view -> {
            String email = SI_Email.getText().toString().trim();
            String pass = SI_Pass.getText().toString().trim();

            if (!email.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                if (!pass.isEmpty()) {
                    mAuth.signInWithEmailAndPassword(email, pass).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(Activity_login.this, "Log in completed", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(Activity_login.this, Activity_signout.class));
                            finish();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(Activity_login.this, "Log in failed", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    SI_Pass.setError("Password cannot be empty");
                }
            } else if (email.isEmpty()) {
                SI_Email.setError("Email cannot be empty");
            } else {
                SI_Email.setError("Please enter a valid email");
            }
        });

        signUpRedirectText.setOnClickListener(view -> {
            startActivity(new Intent(Activity_login.this, Activity_signup.class ));
        });

        forgotPassword.setOnClickListener(view -> {
            String email_Reset = SI_Email.getText().toString();
            mAuth.sendPasswordResetEmail(email_Reset).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(Activity_login.this, "Send email", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(Activity_login.this, "Error", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        });

        LogInWithPhoneNum.setOnClickListener(view -> {
            startActivity(new Intent(Activity_login.this, Activity_loginwithphonenum.class));
        });
    }
}