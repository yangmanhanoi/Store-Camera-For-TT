package com.xiaopo.flying.stickerview.fragments.FragmentBackground;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;
import androidx.fragment.app.FragmentManager;

import com.rtugeek.android.colorseekbar.AlphaSeekBar;
import com.rtugeek.android.colorseekbar.OnAlphaChangeListener;
import com.xiaopo.flying.stickerview.R;

import java.io.File;

import jp.co.cyberagent.android.gpuimage.GPUImageView;
import jp.co.cyberagent.android.gpuimage.filter.GPUImageBrightnessFilter;


public class FragmentPartialBrightness extends Fragment {

    Button cancel,accept;
    Bitmap bitmap,image;
    TextView textView;
    AlphaSeekBar alphaSeekBar;
    CardView cardViewEdit;
    int colorChoose;
    int[] pixels;
    FragmentContainerView fragmentContainerView;
    ImageView imageView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_background_partial_brightness, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        cancel = getView().findViewById(R.id.cancel_partialBrightness);
        accept = getView().findViewById(R.id.accept_partialBrightness);
        imageView = getActivity().findViewById(R.id.Image_editing);
        alphaSeekBar = getView().findViewById(R.id.seekBar_partialBrightness);
        cardViewEdit = getActivity().findViewById(R.id.cardViewEdit);
        textView = getView().findViewById(R.id.textView_partialBrightness);
        fragmentContainerView=getActivity().findViewById(R.id.fragment_container_view);
    }

    @Override
    public void onResume() {
        super.onResume();
        GPUImageView surfaceView = new GPUImageView(getActivity());
        bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
        cardViewEdit.removeView(imageView);
        surfaceView.setBackgroundColor(Color.RED);
        cardViewEdit.addView(surfaceView);
        cardViewEdit.addView(imageView);
        imageView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int viewX = (int) event.getX();
                int viewY = (int) event.getY();
                int viewWidth = imageView.getWidth();
                int viewHeight = imageView.getHeight();
                image = ((BitmapDrawable)imageView.getDrawable()).getBitmap();
                int imageWidth = image.getWidth();
                int imageHeight = image.getHeight();
                int imageX = (int)((float)viewX * ((float)imageWidth / (float)viewWidth));
                int imageY = (int)((float)viewY * ((float)imageHeight / (float)viewHeight));
                int currPixel = image.getPixel(imageX, imageY);
                colorChoose = Color.rgb(Color.red(currPixel),Color.green(currPixel),Color.blue(currPixel));
                surfaceView.setBackgroundColor(colorChoose);
                surfaceView.setFilter(new GPUImageBrightnessFilter(0.25f));
                image = replaceColor(image,colorChoose,Color.TRANSPARENT);
                imageView.setImageBitmap(image);
                textView.setText("Brightness");
                textView.setGravity(Gravity.LEFT);
                alphaSeekBar.setVisibility(View.VISIBLE);
                accept.setVisibility(View.VISIBLE);
                imageView.setClickable(false);
                return false;
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetGPUimageView();
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().remove(FragmentPartialBrightness.this).commit();
                fragmentContainerView.setVisibility(View.VISIBLE);
            }
        });
        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                surfaceView.saveToPictures("temp", "editingPic.jpg", new GPUImageView.OnPictureSavedListener() {
                    @Override
                    public void onPictureSaved(Uri uri) {
                        Bitmap bm = BitmapFactory.decodeFile("/storage/emulated/0/Pictures/temp/editingPic.jpg");
                        int pixel = bm.getPixel(bm.getWidth()/2,bm.getHeight()/2);
                        colorChoose = Color.rgb(Color.red(pixel),Color.green(pixel),Color.blue(pixel));
                        bitmap = replaceColor(image, Color.TRANSPARENT,colorChoose);
                        resetGPUimageView();
                        fragmentContainerView.setVisibility(View.VISIBLE);
                        FragmentManager fragmentManager = getFragmentManager();
                        fragmentManager.beginTransaction().remove(FragmentPartialBrightness.this).commit();
                    }
                });
            }
        });
        alphaSeekBar.setOnAlphaChangeListener(new OnAlphaChangeListener() {
            @Override
            public void onAlphaChangeListener(int progress, int alpha) {
                surfaceView.setFilter(new GPUImageBrightnessFilter((progress-12)/100.0f));
            }
        });
    }

    public Bitmap replaceColor(Bitmap src,int fromColor, int targetColor) {
        if(src == null) {
            return null;
        }
        int width = src.getWidth();
        int height = src.getHeight();
        int[] pixels = new int[width * height];
        src.getPixels(pixels, 0, width, 0, 0, width, height);

        for(int x = 0; x < pixels.length; ++x) {
            pixels[x] = (fromColor-2000 <= pixels[x] & pixels[x] <= fromColor+2000) ? targetColor : pixels[x];
        }
        Bitmap result = Bitmap.createBitmap(width, height, src.getConfig());
        result.setPixels(pixels, 0, width, 0, 0, width, height);
        return result;
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