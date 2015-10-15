package com.mychess.mychess;

import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by Carlos on 14/10/2015.
 */
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

public class PagerAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    public PagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                LoginFragment loginFragment = new LoginFragment();
                return loginFragment;
            case 1:
                RegistroFragment registroFragment = new RegistroFragment();
                return registroFragment;

            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}