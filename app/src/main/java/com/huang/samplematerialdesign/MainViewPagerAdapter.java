package com.huang.samplematerialdesign;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by huang on 2018/5/30.
 */

public class MainViewPagerAdapter extends FragmentPagerAdapter {

    public static final int CATEGORY_FRAGMENT_POSITION = 0x00;
    public static final int LIST_FRAGMENT_POSITION = 0x01;
    private int mCurrentPosition;

    public MainViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if(0 == position){
            return "Category";
        }else {
            return "List";
        }
    }

    @Override
    public Fragment getItem(int position) {
        if(0 == position) {
            Log.d("huangcx", "position");
            return new CategoryFragment();
        }else {
            return new ListFragment();
        }
    }

    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        super.setPrimaryItem(container, position, object);
        mCurrentPosition = position;
    }

    @Override
    public int getCount() {
        return 2;
    }

    public int getCurrentPosition(){
        return mCurrentPosition;
    }
}
