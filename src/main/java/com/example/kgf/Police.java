package com.example.kgf;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class Police extends AppCompatActivity {

    DatabaseReference ref,ret;
    public Uri imguri;
    int i =1;
    public ImageView offpic;
    StorageReference sref;
    private StorageTask uploading;
    String imgid,imgurl, date;
    private Button choose,regis;
    ListView listView;
    List<PoliceDB> policeDBList;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_police);

        ret = FirebaseDatabase.getInstance().getReference().child("Police");
        ref = ret;
        listView = findViewById(R.id.recyview);


        progressDialog = new ProgressDialog(this);
        policeDBList = new ArrayList<>();

    }

    @Override
    protected void onStart() {
        super.onStart();

        ret.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot policesnapshot : dataSnapshot.getChildren()){

                    PoliceDB polis = policesnapshot.getValue(PoliceDB.class);
                    policeDBList.add(polis);

                }
                PoliceAdapter policeAdapter = new PoliceAdapter(Police.this,policeDBList);
                listView.setAdapter(policeAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public void add_new_officer(View view) {

        AlertDialog.Builder mydialog = new AlertDialog.Builder(Police.this);
        LayoutInflater inflater = LayoutInflater.from(Police.this);
        View myview = inflater.inflate(R.layout.input, null);
        final AlertDialog dialog = mydialog.create();
        dialog.setView(myview);
        dialog.show();

        final EditText txvid, txvname, txvarea, txvphno, txvdob;


        txvid = myview.findViewById(R.id.id);
        txvname = myview.findViewById(R.id.name);
        txvarea = myview.findViewById(R.id.area);

        date = DateFormat.getDateInstance().format(new Date());

        txvphno = myview.findViewById(R.id.phno);
        txvdob = myview.findViewById(R.id.dob);
        regis = myview.findViewById(R.id.reg);
        choose = myview.findViewById(R.id.choose);
        offpic = myview.findViewById(R.id.image);

        regis.setVisibility(View.INVISIBLE);

        sref = FirebaseStorage.getInstance().getReference("Police");
        choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(i == 1) {
                    filechooser();
                    i = 2;
                    choose.setText("Click here to upload the image");
                }
                else if(uploading!= null && uploading.isInProgress() && i!=1)
                {
                    Toast.makeText(Police.this, "Please wait while the current image is being uploaded", Toast.LENGTH_LONG).show();
                }
                else
                {
                    uploadimg();
                    progressDialog.setMessage("Please wait. Uploading Image");
                    progressDialog.show();

                }



            }
        });
        regis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String offid = txvid.getText().toString().trim();

                String offname = txvname.getText().toString().trim();

                String offarea = txvarea.getText().toString().trim();

                String offph = txvphno.getText().toString().trim();

                String offdob = txvdob.getText().toString().trim();

                if (TextUtils.isEmpty(offid)) {
                    txvid.setError("Required field");
                    return;
                }

                if (TextUtils.isEmpty(offname)) {
                    txvname.setError("Required field");
                    return;
                }

                if (TextUtils.isEmpty(offarea)) {
                    txvarea.setError("Required field");
                    return;
                }


                if (TextUtils.isEmpty(offph)) {
                    txvphno.setError("Required field");
                    return;
                }

                if (TextUtils.isEmpty(offdob)) {
                    txvdob.setError("Required field");
                    return;
                }


                PoliceDB pol = new PoliceDB(offid, offname, offarea, date, offph, imgid, offdob, imgurl);

                ref.push().setValue(pol);

                Toast.makeText(Police.this, "Data inserted Succesfully", Toast.LENGTH_LONG).show();

                dialog.dismiss();

                Police.this.finish();
            }
        });

    }

    private void filechooser() {

        Intent in = new Intent();
        in.setType("image/*");
        in.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(in, 1);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imguri = data.getData();
            offpic.setImageURI(imguri);
        }
    }

    private String getExtension(Uri uri)        {

        ContentResolver cr = getContentResolver();

        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();

        return mimeTypeMap.getExtensionFromMimeType(cr.getType(uri));

    }

    private void uploadimg() {

        imgid = System.currentTimeMillis() + "." + getExtension(imguri);

        final StorageReference refff = sref.child(imgid);

        uploading = refff.putFile(imguri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        // Get a URL to the uploaded content
                         refff.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                imgurl = String.valueOf(uri);
                                Toast.makeText(Police.this, "Click on register to insert the data", Toast.LENGTH_LONG).show();
                                choose.setText("Click on Register Now");
                                choose.setVisibility(View.INVISIBLE);

                                regis.setVisibility(View.VISIBLE);
                                progressDialog.dismiss();
                            }
                        });


                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle unsuccessful uploads
                        // ...
                    }
                });
    }
}
