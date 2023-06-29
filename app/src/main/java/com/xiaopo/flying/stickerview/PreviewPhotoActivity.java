package com.xiaopo.flying.stickerview;

import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.core.Preview;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;

public class PreviewPhotoActivity extends AppCompatActivity {
    ImageView imageView;
    Button butBack,butDel,butShare,butEdit,butSave;
    Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview_photo);
        imageView = findViewById(R.id.Image_preview);
        String photoPath = getIntent().getStringExtra("photoPath");
        Log.e("path", photoPath);
        bitmap = BitmapFactory.decodeFile(photoPath);
        imageView.setImageBitmap(bitmap);

        butBack = findViewById(R.id.butBack_previewPhoto);
        butBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        butSave = findViewById(R.id.butSavePreview);
        butSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long time= System.currentTimeMillis();
                String string = "/storage/emulated/0/DCIM/StoreCamera/"+time+".JPEG";
                File file = new File(string);
                try {
                    OutputStream outputStream = new FileOutputStream(file);
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                Intent intent = new Intent(PreviewPhotoActivity.this, ExportActivity.class);
                intent.putExtra("path", string);
                startActivity(intent);
            }
        });

        butDel = findViewById(R.id.erase_image);
        butDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CameraStoreActivity.class);
                startActivity(intent);
            }
        });

        butShare = findViewById(R.id.share_image);
        butShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent share = new Intent(Intent.ACTION_SEND);
                share.setType("image/jpeg");

                ContentValues values = new ContentValues();
                values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
                Uri uri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

                OutputStream outstream;
                try {
                    outstream = getContentResolver().openOutputStream(uri);
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outstream);
                    outstream.close();
                } catch (Exception e) {
                    System.err.println(e.toString());
                }

                share.putExtra(Intent.EXTRA_STREAM, uri);
                startActivity(Intent.createChooser(share, "Share Image"));
            }
        });

        butEdit = findViewById(R.id.edit_image);
        butEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent edit_Intent = new Intent(PreviewPhotoActivity.this, EditPhotoActivity.class);
                edit_Intent.putExtra("photoPath",photoPath);
                startActivity(edit_Intent);
            }
        });

    }

}