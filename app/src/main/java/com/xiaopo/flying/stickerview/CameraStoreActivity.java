package com.xiaopo.flying.stickerview;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.Surface;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.core.Camera;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.ImageCaptureException;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.content.ContextCompat;

import com.google.common.util.concurrent.ListenableFuture;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.concurrent.ExecutionException;

public class CameraStoreActivity extends AppCompatActivity {

    PreviewView previewView;
    ImageView capture,captured_image,gridView;
    RelativeLayout toolbar_caputure;
    TextView but_delay, but_ratio,countDownView;
    private ImageCapture imageCapture;
    boolean flash_state=false;
    private CameraSelector lensFacing = CameraSelector.DEFAULT_BACK_CAMERA;
    int count_down=0,ratioScale=3;
    Button but_back,but_flash,but_change_camera,but_grid,show_toolbar;
    int width = 1800,height=3770;

    String currentPhotoPath;
    File imageFile,savedImage;
    Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_store);
        previewView = findViewById(R.id.previewView);
        capture = findViewById(R.id.Capture);
        toolbar_caputure = findViewById(R.id.toolbar_capture);
        countDownView = findViewById(R.id.countDown);

        this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        but_back = findViewById(R.id.butBackSnapshot);
        but_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        show_toolbar = findViewById(R.id.show_toolbar_capture);
        show_toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (toolbar_caputure.getVisibility()==View.VISIBLE){
                    toolbar_caputure.setVisibility(View.INVISIBLE);
                    show_toolbar.setBackground(getResources().getDrawable(R.drawable.arrow_up));
                } else {
                    toolbar_caputure.setVisibility(View.VISIBLE);
                    show_toolbar.setBackground(getResources().getDrawable(R.drawable.arrow_down));
                }
            }
        });

        captured_image = findViewById(R.id.capured_image);
        captured_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (lensFacing==CameraSelector.DEFAULT_FRONT_CAMERA) {
                    width=1200;
                    height=1600;
                } else {
                    width=1800;
                    height=3770;
                }
                Log.e("check",bitmap.getHeight() + " ");
                bitmap = Bitmap.createBitmap(bitmap,(bitmap.getWidth()-width)/2,(bitmap.getHeight()-height)/2,width,height);
                try (FileOutputStream out = new FileOutputStream(new File(currentPhotoPath))) {
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Intent preview_Intent = new Intent(getApplicationContext(), PreviewPhotoActivity.class);
                preview_Intent.putExtra("photoPath",currentPhotoPath);
                startActivity(preview_Intent);
            }
        });

        but_grid = findViewById(R.id.grid);
        gridView =findViewById(R.id.grid_View);
        but_grid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (gridView.getVisibility()==View.INVISIBLE){
                    gridView.setVisibility(View.VISIBLE);
                    but_grid.setBackground(getDrawable(R.drawable.grid_on));
                } else {
                    gridView.setVisibility(View.INVISIBLE);
                    but_grid.setBackground(getDrawable(R.drawable.grid_off));
                }
            }
        });

        but_delay=findViewById(R.id.delay);
        but_delay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (count_down){
                    case 0:
                        count_down=2;
                        break;
                    case 2:
                        count_down=5;
                        break;
                    case 5:
                        count_down=10;
                        break;
                    case 10:
                        count_down=0;
                        break;
                    default:
                        return;
                }
                but_delay.setText(count_down+"s");
            }
        });

        but_flash = findViewById(R.id.flash);

        but_ratio=findViewById(R.id.ratio_capture);
        but_ratio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (ratioScale){
                    case 0:
                        ratioScale++;
                        width = 3000;
                        height=width*4/3;
                        but_ratio.setText("3:4");
                        break;
                    case 1:
                        ratioScale++;
                        width = 2200;
                        height=width*16/9;
                        but_ratio.setText("9:16");
                        break;
                    case 2:
                        ratioScale++;
                        width = 1800;
                        height=width*2262/1080;
                        but_ratio.setText("Full");
                        break;
                    case 3:
                        ratioScale=0;
                        width = 3000;
                        height=width;
                        but_ratio.setText("1:1");
                        break;
                    default:
                        return;
                }
                change_ratio_preview();
            }
        });

        but_change_camera=findViewById(R.id.change_camera);
        but_change_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (lensFacing==CameraSelector.DEFAULT_BACK_CAMERA){
                    lensFacing=CameraSelector.DEFAULT_FRONT_CAMERA;
                } else lensFacing=CameraSelector.DEFAULT_BACK_CAMERA;
                start_camera();
            }
        });



        String filename = "photo";
        File storageDirectory = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        try {
            imageFile = File.createTempFile(filename, ".jpg",storageDirectory);
        } catch (IOException e) {
            e.printStackTrace();
        }

        start_camera();

        capture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (count_down!=0) countDownView.setVisibility(View.VISIBLE);
                new CountDownTimer((count_down+1)*1000,1000){

                    @Override
                    public void onTick(long millisUntilFinished) {
                        countDownView.setText(millisUntilFinished/1000+"");
                    }

                    @Override
                    public void onFinish() {
                        capture_image();
                        previewView.setVisibility(View.INVISIBLE);
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                previewView.setVisibility(View.VISIBLE);
                            }
                        },100);
                        countDownView.setVisibility(View.INVISIBLE);
                    }
                }.start();
            }
        });
    }

    protected void start_camera(){
        ListenableFuture<ProcessCameraProvider> cameraProviderFuture = ProcessCameraProvider.getInstance(this);

        cameraProviderFuture.addListener(() -> {
            try {
                ProcessCameraProvider cameraProvider = cameraProviderFuture.get();
                cameraProvider.unbindAll();
                Preview preview = new Preview.Builder().build();
                preview.setTargetRotation(Surface.ROTATION_0);
                preview.setSurfaceProvider(previewView.getSurfaceProvider());
                imageCapture = new ImageCapture.Builder().setCaptureMode(ImageCapture.CAPTURE_MODE_MAXIMIZE_QUALITY).build();
                Camera camera = cameraProvider.bindToLifecycle(this, lensFacing, preview, imageCapture);
                but_flash.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        flash_state=!flash_state;
                        camera.getCameraControl().enableTorch(flash_state);
                    }
                });
            } catch (InterruptedException | ExecutionException e) {

            }
        }, ContextCompat.getMainExecutor(this));
    }

    protected void capture_image(){
        ImageCapture.OutputFileOptions outputFileOptions = new ImageCapture.OutputFileOptions.Builder(imageFile).build();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            imageCapture.takePicture(outputFileOptions, getMainExecutor(), new ImageCapture.OnImageSavedCallback() {
                        @Override
                        public void onImageSaved(ImageCapture.OutputFileResults outputFileResults) {
                            savedImage = new File(outputFileResults.getSavedUri().getPath());
                            currentPhotoPath = savedImage.getAbsolutePath();
                            bitmap = BitmapFactory.decodeFile(currentPhotoPath);
                            Matrix matrix = new Matrix();
                            if (lensFacing==CameraSelector.DEFAULT_BACK_CAMERA) matrix.postRotate(90);
                            else {
                                matrix.postRotate(270);
                                matrix.postScale(-1, 1);
                            }
                            bitmap = Bitmap.createBitmap(bitmap,0,0, bitmap.getWidth(),bitmap.getHeight(),matrix,true);
                            captured_image.setVisibility(View.VISIBLE);
                            captured_image.setImageBitmap(bitmap);
                        }
                        @Override
                        public void onError(ImageCaptureException error) {
                        }
                    }
            );
        }
    }

    protected void change_ratio_preview(){
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(1080, ViewGroup.LayoutParams.MATCH_PARENT);
        switch (ratioScale){
            case 0:
                layoutParams = new FrameLayout.LayoutParams(1080, 1080);
                break;
            case 1:
                layoutParams = new FrameLayout.LayoutParams(1080, 1080/3*4);
                break;
            case 2:
                layoutParams = new FrameLayout.LayoutParams(1080, 1080/9*16);
                break;
            case 3:
                break;
            default:
                return;
        }
        layoutParams.gravity = Gravity.CENTER;
        previewView.setLayoutParams(layoutParams);
    }

}