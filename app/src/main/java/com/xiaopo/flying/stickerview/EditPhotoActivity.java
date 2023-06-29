package com.xiaopo.flying.stickerview;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.xiaopo.flying.stickerview.fragments.FragmentBackground.FragmentBackgroundMain;
import com.xiaopo.flying.stickerview.fragments.FragmentCrop.FragmentCropMain;
import com.xiaopo.flying.stickerview.fragments.FragmentFilter.FragmentFilterMain;
import com.xiaopo.flying.stickerview.fragments.FragmentProperties.FragmentPropertiesMain;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;


public class EditPhotoActivity extends AppCompatActivity {
    ImageView image_edit;
    RadioButton bar_properties, bar_filter, bar_crop, bar_removeBg;
    ImageView dash1,dash2,dash3,dash4;
    TextView toolbar_text,path;
    String photoPath;
    Bitmap bitmap;
    Button butBack,butSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_photo);
        toolbar_text = findViewById(R.id.toolbar_edit);
        image_edit=findViewById(R.id.Image_editing);
        photoPath = getIntent().getStringExtra("photoPath");
        bitmap = BitmapFactory.decodeFile(photoPath);
        image_edit.setImageBitmap(bitmap);

        bar_properties =findViewById(R.id.bar_setting);
        bar_filter =findViewById(R.id.bar_filter);
        bar_crop=findViewById(R.id.bar_crop);
        bar_removeBg =findViewById(R.id.bar_removebg);

        butBack = findViewById(R.id.butBack_editPhoto);
        butBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        butSave = findViewById(R.id.butSave);
        butSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bitmap = ((BitmapDrawable)image_edit.getDrawable()).getBitmap();
                long time= System.currentTimeMillis();
                String string = "/storage/emulated/0/DCIM/StoreCamera/"+time+".JPEG";
                File file = new File(string);
                try {
                    OutputStream outputStream = new FileOutputStream(file);
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                Intent intent = new Intent(EditPhotoActivity.this, ExportActivity.class);
                intent.putExtra("path", string);
                startActivity(intent);
            }
        });

        dash1=findViewById(R.id.dash1);
        dash2=findViewById(R.id.dash2);
        dash3=findViewById(R.id.dash3);
        dash4=findViewById(R.id.dash4);

        FragmentManager fragmentManager = getSupportFragmentManager();

        path = findViewById(R.id.imagePath);
        path.setText(photoPath);

        bar_properties.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetAll();
                fragmentManager.beginTransaction().setCustomAnimations(android.R.anim.fade_in,android.R.anim.fade_out).replace(R.id.fragment_container_view, FragmentPropertiesMain.class,null).commit();
                dash1.setVisibility(View.VISIBLE);
                bar_properties.setChecked(true);
                toolbar_text.setText("Edit");
            }
        });

        bar_filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetAll();
                fragmentManager.beginTransaction().setCustomAnimations(android.R.anim.fade_in,android.R.anim.fade_out).replace(R.id.fragment_container_view, FragmentFilterMain.class,null).commit();
                dash2.setVisibility(View.VISIBLE);
                bar_filter.setChecked(true);
                toolbar_text.setText("Filter");
            }
        });

        bar_crop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetAll();
                fragmentManager.beginTransaction().setCustomAnimations(android.R.anim.fade_in,android.R.anim.fade_out).replace(R.id.fragment_container_view, FragmentCropMain.class,null).commit();
                dash3.setVisibility(View.VISIBLE);
                bar_crop.setChecked(true);
                toolbar_text.setText("Crop");
            }
        });

        bar_removeBg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetAll();
                fragmentManager.beginTransaction().setCustomAnimations(android.R.anim.fade_in,android.R.anim.fade_out).replace(R.id.fragment_container_view, FragmentBackgroundMain.class,null).commit();
                dash4.setVisibility(View.VISIBLE);
                bar_removeBg.setChecked(true);
                toolbar_text.setText("Remove Background");
            }
        });

    }

    public void resetAll(){
        dash1.setVisibility(View.INVISIBLE);
        dash2.setVisibility(View.INVISIBLE);
        dash3.setVisibility(View.INVISIBLE);
        dash4.setVisibility(View.INVISIBLE);
        bar_properties.setChecked(false);
        bar_filter.setChecked(false);
        bar_crop.setChecked(false);
        bar_removeBg.setChecked(false);
    }

}