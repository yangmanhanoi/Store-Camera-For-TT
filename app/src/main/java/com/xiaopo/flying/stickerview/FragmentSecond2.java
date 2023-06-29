package com.xiaopo.flying.stickerview;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class FragmentSecond2 extends Fragment {
    public static FragmentSecond2 newInstance(){
        return new FragmentSecond2();
    }
    public ImageView btn_save;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_second2, container, false);
        return v;
    }
    private void initFragment()
    {

    }
}
