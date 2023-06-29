package com.xiaopo.flying.stickerview.fragments.FragmentCrop;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;
import androidx.fragment.app.FragmentManager;

import com.xiaopo.flying.stickerview.R;


public class FragmentCropFlip extends Fragment {

    Button accept, cancel, flipVertical, flipHorizontal;
    ImageView imageView;
    FragmentContainerView fragmentContainerView;
    Boolean stateVertical,stateHorizontal;
    Bitmap bitmap1, bitmap2;
    Matrix matrix1, matrix2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_crop_flip, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        accept = getView().findViewById(R.id.accept_flip);
        cancel = getView().findViewById(R.id.cancel_flip);
        flipVertical = getView().findViewById(R.id.flip_vertical);
        flipHorizontal = getView().findViewById(R.id.flip_horizontal);
        fragmentContainerView = getActivity().findViewById(R.id.fragment_container_view);
        imageView = getActivity().findViewById(R.id.Image_editing);
    }

    @Override
    public void onResume() {
        super.onResume();
        FragmentManager fragmentManager = getFragmentManager();
        bitmap1 = ((BitmapDrawable)imageView.getDrawable()).getBitmap();
        bitmap2 = bitmap1;
        matrix1 = new Matrix();
        matrix1.postScale(-1, 1, bitmap2.getWidth()/2, bitmap2.getHeight()/2);
        matrix2 = new Matrix();
        matrix2.postScale(1, -1, bitmap2.getWidth()/2, bitmap2.getHeight()/2);
        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentManager.beginTransaction().remove(FragmentCropFlip.this).commit();
                fragmentContainerView.setVisibility(View.VISIBLE);
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentManager.beginTransaction().remove(FragmentCropFlip.this).commit();
                imageView.setVisibility(View.VISIBLE);
                fragmentContainerView.setVisibility(View.VISIBLE);
                imageView.setImageBitmap(bitmap1);
            }
        });
        flipVertical.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bitmap2 = Bitmap.createBitmap(bitmap2, 0, 0, bitmap2.getWidth(), bitmap2.getHeight(), matrix1, false);
                imageView.setImageBitmap(bitmap2);
            }
        });
        flipHorizontal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bitmap2 = Bitmap.createBitmap(bitmap2, 0, 0, bitmap2.getWidth(), bitmap2.getHeight(), matrix2, false);
                imageView.setImageBitmap(bitmap2);
            }
        });
    }

}