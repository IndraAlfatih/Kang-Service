package com.airun.bigboss.kangservice;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class KipasActivity extends AppCompatActivity implements View.OnClickListener {

    private Button button, kirim;
    private CheckBox box1;
    private ImageView imagekerusakan;
    private EditText etextmerk, etextversi, etextkerusakan;
    private ProgressDialog prodialog;
    private FirebaseUser user;
    private ProgressBar mProgress;
    FirebaseDatabase mDatabase;
    FirebaseStorage mStorage;
    StorageReference storageReference;
    DatabaseReference mReference;
    Uri imageUri;
    public static final int REQUEST_CODE = 1234;
    public static final String FB_STORAGE_PATH = "Service/";
    private static final String TAG = "AndroidActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_kipas);

        final CheckBox checkBox = (CheckBox) findViewById(R.id.chkBox1);
        button = (Button) findViewById(R.id.btnterm);
        kirim = (Button) findViewById(R.id.btn_send);
        etextmerk = (EditText) findViewById(R.id.etextmerkkipas);
        etextversi = (EditText) findViewById(R.id.etextversikipas);
        etextkerusakan = (EditText) findViewById(R.id.etextkerusakankipas);
        imagekerusakan = (ImageView) findViewById(R.id.upgambar3);
        box1 = (CheckBox) findViewById(R.id.chkBox1);

        kirim.setOnClickListener(this);
        button.setOnClickListener(this);
        prodialog = new ProgressDialog(this);

        user = FirebaseAuth.getInstance().getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance();
        mStorage = FirebaseStorage.getInstance();
        mReference = mDatabase.getReference("Service" + "/" + user.getUid());
        storageReference = mStorage.getReference();

        box1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean b) {
                if (box1.isChecked()) {
                    kirim.setEnabled(true);
                } else {
                    kirim.setEnabled(false);
                }
            }
        });

        imagekerusakan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select image"), REQUEST_CODE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();

            Picasso.with(this).load(imageUri).into(imagekerusakan);
        }
    }

    public String getImageExt(Uri uri) {
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    private void sendRequest() {
        final String Rmerk = etextmerk.getText().toString().trim();
        final String Rversi = etextversi.getText().toString().trim();
        final String Rkerusakan = etextkerusakan.getText().toString().trim();

        prodialog.setTitle("Sending Request");
        prodialog.setMessage("Please wait while send request...");
        prodialog.setCanceledOnTouchOutside(false);
        prodialog.show();

        if (Rmerk.isEmpty()) {
            prodialog.dismiss();
            etextmerk.setError("Harap masukkan merk kipas angin");
            etextmerk.requestFocus();
            return;
        }

        if (Rkerusakan.isEmpty()) {
            prodialog.dismiss();
            etextkerusakan.setError("Harap masukkan deskripsi kerusakan");
            etextkerusakan.requestFocus();
            return;
        }

        final StorageReference ref = storageReference.child(FB_STORAGE_PATH + System.currentTimeMillis() + "." + getImageExt(imageUri));
        ref.putFile(imageUri).continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                if (!task.isSuccessful())
                {
                    throw task.getException();
                }
                return ref.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>()
        {
            @Override
            public void onComplete(@NonNull Task<Uri> task)
            {
                if (task.isSuccessful())
                {
                    Uri downloadUri = task.getResult();
                    Log.e(TAG, "then: " + downloadUri.toString());

                    prodialog.dismiss();
                    Toast.makeText(getApplicationContext(), "Request telah terkirim", Toast.LENGTH_SHORT).show();
                    RequestAndroid req_android = new RequestAndroid(etextmerk.getText().toString(),etextversi.getText().toString(),
                            downloadUri.toString(),etextkerusakan.getText().toString());

                    mReference.push().setValue(req_android);

                    Intent intent = new Intent(KipasActivity.this, MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                } else
                {
                    prodialog.dismiss();
                    Toast.makeText(KipasActivity.this, "Request Gagal : " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        if(v==button)
        {
            openDialog();
        }
        else if (v==kirim)
        {
            sendRequest();
        }
    }

    public void openDialog() {
        Termofservice term = new Termofservice();
        term.show(getSupportFragmentManager(),"Syarat dan Ketentuan");
    }
}

