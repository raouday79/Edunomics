package in.edunomics.chat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import in.edunomics.chat.modal.EdunomicsUserModal;

import android.content.Intent;
import android.os.Bundle;
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
import com.google.firebase.database.FirebaseDatabase;

import java.util.logging.Logger;

public class RegsisterActivity extends AppCompatActivity {

    Button signup;
    TextView login;
    EditText name,email,password;
    EdunomicsUserModal eu = new EdunomicsUserModal();
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regsister);
        init();
    }

    private void init() {
        mAuth = FirebaseAuth.getInstance();
        signup = findViewById(R.id.signup);
        login = findViewById(R.id.login_clk);
        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        password = findViewById(R.id.passwprd);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegsisterActivity.this, LoginActivity.class));
                finish();
            }
        });
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String n = name.getText().toString();
                String e = email.getText().toString();
                String p = password.getText().toString();
                if(p.length()<6)
                {
                    msg("Password length must be grater then 6");
                    return;

                }
                if(e.equals("") || p.equals("") || n.equals(""))
                {
                    msg("Please check your details.");
                    return;
                }
                eu.setName(n);
                eu.setMail(e);
                eu.setPassword(p);

                mAuth.createUserWithEmailAndPassword(e, p)
                        .addOnCompleteListener(RegsisterActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                   /// Log.d(TAG, "createUserWithEmail:success");
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    FirebaseDatabase.getInstance().getReference()
                                            .child("user")
                                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                            .setValue(eu);
                                    updateUI(user);
                                } else {
                                    // If sign in fails, display a message to the user.
                                    //Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                    Toast.makeText(RegsisterActivity.this, task.getException().getMessage(),
                                            Toast.LENGTH_SHORT).show();
                                    updateUI(null);
                                }

                                // ...
                            }
                        });

            }
        });

    }

    private void msg(String msg) {
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
    }


    private void updateUI(final FirebaseUser fUser)
    {
        if(fUser!=null)
        {
            startActivity(new Intent(RegsisterActivity.this,MainActivity.class));
            finish();

        }
    }
}
