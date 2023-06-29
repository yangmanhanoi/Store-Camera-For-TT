package com.xiaopo.flying.stickerview.fragments.FragmentCrop;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;
import androidx.fragment.app.FragmentManager;

import com.theartofdev.edmodo.cropper.CropImageView;
import com.xiaopo.flying.stickerview.R;


public class FragmentCropRatio extends Fragment {

    Button cancel,crop169,crop43,crop11,crop34,crop916,accept;
    CropImageView cropImageView;
    Bitmap bitmap;
    TextView path;
    ImageView imageView;
    FragmentContainerView fragmentContainerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_crop_ratio, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        cancel= getView().findViewById(R.id.cancel_ratio);
        crop169 = getView().findViewById(R.id.crop169);
        crop43 = getView().findViewById(R.id.crop43);
        crop11 = getView().findViewById(R.id.crop11);
        crop34 = getView().findViewById(R.id.crop34);
        crop916 = getView().findViewById(R.id.crop916);
        accept = getView().findViewById(R.id.accept_ratio);
        cropImageView = getActivity().findViewById(R.id.cropImageView);
        path = getActivity().findViewById(R.id.imagePath);
        imageView = getActivity().findViewById(R.id.Image_editing);
        fragmentContainerView = getActivity().findViewById(R.id.fragment_container_view);
    }

    @Override
    public void onResume() {
        super.onResume();
        bitmap = ((BitmapDrawable)imageView.getDrawable()).getBitmap();
        cropImageView.setImageBitmap(bitmap);
        cropImageView.setVisibility(View.VISIBLE);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cropImageView.setVisibility(View.GONE);
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().remove(FragmentCropRatio.this).commit();
                fragmentContainerView.setVisibility(View.VISIBLE);
                imageView.setVisibility(View.VISIBLE);
            }
        });
        crop169.setOnClickListener(this::crop_ratio);
        crop43.setOnClickListener(this::crop_ratio);
        crop11.setOnClickListener(this::crop_ratio);
        crop34.setOnClickListener(this::crop_ratio);
        crop916.setOnClickListener(this::crop_ratio);

        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cropImageView.setVisibility(View.GONE);
                bitmap = cropImageView.getCroppedImage();
                imageView.setImageBitmap(bitmap);
                imageView.setVisibility(View.VISIBLE);
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().remove(FragmentCropRatio.this).commit();
                fragmentContainerView.setVisibility(View.VISIBLE);
            }
        });
    }

    private void crop_ratio(View v){
        cropImageView.setAutoZoomEnabled(true);
        switch (v.getId()){
            case R.id.crop169:
                cropImageView.setAspectRatio(9,16);
                break;
            case R.id.crop43:
                cropImageView.setAspectRatio(3,4);
                break;
            case R.id.crop11:
                cropImageView.setAspectRatio(1,1);
                break;
            case R.id.crop34:
                cropImageView.setAspectRatio(4,3);
                break;
            case R.id.crop916:
                cropImageView.setAspectRatio(16,9);
                break;
            default:
                return;
        }
    }
}
