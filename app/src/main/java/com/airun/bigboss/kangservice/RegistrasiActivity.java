package com.airun.bigboss.kangservice;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegistrasiActivity extends AppCompatActivity implements View.OnClickListener{

    private Button btnsignup;
    private EditText etextusername, etextemail, etextpassword, etextalamat, etextnumber;
    private ProgressDialog prodialog;
    private ImageView profil_image;
    private CheckBox check;
    private FirebaseAuth regauth;
    FirebaseDatabase mDatabase;
    DatabaseReference mReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_registrasi);

        btnsignup = (Button) findViewById(R.id.btn_signUp);
        etextusername = (EditText) findViewById(R.id.editusername);
        etextpassword = (EditText) findViewById(R.id.editpassword);
        etextnumber = (EditText) findViewById(R.id.editnumber);
        etextemail = (EditText) findViewById(R.id.editemail);
        etextalamat = (EditText) findViewById(R.id.editalamat);
        check = (CheckBox) findViewById(R.id.chkBox1);

        final CheckBox checkBox = (CheckBox) findViewById(R.id.chkBox1);

        regauth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance();
        mReference = mDatabase.getReference();
        prodialog = new ProgressDialog(this);


        btnsignup.setOnClickListener(this);

        check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean b) {
                if(check.isChecked()) {
                    btnsignup.setEnabled(true);
                } else {
                    btnsignup.setEnabled(false);
                }
            }
        });
    }

    private void registerUser() {
        final String name = etextusername.getText().toString().trim();
        final String email = etextemail.getText().toString().trim();
        String password = etextpassword.getText().toString().trim();
        final String phone = etextnumber.getText().toString().trim();
        final String alamat = etextalamat.getText().toString().trim();

        prodialog.setTitle("Registering User");
        prodialog.setMessage("Please wait while we create your account...");
        prodialog.setCanceledOnTouchOutside(false);
        prodialog.show();

        if (name.isEmpty()) {
            prodialog.dismiss();
            etextusername.setError(getString(R.string.input_error_name));
            etextusername.requestFocus();
            return;
        }

        if (email.isEmpty()) {
            prodialog.dismiss();
            etextemail.setError(getString(R.string.input_error_email));
            etextemail.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            prodialog.dismiss();
            etextemail.setError(getString(R.string.input_error_email_invalid));
            etextemail.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            prodialog.dismiss();
            etextpassword.setError(getString(R.string.input_error_password));
            etextpassword.requestFocus();
            return;
        }

        if (password.length() < 6) {
            prodialog.dismiss();
            etextpassword.setError(getString(R.string.input_error_password_length));
            etextpassword.requestFocus();
            return;
        }

        if (phone.isEmpty()) {
            prodialog.dismiss();
            etextnumber.setError(getString(R.string.input_error_phone));
            etextnumber.requestFocus();
            return;
        }

        regauth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {

                            CustomerProfile user = new CustomerProfile(
                                    name,
                                    email,
                                    phone,
                                    alamat
                            );

                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        prodialog.dismiss();
                                        Toast.makeText(RegistrasiActivity.this, getString(R.string.registration_success), Toast.LENGTH_LONG).show();
                                        goLogin();
                                    } else {
                                        prodialog.dismiss();
                                        Toast.makeText(RegistrasiActivity.this, "Registrasi Gagal!", Toast.LENGTH_LONG).show();
                                    }
                                }
                            });

                        } else {
                            prodialog.dismiss();
                            Toast.makeText(RegistrasiActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });

    }

    @Override
    public void onClick(View v) {
        if(v==btnsignup)
        {
            registerUser();
        }
    }

    public void goLogin()
    {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);

    }
}
