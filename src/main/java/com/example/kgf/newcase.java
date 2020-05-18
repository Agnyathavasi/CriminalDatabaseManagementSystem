package com.example.kgf;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.util.Date;

public class newcase extends AppCompatActivity {
    Spinner txtComplainttype;
    EditText txtfir,txtname,txtoccupation,txtmobile,txtaddress,txtstatement;
    Button register;
    DatabaseReference reff;
    Case member;
    String cmpl,date;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newcase);
        txtfir = findViewById(R.id.firid);
        txtname = findViewById(R.id.nameofcriminal);
        date = DateFormat.getDateInstance().format(new Date());
        txtoccupation = findViewById(R.id.occupation);
        txtmobile = findViewById(R.id.mobilenumber);
        txtaddress = findViewById(R.id.address);
        txtstatement = findViewById(R.id.complaintstatement);
        register = findViewById(R.id.register);
        txtComplainttype = findViewById(R.id.spinner2);
        member = new Case();
        reff = FirebaseDatabase.getInstance().getReference().child("Case");
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(txtmobile.getText().toString().trim().length()!=10)
                {
                    Toast.makeText(newcase.this,"Mobile Number is not proper",Toast.LENGTH_LONG).show();
                    return;
                }

                cmpl = txtComplainttype.getSelectedItem().toString();
                if(cmpl.equals("Complaint type"))
                {
                    Toast.makeText(newcase.this,"Select a Complaint type",Toast.LENGTH_LONG).show();
                    return;
                }
                member.setFir(txtfir.getText().toString().trim());
                member.setName(txtname.getText().toString().trim());
                member.setDate(date);
                member.setOccupation(txtoccupation.getText().toString().trim());
                member.setAddress(txtaddress.getText().toString().trim());
                member.setMobile(txtmobile.getText().toString().trim());
                member.setComplainttype(cmpl);
                member.setStatement(txtstatement.getText().toString().trim());
                member.setStatus("Not Solved");
                reff.push().setValue(member);
                Toast.makeText(newcase.this,"Data inserted Succesfully",Toast.LENGTH_LONG).show();

                newcase.this.finish();
            }
        });
    }
}
