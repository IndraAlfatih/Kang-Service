package com.airun.bigboss.kangservice;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class FragmentAkun extends Fragment {

    View view;
    private FirebaseAuth firebaseauth;
    private Button signout;
    private ImageView mImageView;
    private Uri mImageUrl;
    public TextView name, email, number, alamat;
    DatabaseReference databaseReference;
    StorageReference storageReference;
    FirebaseUser user;
    String uid;
    CustomerProfile profile;

    public FragmentAkun(){
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view =inflater.inflate(R.layout.layout_akun,container,false);

        user = FirebaseAuth.getInstance().getCurrentUser();
        firebaseauth = FirebaseAuth.getInstance();
        name= (TextView) view.findViewById(R.id.textname);
        email= (TextView) view.findViewById(R.id.textemail);
        number= (TextView) view.findViewById(R.id.textphone);
        alamat= (TextView) view.findViewById(R.id.textalamat);
        mImageView= view.findViewById(R.id.picprofil);
        uid = user.getUid();
        signout=(Button)view.findViewById(R.id.btn_signout);

        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseauth.signOut();
                Intent in = new Intent(getActivity(), LoginActivity.class);
                in.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(in);
            }
        });

        storageReference=FirebaseStorage.getInstance().getReference("Profil" + "/" + user.getUid() + "/" + "profilepic.jpg");
        databaseReference=FirebaseDatabase.getInstance().getReference().child("Users");
        databaseReference.keepSynced(true);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    String nama = dataSnapshot.child(uid).child("name").getValue(String.class);
                    String imail = dataSnapshot.child(uid).child("email").getValue(String.class);
                    String nomor = dataSnapshot.child(uid).child("phone").getValue(String.class);
                    String aalamat = dataSnapshot.child(uid).child("alamat").getValue(String.class);

                    name.setText(nama);
                    email.setText(imail);
                    number.setText(nomor);
                    alamat.setText(aalamat);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        Button updateprofile = (Button) view.findViewById(R.id.btn_editprofil);
        updateprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getActivity(), UpdateProfileActivity.class);
                startActivity(in);
            }
        });

        return view;
    }
}
