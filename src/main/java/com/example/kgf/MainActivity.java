package com.example.kgf;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {

    private EditText memail,mpassword;
    private TextView signup;
    private Button login;
    private FirebaseAuth mAuth;
    private ProgressDialog mdial;
    Switch remember;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent inte = getIntent();
        int bmind =  inte.getIntExtra(dashboard.EXTRA, 0);

        mAuth = FirebaseAuth.getInstance();
        mdial = new ProgressDialog(this);
        memail = findViewById(R.id.userid);
        mpassword = findViewById(R.id.pasword);
        signup = findViewById(R.id.signup);
        login = findViewById(R.id.submit);
        remember = findViewById(R.id.switch1);

        SharedPreferences preferences = getSharedPreferences("switch",MODE_PRIVATE);
        String swich = preferences.getString("remember","");

        if(bmind == 1)
        {
            Toast.makeText(getApplicationContext(),"Please login to your Account",Toast.LENGTH_LONG).show();
            preferences.edit().clear().apply();
        }

        else if (swich.equals("true") )
        {
            startActivity(new Intent(getApplicationContext(),dashboard.class));

            MainActivity.this.finish();
        }

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String em = memail.getText().toString().trim();
                String pwd = mpassword.getText().toString().trim();

                if(TextUtils.isEmpty(em))
                {
                    memail.setError("Required field!");
                    return;
                }

                if(TextUtils.isEmpty(pwd))
                {
                    mpassword.setError("Required field!");
                    return;
                }
                mdial.setMessage("Signing In");
                mdial.show();

                mAuth.signInWithEmailAndPassword(em,pwd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful())
                        {
                            startActivity(new Intent(getApplicationContext(),dashboard.class));
                            Toast.makeText(getApplicationContext(),"Login Successful",Toast.LENGTH_LONG).show();
                            mdial.dismiss();
                            MainActivity.this.finish();
                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(),"Failed to Login",Toast.LENGTH_LONG).show();
                            mdial.dismiss();
                        }

                    }
                });
                if(remember.isChecked())
                {
                    SharedPreferences preferences = getSharedPreferences("switch",MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("remember","true");
                    editor.apply();

                }
                else if (!remember.isChecked())
                {
                    SharedPreferences preferences = getSharedPreferences("switch",MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("remember","false");
                    editor.apply();

                }


            }

        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Registration.class));
                MainActivity.this.finish();
            }
        });

    }
}

