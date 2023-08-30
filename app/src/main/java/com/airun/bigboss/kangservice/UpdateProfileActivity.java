package com.airun.bigboss.kangservice;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.core.Tag;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Random;

public class UpdateProfileActivity extends AppCompatActivity {

    private static final String TAG = UpdateProfileActivity.class.getSimpleName();
    FirebaseDatabase mDatabase;
    DatabaseReference databaseReference;
    EditText e1, e2, e3, e4;

    private static final int Gallery_Request = 1;
    String user_id;
    StorageReference storageReference;
    String name, number, address, email;
    Button update;
    ProgressDialog progressDialog;
    ImageView i1;

    Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_updateprofile);

        mDatabase = FirebaseDatabase.getInstance();
        databaseReference = mDatabase.getReference();
        e1 = (EditText) findViewById(R.id.editusername);
        e2 = (EditText) findViewById(R.id.editnomor);
        e3 = (EditText) findViewById(R.id.editalamat);
        e4 = (EditText) findViewById(R.id.editemail);
        i1 = (ImageView) findViewById(R.id.uppicprofil);
        update = (Button) findViewById(R.id.btnupdate);
        storageReference = FirebaseStorage.getInstance().getReference();
        progressDialog = new ProgressDialog(this);

        getUser_Profile_Data();
        getProfile_User();
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateuser();
            }
        });

        i1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                Intent i = new Intent();
                i.setAction(Intent.ACTION_GET_CONTENT);
                i.setType("image/*");
                startActivityForResult(i, Gallery_Request);
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        imageUri = data.getData();
            Picasso.with(this).load(imageUri).into(i1);
    }

    private void getUser_Profile_Data() {
        FirebaseUser users = FirebaseAuth.getInstance().getCurrentUser();
        storageReference.child("Profil" + "/" + users.getUid() + "/" + "profilepic.jpg");
        databaseReference.child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        Log.d(TAG, "onDataChange: User id :- " + FirebaseAuth.getInstance().getCurrentUser().getUid());
                        String username, email, number, address;

                        username = dataSnapshot.child("name").getValue().toString();
                        email = dataSnapshot.child("email").getValue().toString();
                        number = dataSnapshot.child("phone").getValue().toString();
                        address = dataSnapshot.child("alamat").getValue().toString();

                        e1.setText(username);
                        e2.setText(number);
                        e3.setText(address);
                        e4.setText(email);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
    }

    private void getProfile_User() {


    }

    private void updateuser() {
            address = e3.getText().toString();
            email = e4.getText().toString();
            name = e1.getText().toString();
            number = e2.getText().toString();

            CustomerProfile profile = new CustomerProfile(name, email, number, address);
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            databaseReference.child("Users").child(user.getUid()).setValue(profile);

            StorageReference ref_child = storageReference.child(user.getUid() + "/" + "profilepic.jpg");

            ref_child.putFile(imageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Toast.makeText(UpdateProfileActivity.this,"Update Berhasil",Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(UpdateProfileActivity.this, MainActivity.class);
                            startActivity(intent);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(UpdateProfileActivity.this, "Update Gagal -> "+ e,Toast.LENGTH_SHORT).show();
                        }
                    });
        }
}
