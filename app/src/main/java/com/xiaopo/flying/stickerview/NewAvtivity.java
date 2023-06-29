package com.xiaopo.flying.stickerview;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.xiaopo.flying.sticker.BitmapStickerIcon;
import com.xiaopo.flying.sticker.DeleteIconEvent;
import com.xiaopo.flying.sticker.FlipHorizontallyEvent;
import com.xiaopo.flying.sticker.Sticker;
import com.xiaopo.flying.sticker.StickerView;
import com.xiaopo.flying.sticker.TextSticker;
import com.xiaopo.flying.sticker.ZoomIconEvent;

import java.util.ArrayList;
import java.util.Arrays;

public class NewAvtivity extends AppCompatActivity implements View.OnClickListener{
    StickerView stickerView;
    FrameLayout canvas;
    Toolbar toolbar;
    ImageView layer, addText, sticker, addPhoto, back;
    TextSticker textSticker;
    LinearLayout sticker_change, newIncludeLayout;
    RecyclerView rcl;
    ArrayList<String> arrayList = new ArrayList<>();
    Context context;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_new_activity);
        context = getApplicationContext();
        //toolbar = findViewById(R.id.newtoolbar);
        layer =  findViewById(R.id.btn_layer);
        addText =  findViewById(R.id.btn_addtext);
        sticker =  findViewById(R.id.btn_sticker);
        addPhoto =  findViewById(R.id.btn_addphoto);
        stickerView = findViewById(R.id.sticker);
        canvas = findViewById(R.id.canvas);
        sticker_change = findViewById(R.id.unseen_layout);
        newIncludeLayout = findViewById(R.id.newIncludeLayout);
//
        rcl = sticker_change.findViewById(R.id.recycler_addText);
        back = sticker_change.findViewById(R.id.back_to_addtext);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sticker_change.setVisibility(View.GONE);
                newIncludeLayout.setVisibility(View.VISIBLE);
            }
        });
        addResources();;
//        AdapterRecyclerItem adapterRecyclerItem = new AdapterRecyclerItem(getApplicationContext(), R.layout.custom_layout_addtext, arrayList);
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, RecyclerView.HORIZONTAL, true);
//        rcl.setLayoutManager(linearLayoutManager);
//        rcl.setAdapter(adapterRecyclerItem);
        //adapterRecyclerItem.notifyDataSetChanged();
//
        layer.setOnClickListener(this);
        addText.setOnClickListener(this);
        sticker.setOnClickListener(this);
        addPhoto.setOnClickListener(this);
// Bitmap StickerIcon
        BitmapStickerIcon deleteIcon = new BitmapStickerIcon(ContextCompat.getDrawable(this,
                com.xiaopo.flying.sticker.R.drawable.sticker_ic_close_white_18dp),
                BitmapStickerIcon.LEFT_TOP);
        deleteIcon.setIconEvent(new DeleteIconEvent());

        BitmapStickerIcon zoomIcon = new BitmapStickerIcon(ContextCompat.getDrawable(this,
                com.xiaopo.flying.sticker.R.drawable.sticker_ic_scale_white_18dp),
                BitmapStickerIcon.RIGHT_BOTOM);
        zoomIcon.setIconEvent(new ZoomIconEvent());

        BitmapStickerIcon flipIcon = new BitmapStickerIcon(ContextCompat.getDrawable(this,
                com.xiaopo.flying.sticker.R.drawable.sticker_ic_flip_white_18dp),
                BitmapStickerIcon.RIGHT_TOP);
        flipIcon.setIconEvent(new FlipHorizontallyEvent());

        BitmapStickerIcon heartIcon =
                new BitmapStickerIcon(ContextCompat.getDrawable(this, R.drawable.ic_favorite_white_24dp),
                        BitmapStickerIcon.LEFT_BOTTOM);
        heartIcon.setIconEvent(new HelloIconEvent());
        stickerView.setIcons(Arrays.asList(deleteIcon, zoomIcon, flipIcon, heartIcon));

        initView();
    }

    public NewAvtivity() {
        super();
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if(id == R.id.btn_addtext)
        {
       //  Add a StickerText
            textSticker = new TextSticker(this);
            textSticker.setDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.sticker_transparent_background));
            textSticker.setText("Your text here!!");
            textSticker.setTextColor(Color.BLACK);
            textSticker.setTextAlign(Layout.Alignment.ALIGN_CENTER);
            textSticker.resizeText();
            stickerView.addSticker(textSticker);
        }
    }
    private void addResources()
    {
        String x = "item";
        for(int i = 7; i >= 1; i--)
        {
            arrayList.add(x + String.valueOf(i));
        }
        arrayList.add("item0");
    }
    private void initView()
    {
        stickerView.setBackgroundColor(Color.WHITE);
        stickerView.setLocked(false);
        stickerView.setConstrained(true);
        stickerView.setOnStickerOperationListener(new StickerView.OnStickerOperationListener() {
            @Override
            public void onStickerAdded(@NonNull Sticker sticker) {

            }

            @Override
            public void onStickerClicked(@NonNull Sticker sticker) {
                if(textSticker instanceof TextSticker)
                {
                    textSticker.setTextColor(Color.RED);
                    sticker_change.setVisibility(View.VISIBLE);
                    newIncludeLayout.setVisibility(View.GONE);
                    // stickerView.replace(textSticker);
                    // stickerView.invalidate();
                }
            }

            @Override
            public void onStickerDeleted(@NonNull Sticker sticker) {
                sticker_change.setVisibility(View.GONE);
                newIncludeLayout.setVisibility(View.VISIBLE);
            }

            @Override
            public void onStickerDragFinished(@NonNull Sticker sticker) {

            }

            @Override
            public void onStickerTouchedDown(@NonNull Sticker sticker) {

            }

            @Override
            public void onStickerZoomFinished(@NonNull Sticker sticker) {

            }

            @Override
            public void onStickerFlipped(@NonNull Sticker sticker) {

            }

            @Override
            public void onStickerDoubleTapped(@NonNull Sticker sticker) {

            }

            @Override
            public void onStickerTouchOutside() {

            }
        });
    }
}
