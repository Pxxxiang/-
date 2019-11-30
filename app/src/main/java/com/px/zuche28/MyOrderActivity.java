package com.px.zuche28;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.px.zuche28.Fragment.AllFragment;
import com.px.zuche28.Fragment.EndFragment;
import com.px.zuche28.Fragment.MyViewPage;
import com.px.zuche28.Adapter.ViewPagerAdapter;
import com.px.zuche28.Fragment.YuYueingFragment;

import java.util.ArrayList;


public class MyOrderActivity extends AppCompatActivity  {

    private MyViewPage viewPager;
    private TabLayout tabLayout;
    private Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.myorder_page);

//        TableLayout tableLayout = findViewById(R.id.tabs);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar supportActionBar = getSupportActionBar();
        supportActionBar.setDisplayHomeAsUpEnabled(true);

        supportActionBar.setDisplayShowTitleEnabled(false);

        viewPager = findViewById(R.id.viewpage2);
        ArrayList<Fragment> fragmentsdata = new ArrayList();
        fragmentsdata.add(new AllFragment());
        fragmentsdata.add(new YuYueingFragment());
        fragmentsdata.add(new EndFragment());

        ArrayList<String> titles = new ArrayList<>();
        titles.add("全部");
        titles.add("预约中");
        titles.add("已结束");

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(),fragmentsdata,titles);

        viewPager.setAdapter(viewPagerAdapter);
        viewPager.setOffscreenPageLimit(fragmentsdata.size());

        tabLayout=findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        if (id==android.R.id.home){
            finish();
        }
        return true;
    }
}
