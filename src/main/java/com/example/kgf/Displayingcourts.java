package com.example.kgf;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Displayingcourts extends AppCompatActivity {

    private ListView cortlist;
    List<Addcourts> courtslist;
    private DatabaseReference retri;
    int i;
    String ctype;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_displayingcourts);

        cortlist = findViewById(R.id.court__list);
        Intent inte = getIntent();
        i =  inte.getIntExtra(Court.EXTRA, 0);
        courtslist = new ArrayList<>();

        if(i == 1){

            ctype="National";

        }

        else if (i == 2){

            ctype="State";

        }

        else if (i == 3){

            ctype = "District";

    }

        retri = FirebaseDatabase.getInstance().getReference().child("Court").child(ctype);

    }

    @Override
    protected void onStart() {
        super.onStart();
        retri.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot courtsnapshot : dataSnapshot.getChildren()){

                    Addcourts adms = courtsnapshot.getValue(Addcourts.class);
                    courtslist.add(adms);

                }
                CourtAdapter courtAdapter = new CourtAdapter(Displayingcourts.this,courtslist);
                cortlist.setAdapter(courtAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
