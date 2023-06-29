package com.xiaopo.flying.stickerview.fragments.FragmentProperties;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;
import androidx.fragment.app.FragmentManager;

import com.rtugeek.android.colorseekbar.ColorSeekBar;
import com.rtugeek.android.colorseekbar.OnColorChangeListener;
import com.xiaopo.flying.stickerview.R;

import java.io.File;


public class FragmentPartialColor extends Fragment {

    Button cancel,accept;
    Bitmap bitmap,image;
    TextView textView;
    ColorSeekBar colorSeekBar;
    CardView cardViewEdit;
    int colorChoose;
    FragmentContainerView fragmentContainerView;
    ImageView imageView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_properties_partial_color, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        cancel = getView().findViewById(R.id.cancel_partial);
        accept = getView().findViewById(R.id.accept_partial);
        imageView = getActivity().findViewById(R.id.Image_editing);
        colorSeekBar = getView().findViewById(R.id.seekBar_partialColor);
        cardViewEdit = getActivity().findViewById(R.id.cardViewEdit);
        textView = getView().findViewById(R.id.textView_partialColor);
        fragmentContainerView=getActivity().findViewById(R.id.fragment_container_view);
    }

    @Override
    public void onResume() {
        super.onResume();
        SurfaceView surfaceView = new SurfaceView(getActivity());
        bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
        cardViewEdit.removeAllViews();
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        surfaceView.setBackgroundColor(Color.RED);
        cardViewEdit.addView(surfaceView);
        cardViewEdit.addView(imageView);
        imageView.setOnTouchListener(new View.OnTouchListener() {
            @SuppressLint("ClickableViewAccessibility")
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
                image = replaceColor(image,colorChoose,Color.TRANSPARENT);
                imageView.setImageBitmap(image);
                textView.setText("Color");
                textView.setGravity(Gravity.LEFT);
                colorSeekBar.setVisibility(View.VISIBLE);
                accept.setVisibility(View.VISIBLE);
                imageView.setClickable(false);
                return false;
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().remove(FragmentPartialColor.this).commit();
                resetGPUimageView();
                fragmentContainerView.setVisibility(View.VISIBLE);
            }
        });
        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().remove(FragmentPartialColor.this).commit();
                bitmap = replaceColor(image, Color.TRANSPARENT, colorSeekBar.getColor());
                resetGPUimageView();
                fragmentContainerView.setVisibility(View.VISIBLE);
            }
        });
        colorSeekBar.setOnColorChangeListener(new OnColorChangeListener() {
            @Override
            public void onColorChangeListener(int progress, int color) {
                surfaceView.setBackgroundColor(color);
            }
        });
    }

    public Bitmap replaceColor(Bitmap src,int fromColor, int targetColor) {
        if(src == null) {
            return null;
        }
        // Source image size
        int width = src.getWidth();
        int height = src.getHeight();
        int[] pixels = new int[width * height];
        //get pixels
        src.getPixels(pixels, 0, width, 0, 0, width, height);

        for(int x = 0; x < pixels.length; ++x) {
            pixels[x] = (fromColor-2000 <= pixels[x] & pixels[x] <= fromColor+2000) ? targetColor : pixels[x];
        }
        // create result bitmap output
        Bitmap result = Bitmap.createBitmap(width, height, src.getConfig());
        //set pixels
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