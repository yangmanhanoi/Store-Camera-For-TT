package com.xiaopo.flying.stickerview;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AddTextActivity extends AppCompatActivity {
    Toolbar toolbar;
    RecyclerView rcl;
    ImageView recover, next;
    Context context;
    ArrayList<String> arrayList;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_add_text);

        rcl = (RecyclerView) findViewById(R.id.recycler_addText);
        recover = (ImageView) findViewById(R.id.recover);
        next = (ImageView) findViewById(R.id.next);
        context = getApplicationContext();
        toolbar = findViewById(R.id.toolbar);
        arrayList = new ArrayList<>();

        addResources();
        Log.e("a", String.valueOf(arrayList.size()));
// toolbar
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
///
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, true);
        rcl.setLayoutManager(linearLayoutManager);
        AdapterRecyclerItem adapterRecyclerItem = new AdapterRecyclerItem(context,R.layout.custom_layout_addtext, arrayList, new IClickFontListener() {
            @Override
            public void onClickFont(String s) {

            }
        });
        rcl.setAdapter(adapterRecyclerItem);
        adapterRecyclerItem.notifyDataSetChanged();
    }
    private void addResources()
    {
        String x = "item";
        for(int i = 6; i >= 1; i--)
        {
            arrayList.add(x + String.valueOf(i));
        }
    }
}
