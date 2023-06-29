package com.xiaopo.flying.stickerview;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class StickerFragment extends Fragment {
    public static final String TAG = StickerFragment.class.getName();
    List<String> list = new ArrayList<>();
    List<Bitmap> listDetailSticker = new ArrayList<>();
    Context context;
    FrameLayout frameLayout;
    RecyclerView rcl_sticker;
    NewEditActivity newEditActivity;
    AdapterStickerContent adapterStickerContent;
    OnStickerContentChosen listener;
    AssetManager assetManager;
    String[] sticker;
    public static int current = -1;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_sticker, container, false);
        context = getContext();
        rcl_sticker = v.findViewById(R.id.sticker_content);
        newEditActivity = (NewEditActivity) getActivity();
        frameLayout = v.findViewById(R.id.fragment_detail_sticker);
        getData();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        rcl_sticker.setLayoutManager(linearLayoutManager);
        adapterStickerContent = new AdapterStickerContent(context, R.layout.custom_sticker_content, list, new AdapterStickerContent.OnStickerContentClicked() {
            @Override
            public void iStickerContentClicked(int pos) {
                DetailStickerFragment detailStickerFragment = new DetailStickerFragment();
                FragmentManager fm = getFragmentManager();
                fm.beginTransaction().replace(R.id.fragment_detail_sticker, detailStickerFragment).commit();
                Bundle bundle = new Bundle();
                bundle.putInt("Sticker Content", pos);
                detailStickerFragment.setArguments(bundle);
                Log.e("content position", pos + "");
            }
        });
        rcl_sticker.setAdapter(adapterStickerContent);
        adapterStickerContent.notifyDataSetChanged();
        return v;
    }

    protected void getData() {
        if(list.size() != 0) return;
        list.add("Animal");
        list.add("Box");
        list.add("Brush Stroke");
        list.add("Circle");
        list.add("Collection");
        list.add("Lens Flare");
        list.add("Smiley Face");
        list.add("Troll Face");
    }


    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    public interface OnStickerContentChosen {
        void iStickerContentChosen(int pos);
    }
}
