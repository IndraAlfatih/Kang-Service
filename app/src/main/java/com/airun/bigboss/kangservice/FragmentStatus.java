package com.airun.bigboss.kangservice;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;


public class FragmentStatus extends Fragment {
    View view;
    private DatabaseReference mDatabaseRef;
    private List<RequestAndroid> imgList;
    private ListView lv;
    private ServiceAndroidAdapter adapter;
    private ProgressDialog progressDialog;
    private FirebaseUser user;
    private FirebaseStorage mStorage;
    private StorageReference storageReference;

    public FragmentStatus(){
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view =inflater.inflate(R.layout.layout_status,container,false);

        imgList = new ArrayList<>();
        lv = (ListView) view.findViewById(R.id.listViewImage);

        user = FirebaseAuth.getInstance().getCurrentUser();
        mStorage = FirebaseStorage.getInstance();
        storageReference = mStorage.getReference();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("Service" + "/" + user.getUid());

        mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //Fetch image data from firebase database
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    //ImageUpload class require default constructor
                    RequestAndroid img = snapshot.getValue(RequestAndroid.class);
                    imgList.add(img);
                }


                //Init adapter
                adapter = new ServiceAndroidAdapter(getActivity(), R.layout.list_statusandroid, imgList);
                //Set adapter for listview
                lv.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return view;
    }
}
