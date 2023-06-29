package com.xiaopo.flying.stickerview.fragments.FragmentBackground;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;
import androidx.fragment.app.FragmentManager;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.segmentation.Segmentation;
import com.google.mlkit.vision.segmentation.SegmentationMask;
import com.google.mlkit.vision.segmentation.Segmenter;
import com.google.mlkit.vision.segmentation.selfie.SelfieSegmenterOptions;
import com.xiaopo.flying.stickerview.R;

import java.nio.ByteBuffer;



public class FragmentBgColor extends Fragment implements View.OnClickListener{

    ImageView imageView, transparent,color1,color2,color3, color4, color5, color6, color7, color8, color9;
    Bitmap bitmap,bitmap0;
    FragmentContainerView fragmentContainerView;
    Button accept,cancel;
    TextView progress;
    CardView cardViewEdit;
    SurfaceView surfaceView;
    LinearLayout linearLayout;
    boolean transparentBG = false;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_background_bg_color, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        imageView = getActivity().findViewById(R.id.Image_editing);
        fragmentContainerView = getActivity().findViewById(R.id.fragment_container_view);
        cancel = getView().findViewById(R.id.cancel_backgroundColor);
        accept = getView().findViewById(R.id.accept_backgroundColor);
        progress = getView().findViewById(R.id.textView_backgroundColor);
        cardViewEdit = getActivity().findViewById(R.id.cardViewEdit);
        linearLayout = getView().findViewById(R.id.colorBackground);
        transparent = getView().findViewById(R.id.transparent);
        color1 = getView().findViewById(R.id.BC1);
        color2 = getView().findViewById(R.id.BC2);
        color3 = getView().findViewById(R.id.BC3);
        color4 = getView().findViewById(R.id.BC4);
        color5 = getView().findViewById(R.id.BC5);
        color6 = getView().findViewById(R.id.BC6);
        color7 = getView().findViewById(R.id.BC7);
        color8 = getView().findViewById(R.id.BC8);
    }

    @Override
    public void onResume() {
        super.onResume();
        bitmap0 = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
        bitmap = bitmap0;
        surfaceView = new SurfaceView(getActivity());
        surfaceView.setBackgroundColor(Color.BLACK);
        SelfieSegmenterOptions options = new SelfieSegmenterOptions.Builder()
                .setDetectorMode(SelfieSegmenterOptions.SINGLE_IMAGE_MODE).build();
        Segmenter segmenter = Segmentation.getClient(options);
        InputImage image = InputImage.fromBitmap(bitmap,0);
        Task<SegmentationMask> result = segmenter.process(image).addOnSuccessListener(new OnSuccessListener<SegmentationMask>() {
            @Override
            public void onSuccess(SegmentationMask segmentationMask) {
                ByteBuffer byteBuffer = segmentationMask.getBuffer();
                int width = segmentationMask.getWidth();
                int height = segmentationMask.getHeight();
                Bitmap bitmap1 = Bitmap.createBitmap(bitmap.getWidth(),bitmap.getHeight(),bitmap.getConfig());
                for (int i=0;i<height;i++){
                    for (int j=0;j<width;j++){
                        double x = byteBuffer.getFloat();
                        Double.isNaN(x);
                        if (x>bitmap.getWidth()) x=bitmap.getWidth();
                        bitmap1.setPixel(j,i, Color.argb((int) ((1.0 - x) * 255.0),0,0,0));
                    }
                }
                byteBuffer.rewind();
                bitmap = mergeToPinBitmap(bitmap,bitmap1);
                progress.setVisibility(View.GONE);
                imageView.setImageBitmap(bitmap);
                cardViewEdit.removeAllViews();
                cardViewEdit.addView(surfaceView);
                cardViewEdit.addView(imageView);
                accept.setVisibility(View.VISIBLE);
                linearLayout.setVisibility(View.VISIBLE);
            }
        });

        transparent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                transparentBG = true;
                surfaceView.setBackground(getResources().getDrawable(R.drawable.blank_background));
            }
        });
        color1.setOnClickListener(this::onClick);
        color2.setOnClickListener(this::onClick);
        color3.setOnClickListener(this::onClick);
        color4.setOnClickListener(this::onClick);
        color5.setOnClickListener(this::onClick);
        color6.setOnClickListener(this::onClick);
        color7.setOnClickListener(this::onClick);
        color8.setOnClickListener(this::onClick);

        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (transparentBG==false){
                    bitmap = replaceColor(bitmap0, Color.TRANSPARENT, surfaceView.getSolidColor());
                }
                imageView.setImageBitmap(bitmap);
                cardViewEdit.removeView(surfaceView);
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().remove(FragmentBgColor.this).commit();
                fragmentContainerView.setVisibility(View.VISIBLE);
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cardViewEdit.removeView(surfaceView);
                imageView.setImageBitmap(bitmap0);
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().remove(FragmentBgColor.this).commit();
                fragmentContainerView.setVisibility(View.VISIBLE);
            }
        });
    }

    Bitmap mergeToPinBitmap(Bitmap input1, Bitmap input2){
        Bitmap result = Bitmap.createBitmap(input2.getWidth(),input2.getHeight(),Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(result);
        Paint paint = new Paint(1);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));
        canvas.drawBitmap(input1,0,0,null);
        canvas.drawBitmap(input2,0,0,paint);
        paint.setXfermode(null);
        return result;
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


    @Override
    public void onClick(View v) {
        transparentBG = false;
        ColorDrawable colorDrawable = (ColorDrawable) v.getBackground();
        surfaceView.setBackgroundColor(colorDrawable.getColor());
    }
}