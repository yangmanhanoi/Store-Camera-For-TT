package com.xiaopo.flying.stickerview;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DetailTextFont extends AppCompatActivity {
    Toolbar toolbar;
    RecyclerView rcl_textFont;
    AdapterTextFont adapterTextFont;
    Context context;
    String[] assetManager;
    List<Typeface> listFont = new ArrayList<>();
    List<String> list = new ArrayList<>();
    FontFragment fontFragment = new FontFragment();
    ischosenFont listener;
    public interface ischosenFont
    {
        void onFontchosensent(String s);
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.fragment_detail_text_font);
        super.onCreate(savedInstanceState);
        //
//        toolbar = findViewById(R.id.control);
//        toolbar.setTitle("");
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayShowHomeEnabled(true);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        rcl_textFont = findViewById(R.id.rcl_detail_font);
        context = getApplicationContext();
        getData();
        getTypeface();
        //
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, true);
        rcl_textFont.setLayoutManager(linearLayoutManager);
//        adapterTextFont = new AdapterTextFont(context, R.layout.custom_text_font, listFont, new AdapterTextFont.iClickFontName() {
//            @Override
//            public void onClickFontName(String s) {
//                listener.onFontchosensent(s);
//                changeFont(s);
//            }
//        });
        rcl_textFont.setAdapter(adapterTextFont);
        adapterTextFont.notifyDataSetChanged();
    }
    public void getData()
    {
        try {
            assetManager = getAssets().list("fonts");
        } catch (IOException e) {
            e.printStackTrace();
        }
        list = Arrays.asList(assetManager);
        Log.e("size", String.valueOf(list.size()));
    }
    public void getTypeface()
    {
        for(int i = 0; i < list.size(); i++)
        {
            Typeface res = Typeface.createFromAsset(context.getAssets(), "fonts/" + list.get(i));
            listFont.add(res);
        }
    }
    public void changeFont(String s)
    {
        // send data to FontFragment
        Bundle bundle = new Bundle();
        bundle.putString("fontName", s);
        fontFragment.setArguments(bundle);
    }
}
