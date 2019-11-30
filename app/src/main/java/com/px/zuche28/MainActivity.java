package com.px.zuche28;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.annotation.NonNull;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;

import com.px.zuche28.Fragment.HomeFragment;
import com.px.zuche28.Fragment.MyFragment;
import com.px.zuche28.Fragment.MyFragmentStatePagerAdapter;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    private ViewPager viewPager;
    private BottomNavigationView bottomNavigationView;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
//                    mTextMessage.setText(R.string.title_home);
                    viewPager.setCurrentItem(0);
                    return true;
                case R.id.navigation_dashboard:
//                    mTextMessage.setText(R.string.title_dashboard);
                    viewPager.setCurrentItem(1);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //去掉标题栏
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        bottomNavigationView = findViewById(R.id.nav_view);
//        mTextMessage = findViewById(R.id.message);

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("action.refreshMain");
        registerReceiver(mRefreshBroadcastReceiver, intentFilter);


        //设置ViewPage
        viewPager = findViewById(R.id.viewpage);
        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        ArrayList<Fragment> data = new ArrayList();
        data.add(new HomeFragment());
        data.add(new MyFragment());

        MyFragmentStatePagerAdapter myFragmentStatePagerAdapter = new
                MyFragmentStatePagerAdapter(getSupportFragmentManager(), data);
        viewPager.setAdapter(myFragmentStatePagerAdapter);
        viewPager.setOffscreenPageLimit(data.size());
        viewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                switch (position) {
                    case 0:
                        bottomNavigationView.setSelectedItemId(R.id.navigation_home);
                        break;
                    case 1:
                        bottomNavigationView.setSelectedItemId(R.id.navigation_dashboard);
                        break;
                }
            }
        });

    }
    private BroadcastReceiver mRefreshBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            System.out.println("action:"+action);
            if (action.equals("action.refreshMain")) {
                reFreshFrinedList();
            }
        }
    };


    public void myOrder(View view) {
        Intent intent = new Intent(this, MyOrderActivity.class);
        startActivity(intent);
    }



    private void reFreshFrinedList() {
        HomeFragment homeFragment = new HomeFragment();
        homeFragment.getDate();
    }

    //    public void yuyue(View view) {
//        Intent intent = new Intent(this, DateTimeActivity.class);
//        startActivity(intent);
//    }
    //销毁注册
    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mRefreshBroadcastReceiver);
    }
}
