package com.xiaopo.flying.stickerview.fragments.FragmentProperties;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;
import androidx.fragment.app.FragmentManager;

import com.xiaopo.flying.stickerview.R;


public class FragmentPropertiesMain extends Fragment {

    RelativeLayout whiteBalance, partial, hd, brightness, exposure;
    FragmentManager fragmentManager;
    FragmentContainerView fragmentContainerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_properties, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        whiteBalance = getView().findViewById(R.id.white_balance);
        partial = getView().findViewById(R.id.partial_color);
        hd = getView().findViewById(R.id.hd);
        exposure = getView().findViewById(R.id.exposure);
        brightness = getView().findViewById(R.id.brightness);
        fragmentContainerView=getActivity().findViewById(R.id.fragment_container_view);
    }

    @Override
    public void onResume() {
        super.onResume();
        fragmentManager = getFragmentManager();
        whiteBalance.setOnClickListener(this::setWhiteBalance);
        partial.setOnClickListener(this::setPartial);
        hd.setOnClickListener(this::setHd);
        exposure.setOnClickListener(this::setExposure);
        brightness.setOnClickListener(this::setBrightness);
    }

    void setWhiteBalance(View v) {
        FragmentWhiteBalance fragmentWhiteBalance = new FragmentWhiteBalance();
        fragmentManager.beginTransaction().replace(R.id.advanced_setting, fragmentWhiteBalance).commit();
        fragmentContainerView.setVisibility(View.GONE);
    }
    void setPartial(View v){
        FragmentPartialColor fragmentPartialColor = new FragmentPartialColor();
        fragmentManager.beginTransaction().replace(R.id.advanced_setting, fragmentPartialColor).commit();
        fragmentContainerView.setVisibility(View.GONE);
    }

    void setHd(View v) {
        FragmentHD fragmentHD = new FragmentHD();
        fragmentManager.beginTransaction().replace(R.id.advanced_setting, fragmentHD).commit();
        fragmentContainerView.setVisibility(View.GONE);
    }

    void setExposure(View v){
        FragmentExposure fragmentExposure = new FragmentExposure();
        fragmentManager.beginTransaction().replace(R.id.advanced_setting, fragmentExposure).commit();
        fragmentContainerView.setVisibility(View.GONE);
    }

    void setBrightness(View v){
        FragmentBrightness fragmentBrightness = new FragmentBrightness();
        fragmentManager.beginTransaction().replace(R.id.advanced_setting, fragmentBrightness).commit();
        fragmentContainerView.setVisibility(View.GONE);
    }
}