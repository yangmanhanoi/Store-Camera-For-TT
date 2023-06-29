package com.xiaopo.flying.stickerview.fragments.FragmentBackground;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
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

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.segmentation.Segmentation;
import com.google.mlkit.vision.segmentation.SegmentationMask;
import com.google.mlkit.vision.segmentation.Segmenter;
import com.google.mlkit.vision.segmentation.selfie.SelfieSegmenterOptions;
import com.xiaopo.flying.stickerview.R;

import java.nio.ByteBuffer;



public class FragmentRemoveBg extends Fragment {

    ImageView imageView;
    Bitmap bitmap,bitmap0;
    FragmentContainerView fragmentContainerView;
    Button accept,cancel;
    TextView progress;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_background_remove_bg, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        imageView = getActivity().findViewById(R.id.Image_editing);
        fragmentContainerView = getActivity().findViewById(R.id.fragment_container_view);
        cancel = getView().findViewById(R.id.cancel_removeBg);
        accept = getView().findViewById(R.id.accept_removeBg);
        progress = getView().findViewById(R.id.progress_removeBg);
    }

    @Override
    public void onResume() {
        super.onResume();
        bitmap = ((BitmapDrawable)imageView.getDrawable()).getBitmap();
        bitmap0 = bitmap;
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
                        bitmap1.setPixel(j,i,Color.argb((int) ((1.0 - x) * 255.0),0,0,0));
                    }
                }
                byteBuffer.rewind();
                bitmap = mergeToPinBitmap(bitmap,bitmap1);
                progress.setText("Complete");
                imageView.setImageBitmap(bitmap);
                accept.setVisibility(View.VISIBLE);
            }
        });
        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().remove(FragmentRemoveBg.this).commit();
                fragmentContainerView.setVisibility(View.VISIBLE);
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageView.setImageBitmap(bitmap0);
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().remove(FragmentRemoveBg.this).commit();
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
}