package com.xiaopo.flying.stickerview.fragments.FragmentCrop;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.theartofdev.edmodo.cropper.CropImageView;
import com.xiaopo.flying.stickerview.R;

public class FragmentCropMain extends Fragment {

    RelativeLayout but_ratio, but_flip, but_rotate;
    CropImageView cropImageView;
    FragmentContainerView fragmentContainerView;
    Bitmap bitmap;
    ImageView imageView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_crop, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        but_ratio = getView().findViewById(R.id.ratio_edit);
        but_flip = getView().findViewById(R.id.flip_edit);
        but_rotate = getView().findViewById(R.id.rotate_edit);
        cropImageView = getActivity().findViewById(R.id.cropImageView);
        cropImageView.setAutoZoomEnabled(true);
        fragmentContainerView = getActivity().findViewById(R.id.fragment_container_view);
        imageView = getActivity().findViewById(R.id.Image_editing);
    }

    @Override
    public void onResume() {
        super.onResume();
        but_ratio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentCropRatio fragmentCropRatio = new FragmentCropRatio();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction().setCustomAnimations(android.R.anim.fade_in,android.R.anim.fade_out);
                fragmentTransaction.replace(R.id.advanced_setting, fragmentCropRatio).commit();
                imageView.setVisibility(View.GONE);
                fragmentContainerView.setVisibility(View.GONE);
            }
        });
        but_flip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentCropFlip fragmentCropFlip = new FragmentCropFlip();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction().setCustomAnimations(android.R.anim.fade_in,android.R.anim.fade_out);
                fragmentTransaction.replace(R.id.advanced_setting, fragmentCropFlip).commit();
                fragmentContainerView.setVisibility(View.GONE);
            }
        });
        but_rotate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentCropRotate fragmentCropRotate = new FragmentCropRotate();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction().setCustomAnimations(android.R.anim.fade_in,android.R.anim.fade_out);
                fragmentTransaction.replace(R.id.advanced_setting, fragmentCropRotate).commit();
                fragmentContainerView.setVisibility(View.GONE);
            }
        });
    }
}