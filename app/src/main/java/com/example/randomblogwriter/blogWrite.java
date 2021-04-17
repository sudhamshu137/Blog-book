package com.example.randomblogwriter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
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
import com.shashank.sony.fancytoastlib.FancyToast;
import com.squareup.picasso.Picasso;

import java.io.IOException;

public class blogWrite extends AppCompatActivity {

    DatabaseReference db;
    EditText titleEt, bodyEt;
    TextView ntv;
    long n;
    ImageView iv;

    int picked;

    StorageReference mStorageRef;
    StorageTask uploadTask;
    Uri urii;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blog_write);

        picked = 0;

        db = FirebaseDatabase.getInstance().getReference();
        mStorageRef = FirebaseStorage.getInstance().getReference("Images");

        titleEt = findViewById(R.id.titleEt);
        bodyEt = findViewById(R.id.bodyEt);
        ntv = findViewById(R.id.ntv);


    }

    public void done(View view){

        if((titleEt.getText().toString().equals("")) || (bodyEt.getText().toString().equals(""))){
            return;
        }

        FirebaseDatabase.getInstance().getReference()
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        n = snapshot.getChildrenCount();
                        n++;

                        String title = titleEt.getText().toString();
                        String body = bodyEt.getText().toString();

                        if(title.contains("\n")){
                            title = title.replace("\n", " ");
                        }
                        if(body.contains("\n")){
                            body = body.replace("\n", " ");
                        }

                        FirebaseDatabase.getInstance().getReference().child("" + n).child("TITLE").setValue(title);
                        FirebaseDatabase.getInstance().getReference().child("" + n).child("BODY").setValue(body)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful()){

                                            if(picked == 1){
                                                final String s = n +"."+getExtension(urii);
                                                final StorageReference ref = mStorageRef.child(s);
                                                uploadTask = ref.putFile(urii)
                                                        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                                            @Override
                                                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                                                                Toast.makeText(blogWrite.this, "Uploaded successfully", Toast.LENGTH_LONG).show();

                                                                String sss = "https://firebasestorage.googleapis.com/v0/b/random-blog-writer.appspot.com/o/Images%2F" + s + "?alt=media&token=50faaae0-efb4-40dd-9e6f-3d98473a2d6b";

//                                                            int c = Integer.parseInt(n);
//                                                            c++;
//                                                            String nn = String.valueOf(c);

                                                                FirebaseDatabase.getInstance().getReference().child(String.valueOf(n)).child("URL").setValue(sss)
                                                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                            @Override
                                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                                Intent i = new Intent(blogWrite.this, next.class);
                                                                                FancyToast.makeText(blogWrite.this,"Posted Successfully!",FancyToast.LENGTH_LONG, FancyToast.SUCCESS,true).show();
                                                                                startActivity(i);
                                                                                finish();
                                                                            }
                                                                        });

                                                            }
                                                        })
                                                        .addOnFailureListener(new OnFailureListener() {
                                                            @Override
                                                            public void onFailure(@NonNull Exception exception) {
                                                                FirebaseDatabase.getInstance().getReference().child("12").child("URL").setValue("error");
                                                            }
                                                        });
                                            }
                                            else{
                                                Intent i = new Intent(blogWrite.this, next.class);
                                                FancyToast.makeText(blogWrite.this,"Posted Successfully!",FancyToast.LENGTH_LONG, FancyToast.SUCCESS,true).show();
                                                startActivity(i);
                                                finish();
                                            }


                                        }
                                        else{
                                            FancyToast.makeText(blogWrite.this,"Error! Uploading failed",FancyToast.LENGTH_LONG,FancyToast.ERROR,true).show();
                                        }
                                    }
                                });
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        FancyToast.makeText(blogWrite.this,"Error occurred!",FancyToast.LENGTH_LONG,FancyToast.ERROR,true).show();
                    }
                });
    }

    public void pick(View view){

        if(ActivityCompat.checkSelfPermission( blogWrite.this,
                Manifest.permission.READ_EXTERNAL_STORAGE) ==
                PackageManager.PERMISSION_GRANTED){

            // operation if permission is granted
            Intent i = new Intent(Intent.ACTION_PICK);
            i.setType("image/*");
            startActivityForResult(i, 1);
        }
        else{

            ActivityCompat.requestPermissions(blogWrite.this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    100);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null){
            urii = data.getData();
            picked = 1;
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), urii);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    protected void onStart() {

        FirebaseDatabase.getInstance().getReference().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                n = snapshot.getChildrenCount();
                ntv.setText(String.valueOf(n));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        super.onStart();
    }


    private String getExtension(Uri uri){
        ContentResolver cr = getContentResolver();
        MimeTypeMap mtm = MimeTypeMap.getSingleton();
        return  mtm.getExtensionFromMimeType(cr.getType(uri));
    }

}