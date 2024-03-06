package poly.hungnvph40917.firebase;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Activity_signup extends AppCompatActivity {

    EditText SU_Email, SU_Pass, SU_CPass;
    Button SU_Done;
    TextView signInRedirectText;
    private FirebaseAuth mAuth;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        SU_Email = findViewById(R.id.edtEmail);
        SU_Pass = findViewById(R.id.edtPass);
        SU_CPass = findViewById(R.id.edtCPass);
        SU_Done = findViewById(R.id.btnDone);

        signInRedirectText = findViewById(R.id.signInRedirectText);

        mAuth = FirebaseAuth.getInstance();


        SU_Done.setOnClickListener(view -> {
            String email = SU_Email.getText().toString().trim();
            String pass = SU_Pass.getText().toString();
            String confirmPass = SU_CPass.getText().toString();

            if (email.isEmpty() || pass.isEmpty() || confirmPass.isEmpty()) {
                Toast.makeText(Activity_signup.this, "Something information is empty", Toast.LENGTH_SHORT).show();
            } else if (!(confirmPass.equals(pass))) {
                Toast.makeText(Activity_signup.this, "Confirm password is not correct", Toast.LENGTH_SHORT).show();
            } else {
                mAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(Activity_signup.this, "Sign up completed", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(Activity_signup.this, Activity_login.class));
                            finish();
                        } else {
                            Log.w(TAG, "createUserWithEmail: failure", task.getException());
                            Toast.makeText(Activity_signup.this, "Sign up failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        signInRedirectText.setOnClickListener(view -> {
            startActivity(new Intent(Activity_signup.this, Activity_login.class));
        });

    }
}