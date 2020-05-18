package com.example.kgf;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Registration extends AppCompatActivity {

    private EditText fullname;
    private EditText email;
    private EditText identity;
    private EditText password;
    private Button register;
    private TextView signin;
    private FirebaseAuth mAuth;
    private ProgressDialog mdial;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        mAuth = FirebaseAuth.getInstance();
        mdial = new ProgressDialog(this);

        fullname = findViewById(R.id.name);
        email = findViewById(R.id.email);
        identity = findViewById(R.id.identity);
        password = findViewById(R.id.password);
        signin = findViewById(R.id.signin);
        register = findViewById(R.id.sign_up);

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                Registration.this.finish();
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mname = fullname.getText().toString().trim();
                String memail = email.getText().toString().trim();
                String midentity = identity.getText().toString().trim();
                String mpwd = password.getText().toString().trim();

                if(TextUtils.isEmpty(mname))
                {
                    fullname.setError("Required field!");
                    return;
                }

                if(TextUtils.isEmpty(memail))
                {
                    email.setError("Required field!");
                    return;
                }

                if(TextUtils.isEmpty(midentity))
                {
                    identity.setError("Required field!");
                    return;
                }

                if(TextUtils.isEmpty(mpwd))
                {
                    password.setError("Required field!");
                    return;
                }
                mdial.setMessage("Registering");
                mdial.show();
                mAuth.createUserWithEmailAndPassword(memail,mpwd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())
                        {
                            startActivity(new Intent(getApplicationContext(),dashboard.class));
                            Toast.makeText(getApplicationContext(),"Registration Successful",Toast.LENGTH_LONG).show();
                            mdial.dismiss();
                            Registration.this.finish();
                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(),"Failed to Register",Toast.LENGTH_LONG).show();
                            mdial.dismiss();
                        }
                    }
                });
            }
        });
    }
}
