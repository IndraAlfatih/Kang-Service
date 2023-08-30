package com.airun.bigboss.kangservice;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class WelcomeActivity extends AppCompatActivity {
    private TextView tv;
    private ImageView iv;
    private ImageView iiv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_welcome);
        tv=(TextView) findViewById(R.id.tekswelcome);
        iv=(ImageView)findViewById(R.id.kampus);
        iiv=(ImageView)findViewById(R.id.imagewelcome);
        Animation myanim = AnimationUtils.loadAnimation(this,R.anim.mytransition);
        Animation toup = AnimationUtils.loadAnimation(this,R.anim.downtoup);
        tv.startAnimation(toup);
        iiv.startAnimation(myanim);
        iv.startAnimation(myanim);
        final Intent i = new Intent(this, PilihAkunActivity.class);
        Thread timer = new Thread(){
            public void run(){
                try {
                    sleep(4000);
                } catch (InterruptedException e){
                    e.printStackTrace();
                }
                finally {
                    startActivity(i);
                    finish();
                }
            }
        };

        timer.start();
    }
}
