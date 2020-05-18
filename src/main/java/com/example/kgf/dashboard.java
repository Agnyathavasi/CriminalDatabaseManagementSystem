package com.example.kgf;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;


public class dashboard extends AppCompatActivity {
    public static final String EXTRA = "com.example.kgf.EXTRA";

    FirebaseAuth mauth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard);

        mauth = FirebaseAuth.getInstance();
    }

    public void cases(View view) {

        Intent in = new Intent(this,Cases.class);
        startActivity(in);

    }

    public void criminals(View view) {

        Intent in = new Intent(this,Criminals.class);
        startActivity(in);

    }

    public void police(View view) {
        Intent in = new Intent(this,Police.class);
        startActivity(in);
    }



    public void court_details(View view) {
        Intent in = new Intent(this,Court.class);
        startActivity(in);
    }

    public void new_case(View view) {
        Intent in = new Intent(this,newcase.class);
        startActivity(in);
    }

    public void signout(View view) {
        mauth.signOut();
        int i = 1;
        Intent in = new Intent(this, MainActivity.class);
        in.putExtra(EXTRA, i);
        startActivity(in);
        dashboard.this.finish();
    }
}