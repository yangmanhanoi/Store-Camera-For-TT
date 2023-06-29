package com.xiaopo.flying.stickerview;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class AdapterViewPagerAlbum extends FragmentStatePagerAdapter {
    Fragment fragment_photos, fragment_myproject;

    public AdapterViewPagerAlbum(@NonNull FragmentManager fm, int behaviorResumeOnlyCurrentFragment) {
        super(fm);
        fragment_photos = new Photosfragment();
        fragment_myproject = new MyProjectFragment();
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position)
        {
            case 0:
                return fragment_photos;
            case 1:
                return fragment_myproject;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        String title = "";
        switch (position)
        {
            case 0:
                title = "Photos";
                break;
            case 1:
                title = "My projects";
                break;
            default: break;

        }
        return title;
    }
}
