package com.example.kgf;


import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

import java.text.DateFormat;
import java.util.Date;
import java.util.Objects;

public class addcriminal extends AppCompatActivity {

    EditText firid,cirminalName,courtName,punishment,capturedBy,height;
    ImageView criminal;
    int i =1;
    Button upload,register;
    Spinner sexspinner,ctypespinner;
    DatabaseReference crim,court;
    StorageReference str;
    public Uri imguri;
    private StorageTask uploading;
    String cimgid ,cimgurl;
    ProgressDialog progr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addcriminal);
        firid = findViewById(R.id.firid);
        cirminalName = findViewById(R.id.nameofcriminal);
        courtName = findViewById(R.id.courtname);
        ctypespinner = findViewById(R.id.court_typ);
        punishment = findViewById(R.id.punishment_time);
        capturedBy = findViewById(R.id.captured_by);
        height = findViewById(R.id.height);

        criminal = findViewById(R.id.imageofcriminal);
        sexspinner = findViewById(R.id.spinner2);

        progr = new ProgressDialog(this);
        upload = findViewById(R.id.chooseimage);
        register = findViewById(R.id.registercriminal);

        register.setVisibility(View.INVISIBLE);
        crim = FirebaseDatabase.getInstance().getReference().child("Criminal");

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(i == 1) {
                    filechooser();
                    i = 2;
                    upload.setText("Click here to upload the image");
                }
                else if(uploading!= null && uploading.isInProgress() && i!=1)
                {
                    Toast.makeText(addcriminal.this, "Please wait while the current image is being uploaded", Toast.LENGTH_LONG).show();
                }
                else
                {
                    uploadimg();
                    progr.setMessage("Please wait. Uploading Image");
                    progr.show();
                }
            }
        });
        str = FirebaseStorage.getInstance().getReference("Criminals");

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String crimname,caseNumber,punish,captby,heiht,date,sex;
                sex = sexspinner.getSelectedItem().toString().trim();
                date = DateFormat.getDateInstance().format(new Date());
                if(sex.equals("Sex"))
                {
                    Toast.makeText(getApplicationContext(),"Please select a particular sex",Toast.LENGTH_LONG).show();
                    return;
                }
                crimname = cirminalName.getText().toString().trim();
                caseNumber = firid.getText().toString().trim();
                punish = punishment.getText().toString().trim() + " years";
                captby = capturedBy.getText().toString().trim();
                heiht = height.getText().toString().trim() + " cm";



                New_criminals Add = new New_criminals(crimname,caseNumber,punish,captby,heiht,cimgid,cimgurl,sex,date);
                crim.push().setValue(Add);

                String cname,ctype;
                cname = courtName.getText().toString().trim();
                ctype = ctypespinner.getSelectedItem().toString().trim();
                if(ctype.equals("Court Type"))
                {
                    Toast.makeText(getApplicationContext(),"Please select a particular Court Type",Toast.LENGTH_LONG).show();
                    return;
                }

                court = FirebaseDatabase.getInstance().getReference().child("Court").child(ctype);
                Addcourts mtv = new Addcourts(cname,caseNumber,date);
                court.push().setValue(mtv);

                addcriminal.this.finish();


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
            criminal.setImageURI(imguri);
        }
    }

    private String getExtension(Uri uri) {
        ContentResolver cr = getContentResolver();

        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();

        return mimeTypeMap.getExtensionFromMimeType(cr.getType(uri));

    }

    private void uploadimg() {

        cimgid = System.currentTimeMillis() + "." + getExtension(imguri);

        final StorageReference refff = str.child(cimgid);

        uploading = refff.putFile(imguri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        // Get a URL to the uploaded content
                        refff.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                cimgurl = String.valueOf(uri);
                                Toast.makeText(addcriminal.this, "Click on register to insert the data", Toast.LENGTH_LONG).show();
                                upload.setText("Click on Register Now");
                                progr.dismiss();
                                upload.setVisibility(View.INVISIBLE);
                                register.setVisibility(View.VISIBLE);
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
