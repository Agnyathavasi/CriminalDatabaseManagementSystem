package com.example.kgf;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Criminals extends AppCompatActivity {

    private ListView listView;
    DatabaseReference ata;
    List<New_criminals> ncrimlist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_criminals);

        listView = findViewById(R.id.lists);
        ata = FirebaseDatabase.getInstance().getReference().child("Criminal");

        ncrimlist = new ArrayList<>();

    }

    @Override
    protected void onStart() {
        super.onStart();
        ata.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot crimsnapshot : dataSnapshot.getChildren()){

                    New_criminals newcri = crimsnapshot.getValue(New_criminals.class);
                    ncrimlist.add(newcri);

                }
                CriminalAdapter criminalAdapter = new CriminalAdapter(Criminals.this,ncrimlist);
                listView.setAdapter(criminalAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}