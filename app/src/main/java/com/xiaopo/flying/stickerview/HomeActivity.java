package com.xiaopo.flying.stickerview;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {
    ViewPager viewPager;
    ImageView img_templates, img_camera, img_myProject;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.home_activity);
        viewPager = findViewById(R.id.viewPager);
        img_camera = findViewById(R.id.img_camera);
        img_myProject = findViewById(R.id.img_myProject);
        img_templates = findViewById(R.id.img_template);

        img_camera.setOnClickListener(this::onClick);
        img_myProject.setOnClickListener(this::onClick);
        img_templates.setOnClickListener(this::onClick);

        AdapterViewPagerMain adapterViewPager = new AdapterViewPagerMain(getSupportFragmentManager());
        viewPager.setAdapter(adapterViewPager);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        img_myProject.setImageResource(R.drawable.not_highlight_album);
                        img_templates.setImageResource(R.drawable.highlighted_template);
                        break;
                    case 1:
                        img_templates.setImageResource(R.drawable.not_highlighted_template);
                        img_myProject.setImageResource(R.drawable.highlighted_album);
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id)
        {
            case R.id.img_camera:
                Intent intent = new Intent(HomeActivity.this, CameraStoreActivity.class);
                startActivity(intent);
                break;
            case R.id.img_template:
                viewPager.setCurrentItem(0);
                break;
            case R.id.img_myProject:
                viewPager.setCurrentItem(1);
                break;
        }
    }
}
