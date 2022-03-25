package com.newprojrct.lr;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
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

public class Login extends AppCompatActivity {
    private EditText emailEt,passwordEt;
    private Button signIn;
    private TextView signUp;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        auth=FirebaseAuth.getInstance();

        emailEt=findViewById(R.id.emailEtLogin);
        passwordEt=findViewById(R.id.passwordEtLogin);
        signIn=findViewById(R.id.Login);
        signUp=findViewById(R.id.signUp);

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in=new Intent(Login.this,Registration.class);
                startActivity(in);
            }
        });

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email=emailEt.getText().toString();
                String password=passwordEt.getText().toString();

                if (TextUtils.isEmpty(email)){

                    emailEt.setText("Needed");
                    return;
                }if (TextUtils.isEmpty(password)){
                    passwordEt.setError("Needed");
                    return;
                }
                singIn(email,password);
            }
        });
    }

    private void singIn(String email,String password){

        auth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()){

                            startActivity(new Intent(Login.this,MainActivity.class));
                            finish();
                        }else {
                            Toast.makeText(Login.this, "Registration is not successful!"+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    }
                });
    }
}