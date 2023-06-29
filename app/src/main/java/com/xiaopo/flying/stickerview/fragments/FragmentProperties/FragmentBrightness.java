package com.xiaopo.flying.stickerview.fragments.FragmentProperties;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;

import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;
import androidx.fragment.app.FragmentManager;

import com.xiaopo.flying.stickerview.R;

import java.io.File;

import jp.co.cyberagent.android.gpuimage.GPUImage;
import jp.co.cyberagent.android.gpuimage.GPUImageView;
import jp.co.cyberagent.android.gpuimage.filter.GPUImageBrightnessFilter;

public class FragmentBrightness extends Fragment {

    Button cancel,accept;
    SeekBar intensity;
    ImageView imageView;
    GPUImageView image_edit;
    FragmentContainerView fragmentContainerView;
    CardView cardViewEdit;
    Bitmap bitmap;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_properties_brightness, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        cancel = getView().findViewById(R.id.cancel_brightness);
        accept = getView().findViewById(R.id.accept_brightness);
        intensity = getView().findViewById(R.id.intensity_brightness);
        imageView=getActivity().findViewById(R.id.Image_editing);
        fragmentContainerView=getActivity().findViewById(R.id.fragment_container_view);
        cardViewEdit = getActivity().findViewById(R.id.cardViewEdit);
    }

    @Override
    public void onResume() {
        super.onResume();
        bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
        cardViewEdit.removeAllViews();
        image_edit = new GPUImageView(getActivity());
        image_edit.setScaleType(GPUImage.ScaleType.CENTER_INSIDE);
        image_edit.setBackgroundColor(239, 239, 244);
        image_edit.setImage(bitmap);
        cardViewEdit.addView(image_edit);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetGPUimageView();
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().remove(FragmentBrightness.this).commit();
                fragmentContainerView.setVisibility(View.VISIBLE);
            }
        });
        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                image_edit.saveToPictures("temp", "editingPic.jpg", new GPUImageView.OnPictureSavedListener() {
                    @Override
                    public void onPictureSaved(Uri uri) {
                        bitmap = BitmapFactory.decodeFile("/storage/emulated/0/Pictures/temp/editingPic.jpg");
                        resetGPUimageView();
                        FragmentManager fragmentManager = getFragmentManager();
                        fragmentManager.beginTransaction().remove(FragmentBrightness.this).commit();
                        fragmentContainerView.setVisibility(View.VISIBLE);
                    }
                });
            }
        });
        intensity.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                image_edit.setFilter(new GPUImageBrightnessFilter((progress-50)/100.0f));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    public void resetGPUimageView(){
        cardViewEdit.removeAllViews();
        imageView = new ImageView(getActivity());
        imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        imageView.setId(R.id.Image_editing);
        imageView.setImageBitmap(bitmap);
        cardViewEdit.addView(imageView);
        File file = new File("/storage/emulated/0/Pictures/temp/editingPic.jpg");
        if (file.exists()) {
            file.delete();
        }
    }
}