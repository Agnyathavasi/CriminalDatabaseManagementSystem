package com.example.kgf;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Court extends AppCompatActivity {

    public static final String EXTRA = "com.example.bmicalculator.EXTRA";

    private int i = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_court);
    }

    public void add_new_criminal(View view) {
        Intent in = new Intent(this,addcriminal.class);
        startActivity(in);
    }

    public void supreme(View view) {

        i = 1;
        Intent in = new Intent(this, Displayingcourts.class);
        in.putExtra(EXTRA, i);
        startActivity(in);

    }

    public void high(View view) {

        i = 2;
        Intent in = new Intent(this, Displayingcourts.class);
        in.putExtra(EXTRA, i);
        startActivity(in);

    }

    public void district(View view) {

        i = 3;
        Intent in = new Intent(this, Displayingcourts.class);
        in.putExtra(EXTRA, i);
        startActivity(in);

    }
}
