package in.edunomics.chat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    Button login;
    EditText email,password;
    TextView sign_up;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_simple_light);
        mAuth = FirebaseAuth.getInstance();
        init();
    }

    private void init() {
        login = findViewById(R.id.login_btn);
        sign_up = findViewById(R.id.sign_up_btn);
        email = findViewById(R.id.email);
        password = findViewById(R.id.passwprd);

        sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,RegsisterActivity.class));
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String e = email.getText().toString();
                String p = password.getText().toString();
                if(e.equals("") || p.equals(""))
                {
                    msg("Please check email and password");
                }

                mAuth.signInWithEmailAndPassword(e, p)
                        .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information

                                    FirebaseUser user = mAuth.getCurrentUser();
                                    updateUI(user);
                                } else {
                                    // If sign in fails, display a message to the user.

                                    msg("Please check email and passsword");

                                    //updateUI(null);
                                    // ...
                                }

                                // ...
                            }
                        });
                startActivity(new Intent(LoginActivity.this,MainActivity.class));


            }
        });
    }

    private void msg(String msg) {
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

    private void updateUI(final FirebaseUser fUser)
    {
        if(fUser!=null)
        {
            startActivity(new Intent(LoginActivity.this,MainActivity.class));
            finish();
        }
    }
}
