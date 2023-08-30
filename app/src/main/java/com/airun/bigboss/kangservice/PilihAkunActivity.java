package com.airun.bigboss.kangservice;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class PilihAkunActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_pilihakun);
    }

    public void kangservice(View view) {
        Intent intent = new Intent(PilihAkunActivity.this, LoginKangActivity.class);
        startActivity(intent);
    }

    public void customer(View view) {
        Intent intent = new Intent(PilihAkunActivity.this, LoginActivity.class);
        startActivity(intent);
    }
}
