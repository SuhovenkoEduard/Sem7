package com.example.labaratornaya_1.fragments;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class ViewPagerAdapter extends FragmentStateAdapter {

    public ViewPagerAdapter(FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = new FragmentOne();
                break;
            case 1:
                fragment = FragmentTwo.newInstance("Train wasn't select");
                break;
        }
        return fragment;
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
