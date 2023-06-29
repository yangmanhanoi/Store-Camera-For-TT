package com.xiaopo.flying.stickerview;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class AdapterViewPagerMain extends FragmentStatePagerAdapter {
    Fragment fragment_albums;
    Fragment fragment_template;
    public AdapterViewPagerMain(@NonNull FragmentManager fm) {
        super(fm);
        fragment_template = new TemplateFragment();
        fragment_albums = new AlbumsFragment();
    }
    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position)
        {
            case 0:
                return fragment_template;
            case 1:
                return fragment_albums;
            default:
                return  null;
        }
    }

    @Override
    public int getCount() {
        return 2;
    }
}
