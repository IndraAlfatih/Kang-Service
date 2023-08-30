package com.airun.bigboss.kangservice;

import android.content.Intent;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private AppBarLayout appBarLayout;
    private ViewPager viewPager;
    private long backPressedTime;
    private Toast backToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tabLayout = (TabLayout) findViewById(R.id.tablayout_id);
        appBarLayout = (AppBarLayout) findViewById(R.id.appbarid);
        viewPager = (ViewPager) findViewById(R.id.viewpager_id);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        //Adding Fragments
        adapter.AddFragment(new FragmentService(),"Service");
        adapter.AddFragment(new FragmentStatus(),"Status");
        adapter.AddFragment(new FragmentPesan(), "Info");
        adapter.AddFragment(new FragmentAkun(),"Akun");
        //adapter Setup
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.getTabAt(0).setIcon(R.drawable.icon5);
        tabLayout.getTabAt(1).setIcon(R.drawable.icon4);
        tabLayout.getTabAt(2).setIcon(R.drawable.icon2);
        tabLayout.getTabAt(3).setIcon(R.drawable.icon1);
    }

    public void audio(View view) {
        Intent intent = new Intent( MainActivity.this, AudioActivity.class);
        startActivity(intent);
    }
}
