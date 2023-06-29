package com.xiaopo.flying.stickerview;

import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.OutputStream;

public class ExportActivity extends AppCompatActivity {

    ImageView back, home, share, createNew, image;
    Bitmap bitmap;
    Uri uri;
    File file2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_export);

        back = findViewById(R.id.backExport);
        home = findViewById(R.id.homeExport);
        share = findViewById(R.id.shareExport);
        createNew = findViewById(R.id.createNewExport);
        image = findViewById(R.id.imageExport);

        bitmap = BitmapFactory.decodeFile(getIntent().getStringExtra("path"));
        image.setImageBitmap(bitmap);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent homeIntent = new Intent(ExportActivity.this, HomeActivity.class);
                startActivity(homeIntent);
            }
        });

        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent share = new Intent(Intent.ACTION_SEND);
                share.setType("image/jpeg");

                ContentValues values = new ContentValues();
                values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
                uri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

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
                file2 = new File(uri.getPath());
                if (file2.exists()) file2.delete();
            }
        });

        createNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent newIntent = new Intent(ExportActivity.this, TemplateFragment.class);
                startActivity(newIntent);
            }
        });
    }
}