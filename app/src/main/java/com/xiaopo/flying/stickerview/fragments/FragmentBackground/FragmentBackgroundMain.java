package com.xiaopo.flying.stickerview.fragments.FragmentBackground;

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


public class FragmentBackgroundMain extends Fragment {

    RelativeLayout removeBg, bgColor, erase, shadow, edgeBlur, partialBrightness;
    FragmentManager fragmentManager;
    FragmentContainerView fragmentContainerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_background, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        removeBg = getView().findViewById(R.id.remove_bg);
        bgColor = getView().findViewById(R.id.bg_color);
        erase = getView().findViewById(R.id.erase);
        shadow = getView().findViewById(R.id.shadow);
        edgeBlur = getView().findViewById(R.id.edge_blur);
        partialBrightness = getView().findViewById(R.id.partial_brightness);
        fragmentContainerView = getActivity().findViewById(R.id.fragment_container_view);
    }

    @Override
    public void onResume() {
        super.onResume();
        fragmentManager = getFragmentManager();
        removeBg.setOnClickListener(this::removeBg);
        bgColor.setOnClickListener(this::bgColor);
        erase.setOnClickListener(this::erase);
        shadow.setOnClickListener(this::shadow);
        edgeBlur.setOnClickListener(this::edgeBlur);
        partialBrightness.setOnClickListener(this::partialBrightness);
    }

    private void removeBg(View view) {
        FragmentRemoveBg fragmentRemoveBg = new FragmentRemoveBg();
        fragmentManager.beginTransaction().replace(R.id.advanced_setting, fragmentRemoveBg).commit();
        fragmentContainerView.setVisibility(View.GONE);
    }

    private void bgColor(View view) {
        FragmentBgColor fragmentBgColor = new FragmentBgColor();
        fragmentManager.beginTransaction().replace(R.id.advanced_setting, fragmentBgColor).commit();
        fragmentContainerView.setVisibility(View.GONE);
    }

    private void erase(View view) {
        FragmentErase fragmentErase = new FragmentErase();
        fragmentManager.beginTransaction().replace(R.id.advanced_setting, fragmentErase).commit();
        fragmentContainerView.setVisibility(View.GONE);
    }

    private void shadow(View view) {
        FragmentShadow fragmentShadow = new FragmentShadow();
        fragmentManager.beginTransaction().replace(R.id.advanced_setting, fragmentShadow).commit();
        fragmentContainerView.setVisibility(View.GONE);
    }

    private void edgeBlur(View view) {
        FragmentEdgeBlur fragmentEdgeBlur = new FragmentEdgeBlur();
        fragmentManager.beginTransaction().replace(R.id.advanced_setting, fragmentEdgeBlur).commit();
        fragmentContainerView.setVisibility(View.GONE);
    }

    private void partialBrightness(View view) {
        FragmentPartialBrightness fragmentPartialBrightness = new FragmentPartialBrightness();
        fragmentManager.beginTransaction().replace(R.id.advanced_setting, fragmentPartialBrightness).commit();
        fragmentContainerView.setVisibility(View.GONE);
    }
}