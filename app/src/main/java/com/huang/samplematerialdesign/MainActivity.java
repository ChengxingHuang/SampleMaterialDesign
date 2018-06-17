package com.huang.samplematerialdesign;

import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity implements CategoryFragment.BackHandlerInterface, ListFragment.BackHandlerInterface{

    private MainViewPagerAdapter mMainViewPagerAdapter;
    private CategoryFragment mCategoryFragment;
    private ListFragment mListFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawerLayout = findViewById(R.id.activity_main);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, 0, 0);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.navigation_view);
        navigationView.setItemIconTintList(null);

        TabLayout tabLayout = findViewById(R.id.tablayout);
        ViewPager viewPager = findViewById(R.id.viewpager);

        mMainViewPagerAdapter = new MainViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(mMainViewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //将指定menu绑定到toolbar
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);

        //在menu中指定class只能通过这种方式拿到
        MenuItem searchMenuItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchMenuItem);
        searchView.setQueryHint("aaaa");

        return true;
    }

    @Override
    public void onBackPressed() {
        boolean isFragmentHandled = false;
        int position = mMainViewPagerAdapter.getCurrentPosition();

        if((mCategoryFragment != null && position == MainViewPagerAdapter.CATEGORY_FRAGMENT_POSITION)) {
            isFragmentHandled = mCategoryFragment.onBackPressed();
        }else if((mListFragment != null && position == MainViewPagerAdapter.LIST_FRAGMENT_POSITION)) {
            isFragmentHandled = mListFragment.onBackPressed();
        }

        if(!isFragmentHandled)
            super.onBackPressed();
    }

    @Override
    public void setCategoryFragment(CategoryFragment fragment) {
        mCategoryFragment = fragment;
    }

    @Override
    public void setListFragment(ListFragment fragment) {
        mListFragment = fragment;
    }
}
