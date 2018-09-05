package com.wzlab.smartcity.activity.main;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;

import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.wzlab.smartcity.R;
import com.wzlab.smartcity.activity.me.MeFragment;
import com.wzlab.smartcity.adapter.ViewPagerAdapter;
import com.wzlab.smartcity.widget.BottomNavMenuBar;
import com.wzlab.smartcity.widget.NoScrollViewPager;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private NoScrollViewPager mVpMainContainer;
    private Toolbar toolbar;
    private String[] text;
    private BottomNavMenuBar mBottomNavMenuBar;
    private double lat;
    private double lon;
    private String from;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = getWindow();
        //透明状态栏
        // window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        //透明导航栏
        //window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        // window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        // window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(getResources().getColor(R.color.colorPrimary));

        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(null);
        toolbar.setTitle("首页");
        setSupportActionBar(toolbar);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        DrawerLayout drawer = findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        mVpMainContainer = findViewById(R.id.vp_main_container);
        ArrayList<Fragment> mFragmentList = new ArrayList<>();
        Fragment tabViewFragment = new TabViewFragment();
        Fragment alarmFragment = new AlarmFragment();
        Fragment meFragment = new MeFragment();

        mFragmentList.add(tabViewFragment);
        mFragmentList.add(alarmFragment);
        mFragmentList.add(meFragment);
        ViewPagerAdapter mViewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), mFragmentList);
        mVpMainContainer.setAdapter(mViewPagerAdapter);




        //设置底部的导航菜单
        int[] iconNormal = {R.drawable.ic_bottom_nav_bar_home,R.drawable.ic_bottom_nav_bar_alarm,R.drawable.ic_bottom_nav_bar_me};
        int[] iconFocus = {R.drawable.ic_bottom_nav_bar_home_focus,R.drawable.ic_bottom_nav_bar_alarm_focus,R.drawable.ic_bottom_nav_bar_me_focus};
        text = new String[]{"首页","报警","我的"};
        mBottomNavMenuBar = findViewById(R.id.bottom_nav_menu_bar);
        mBottomNavMenuBar.setIconRes(iconNormal)
                .setIconResSelected(iconFocus)
                .setTextRes(text)
                .setSelected(0);
        mBottomNavMenuBar.setOnItemSelectedListener(new BottomNavMenuBar.OnItemSelectedListener() {
            @Override
            public void onItemSelected(int position) {
                mVpMainContainer.setCurrentItem(position);
                toolbar.setTitle(text[position]);

            }
        });

        mBottomNavMenuBar.setOnItemReSelectedListener(new BottomNavMenuBar.OnItemReSelectedListener() {
            @Override
            public void onItemReSelected(int position) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    public double getLat(){
        return lat;
    }
    public double getLon(){
        return lon;
    }
    public String getFrom(){
        return from;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        from = intent.getStringExtra("from");
        if(from.equals("alarmTask")){
            lat = intent.getDoubleExtra("lat",0);
            lon = intent.getDoubleExtra("lon",0);
            mVpMainContainer.setCurrentItem(1);
            toolbar.setTitle(text[1]);
            mBottomNavMenuBar.setSelected(1);

        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        return true;
    }
}
