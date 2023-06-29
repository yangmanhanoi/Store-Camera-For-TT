package com.xiaopo.flying.stickerview.fragments.FragmentCrop;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;
import androidx.fragment.app.FragmentManager;

import com.xiaopo.flying.stickerview.R;


public class FragmentCropRotate extends Fragment {

    Button accept, cancel, rotateLeft, rotateRight;
    ImageView imageView;
    FragmentContainerView fragmentContainerView;
    Bitmap bitmap1, bitmap2;
    Matrix matrix1,matrix2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_crop_rotate, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        accept = getView().findViewById(R.id.accept_rotate);
        cancel = getView().findViewById(R.id.cancel_rotate);
        rotateLeft = getView().findViewById(R.id.rotate_left);
        rotateRight = getView().findViewById(R.id.rotate_right);
        fragmentContainerView = getActivity().findViewById(R.id.fragment_container_view);
        imageView = getActivity().findViewById(R.id.Image_editing);
        imageView.setClipToOutline(true);
    }

    @Override
    public void onResume() {
        super.onResume();
        FragmentManager fragmentManager = getFragmentManager();
        bitmap1 = ((BitmapDrawable)imageView.getDrawable()).getBitmap();
        bitmap2 = bitmap1;
        matrix1 = new Matrix();
        matrix1.postRotate(90);
        matrix2 = new Matrix();
        matrix2.postRotate(-90);
        imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentManager.beginTransaction().remove(FragmentCropRotate.this).commit();
                fragmentContainerView.setVisibility(View.VISIBLE);
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentManager.beginTransaction().remove(FragmentCropRotate.this).commit();
                imageView.setImageBitmap(bitmap1);
                fragmentContainerView.setVisibility(View.VISIBLE);
            }
        });
        rotateLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bitmap2 = Bitmap.createBitmap(bitmap2, 0, 0, bitmap2.getWidth(), bitmap2.getHeight(), matrix2, false);
                imageView.setImageBitmap(bitmap2);

            }
        });
        rotateRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bitmap2 = Bitmap.createBitmap(bitmap2, 0, 0, bitmap2.getWidth(), bitmap2.getHeight(), matrix1, false);
                imageView.setImageBitmap(bitmap2);
            }
        });
    }
}