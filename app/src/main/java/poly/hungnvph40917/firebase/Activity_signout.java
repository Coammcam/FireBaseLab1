package poly.hungnvph40917.firebase;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class Activity_signout extends AppCompatActivity {
    Button SignOut;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signout);

        SignOut = findViewById(R.id.signout_button);

        mAuth = FirebaseAuth.getInstance();

        SignOut.setOnClickListener(view -> {
            mAuth.signOut();
//            Toast.makeText(Activity_signout.this, "123", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(Activity_signout.this, Activity_login.class));
        });


    }
}