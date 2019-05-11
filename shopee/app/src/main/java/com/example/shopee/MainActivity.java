package com.example.shopee;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.widget.Toast;

import com.example.shopee.Adapter.pagerAdapter_main;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {
    private TabLayout tablayout;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        Thread thread =new Thread(new Runnable() {
            @Override
            public void run() {
                SharedPreferences getpre= PreferenceManager.getDefaultSharedPreferences(getBaseContext());
                boolean isfirtstart=getpre.getBoolean("firststart",true);
                if(isfirtstart){
                    startActivity(new Intent(MainActivity.this,myintro.class));
                    SharedPreferences.Editor e=getpre.edit();
                    e.putBoolean("firststart",false);
                    e.apply();
                }
            }
        });
        thread.start();

        if (android.os.Build.VERSION.SDK_INT > 9)
        {

            StrictMode.ThreadPolicy policy = new
                    StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        mViewPager = (ViewPager) findViewById(R.id.container);
        tablayout = (TabLayout) findViewById(R.id.tabs);

        FragmentManager manager=getSupportFragmentManager();

        pagerAdapter_main adapter=new pagerAdapter_main(manager);
        mViewPager.setAdapter(adapter);

        tablayout.setupWithViewPager(mViewPager);
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tablayout));
        tablayout.setTabsFromPagerAdapter(adapter);


    }
}
