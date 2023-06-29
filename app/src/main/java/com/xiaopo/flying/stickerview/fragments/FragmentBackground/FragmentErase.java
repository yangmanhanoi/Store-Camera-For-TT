package com.xiaopo.flying.stickerview.fragments.FragmentBackground;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;
import androidx.fragment.app.FragmentManager;

import com.xiaopo.flying.stickerview.BrushImageView;
import com.xiaopo.flying.stickerview.R;
import com.xiaopo.flying.stickerview.TouchImageView;


public class FragmentErase extends Fragment {

    FragmentContainerView fragmentContainerView;
    Button cancel,accept;
    SeekBar intensity;
    TextView textViewSize;
    CardView cardViewEdit;
    Bitmap originalBitmap, bitmapMaster, resizedBitmap, highResolutionOutput, lastEditedBitmap;
    int size=20, imageViewWidth, imageViewHeight, MODE=0, initialDrawingCount, updatedBrushSize, offset=0, initialDrawingCountLimit=20;
    ImageView imageView;
    TextView path;
    Point mainViewSize;
    Path drawingPath;
    Canvas canvasMaster;
    boolean isImageResized, isMultipleTouchErasing,isTouchOnBitmap;
    BrushImageView brushImageView;
    TouchImageView touchImageView;
    float brushSize = 20.0f,currentx, currenty;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_background_erase, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        fragmentContainerView=getActivity().findViewById(R.id.fragment_container_view);
        cancel = getView().findViewById(R.id.cancel_eraser);
        accept = getView().findViewById(R.id.accept_eraser);
        intensity = getView().findViewById(R.id.intensity_eraser);
        textViewSize = getView().findViewById(R.id.eraserSize);
        imageView = getActivity().findViewById(R.id.Image_editing);
        cardViewEdit = getActivity().findViewById(R.id.cardViewEdit);
        path = getActivity().findViewById(R.id.path);
    }

    @Override
    public void onResume() {
        super.onResume();
        originalBitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
        cardViewEdit.removeAllViews();
        brushImageView = new BrushImageView(getActivity());
        touchImageView = new TouchImageView(getActivity());
        mainViewSize = new Point();
        drawingPath = new Path();
        Display display = getActivity().getWindowManager().getDefaultDisplay();
        display.getSize(mainViewSize);
        brushImageView.offset = 0;
        brushImageView.width = brushSize;
        imageViewWidth = cardViewEdit.getWidth();
        imageViewHeight = cardViewEdit.getHeight();
        touchImageView.setOnTouchListener(new OnTouchListner());
        setBitMap();
        cardViewEdit.addView(touchImageView);
        cardViewEdit.addView(brushImageView);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getFragmentManager();
                resetGPUimageView();
                fragmentManager.beginTransaction().remove(FragmentErase.this).commit();
                fragmentContainerView.setVisibility(View.VISIBLE);
            }
        });
        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().remove(FragmentErase.this).commit();
                fragmentContainerView.setVisibility(View.VISIBLE);
                makeHighResolutionOutput();
                originalBitmap = highResolutionOutput;
                resetGPUimageView();
            }
        });

        intensity.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                float currentPosition =  (float)(seekBar.getWidth()-60) / seekBar.getMax()* progress;
                textViewSize.setText(progress+"");
                textViewSize.setX(currentPosition+seekBar.getX());
                size=progress;
                brushSize = progress + 20.0f;
                updateBrushWidth();
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
        imageView.setImageBitmap(originalBitmap);
        cardViewEdit.addView(imageView);
    }

    public void setBitMap() {
        this.isImageResized = false;
        if (resizedBitmap != null) {
            resizedBitmap.recycle();
            resizedBitmap = null;
        }
        if (bitmapMaster != null) {
            bitmapMaster.recycle();
            bitmapMaster = null;
        }
        canvasMaster = null;
        resizedBitmap = resizeBitmapByCanvas();

        lastEditedBitmap = resizedBitmap.copy(Bitmap.Config.ARGB_8888, true);
        bitmapMaster = Bitmap.createBitmap(lastEditedBitmap.getWidth(), lastEditedBitmap.getHeight(), Bitmap.Config.ARGB_8888);
        canvasMaster = new Canvas(bitmapMaster);
        canvasMaster.drawBitmap(lastEditedBitmap, 0.0f, 0.0f, null);
        touchImageView.setImageBitmap(bitmapMaster);
        touchImageView.setPan(false);
        brushImageView.invalidate();
    }

    public Bitmap resizeBitmapByCanvas() {
        float width;
        float heigth;
        float orginalWidth = (float) originalBitmap.getWidth();
        float orginalHeight = (float) originalBitmap.getHeight();
        if (orginalWidth > orginalHeight) {
            width = (float) imageViewWidth;
            heigth = (((float) imageViewWidth) * orginalHeight) / orginalWidth;
        } else {
            heigth = (float) imageViewHeight;
            width = (((float) imageViewHeight) * orginalWidth) / orginalHeight;
        }
        if (width > orginalWidth || heigth > orginalHeight) {
            return originalBitmap;
        }
        Bitmap background = Bitmap.createBitmap((int) width, (int) heigth, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(background);
        float scale = width / orginalWidth;
        float yTranslation = (heigth - (orginalHeight * scale)) / 2.0f;
        Matrix transformation = new Matrix();
        transformation.postTranslate(0.0f, yTranslation);
        transformation.preScale(scale, scale);
        Paint paint = new Paint();
        paint.setFilterBitmap(true);
        canvas.drawBitmap(originalBitmap, transformation, paint);
        this.isImageResized = true;
        return background;
    }

    private void moveTopoint(float startx, float starty) {
        float zoomScale = getImageViewZoom();
        starty -= (float) offset;

        PointF transLation = getImageViewTranslation();
        int projectedX = (int) ((float) (((double) (startx - transLation.x)) / ((double) zoomScale)));
        int projectedY = (int) ((float) (((double) (starty - transLation.y)) / ((double) zoomScale)));
        drawingPath.moveTo((float) projectedX, (float) projectedY);

        updatedBrushSize = (int) (brushSize / zoomScale);
    }

    private void lineTopoint(Bitmap bm, float startx, float starty) {
        if (initialDrawingCount < initialDrawingCountLimit) {
            initialDrawingCount += 1;
            if (initialDrawingCount == initialDrawingCountLimit) {
                isMultipleTouchErasing = true;
            }
        }
        float zoomScale = getImageViewZoom();
        starty -= (float) offset;
        PointF transLation = getImageViewTranslation();
        int projectedX = (int) ((float) (((double) (startx - transLation.x)) / ((double) zoomScale)));
        int projectedY = (int) ((float) (((double) (starty - transLation.y)) / ((double) zoomScale)));
        if (!isTouchOnBitmap && projectedX > 0 && projectedX < bm.getWidth() && projectedY > 0 && projectedY < bm.getHeight()) {
            isTouchOnBitmap = true;
        }
        drawingPath.lineTo((float) projectedX, (float) projectedY);
    }

    private void drawOnTouchMove() {
        Paint paint = new Paint();
        paint.setStrokeWidth((float) updatedBrushSize);
        paint.setColor(0);
        paint.setStyle(Paint.Style.STROKE);
        paint.setAntiAlias(true);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC));
        canvasMaster.drawPath(drawingPath, paint);
        touchImageView.invalidate();
    }

    public void UpdateCanvas() {
        canvasMaster.drawColor(0, PorterDuff.Mode.CLEAR);
        canvasMaster.drawBitmap(lastEditedBitmap, 0.0f, 0.0f, null);
        int i = 0;
        while (true) {
            Paint paint = new Paint();
            paint.setColor(0);
            paint.setStyle(Paint.Style.STROKE);
            paint.setAntiAlias(true);
            paint.setStrokeJoin(Paint.Join.ROUND);
            paint.setStrokeCap(Paint.Cap.ROUND);
            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC));
            paint.setStrokeWidth((float) brushSize);
            i += 1;
        }
    }

    public void updateBrushWidth() {
        brushImageView.width = brushSize / 2.0f;
        brushImageView.invalidate();
    }

    public void updateBrush(float x, float y) {
        brushImageView.offset = (float) offset;
        brushImageView.centerx = x;
        brushImageView.centery = y;
        brushImageView.width = brushSize / 2.0f;
        brushImageView.invalidate();
    }

    public float getImageViewZoom() {
        return touchImageView.getCurrentZoom();
    }

    public PointF getImageViewTranslation() {
        return touchImageView.getTransForm();
    }

    private class OnTouchListner implements View.OnTouchListener {
        OnTouchListner() {
        }

        public boolean onTouch(View v, MotionEvent event) {
            int action = event.getAction();
            if (!(event.getPointerCount() == 1 || isMultipleTouchErasing)) {
                if (initialDrawingCount > 0) {
                    UpdateCanvas();
                    drawingPath.reset();
                    initialDrawingCount = 0;
                }
                touchImageView.onTouchEvent(event);
                MODE = 2;
            } else if (action == MotionEvent.ACTION_DOWN) {
                isTouchOnBitmap = false;
                touchImageView.onTouchEvent(event);
                MODE = 1;
                initialDrawingCount = 0;
                isMultipleTouchErasing = false;
                moveTopoint(event.getX(), event.getY());

                updateBrush(event.getX(), event.getY());
            } else if (action == MotionEvent.ACTION_MOVE) {
                if (MODE == 1) {
                    currentx = event.getX();
                    currenty = event.getY();

                    updateBrush(currentx, currenty);
                    lineTopoint(bitmapMaster, currentx, currenty);

                    drawOnTouchMove();
                }
            } else if (action == MotionEvent.ACTION_UP || action == MotionEvent.ACTION_POINTER_UP) {
                if (isTouchOnBitmap) {
                    drawingPath = new Path();
                }
                isMultipleTouchErasing = false;
                initialDrawingCount = 0;
                MODE = 0;
            }
            if (action == MotionEvent.ACTION_UP || action == MotionEvent.ACTION_POINTER_UP) {
                MODE = 0;
            }
            return true;
        }
    }

    private void makeHighResolutionOutput() {
        if (this.isImageResized) {
            Bitmap solidColor = Bitmap.createBitmap(this.originalBitmap.getWidth(), this.originalBitmap.getHeight(), this.originalBitmap.getConfig());
            Canvas canvas = new Canvas(solidColor);
            Paint paint = new Paint();
            paint.setColor(Color.argb(255, 255, 255, 255));
            Rect src = new Rect(0, 0, this.bitmapMaster.getWidth(), this.bitmapMaster.getHeight());
            Rect dest = new Rect(0, 0, this.originalBitmap.getWidth(), this.originalBitmap.getHeight());
            canvas.drawRect(dest, paint);
            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));
            canvas.drawBitmap(this.bitmapMaster, src, dest, paint);
            this.highResolutionOutput = null;
            this.highResolutionOutput = Bitmap.createBitmap(this.originalBitmap.getWidth(), this.originalBitmap.getHeight(), this.originalBitmap.getConfig());
            Canvas canvas1 = new Canvas(this.highResolutionOutput);
            canvas1.drawBitmap(this.originalBitmap, 0.0f, 0.0f, null);
            Paint paint1 = new Paint();
            paint1.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));
            canvas1.drawBitmap(solidColor, 0.0f, 0.0f, paint1);
            if (solidColor != null && !solidColor.isRecycled()) {
                solidColor.recycle();
                solidColor = null;
            }
            return;
        }
        this.highResolutionOutput = null;
        this.highResolutionOutput = this.bitmapMaster.copy(this.bitmapMaster.getConfig(), true);
    }

}