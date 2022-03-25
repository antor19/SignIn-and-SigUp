package com.newprojrct.lr;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class Registration extends AppCompatActivity {

    private EditText nameEdit,emailEdit,phoneEdit,passwordEdit,confirmPasswordEdit;
    private Button register;
    private TextView login;

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        auth=FirebaseAuth.getInstance();

        nameEdit=findViewById(R.id.nameEt);
        emailEdit=findViewById(R.id.emailEt);
        phoneEdit=findViewById(R.id.phoneEt);
        passwordEdit=findViewById(R.id.passwordEt);
        confirmPasswordEdit=findViewById(R.id.confirmPasswordEt);
        register=findViewById(R.id.registerBtn);
        login=findViewById(R.id.signIn);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in=new Intent(Registration.this,Login.class);
                startActivity(in);
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name=nameEdit.getText().toString();
                String email=emailEdit.getText().toString();
                String phone=phoneEdit.getText().toString();
                String pass=passwordEdit.getText().toString();
                String confirmpass=confirmPasswordEdit.getText().toString();

                if (name.isEmpty()){

                    nameEdit.setError("Needed");
                    return;
                }if (email.isEmpty()){
                    emailEdit.setError("Needed");
                    return;

                }if (phone.isEmpty()){
                    phoneEdit.setError("Needed");
                    return;

                }if (pass.isEmpty()){
                    passwordEdit.setError("Needed");
                    return;
                }if (confirmpass.isEmpty() ||!pass.equals(confirmpass)){

                    confirmPasswordEdit.setError("Needed");
                    return;
                }

                createAccount(email,pass);
            }
        });

    }

    private void createAccount(String email,String password){

        auth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()){

                            FirebaseUser user=auth.getCurrentUser();
                            updateUi(user,email);

                            Toast.makeText(Registration.this, "Registration Success", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(Registration.this, "Registration failed"+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    }
                });

    }
    private void updateUi(FirebaseUser user,String email){

        HashMap<String,Object>map=new HashMap<>();
        map.put("name",nameEdit.getText().toString());
        map.put("email",email);
        map.put("phone",phoneEdit.getText().toString());

        DatabaseReference reference= FirebaseDatabase.getInstance().getReference().child("User");
        reference.child(user.getUid())
                .setValue(map)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if (task.isSuccessful()){

                            startActivity(new Intent(Registration.this,Login.class));
                            finish();
                            Toast.makeText(Registration.this, "Registration Success", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(Registration.this, "Registration failed"+task.getException().getMessage(), Toast.LENGTH_SHORT).show();


                        }

                    }
                });
    }
}