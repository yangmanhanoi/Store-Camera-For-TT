package com.xiaopo.flying.stickerview;

import static com.xiaopo.flying.stickerview.StickerFragment.*;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.renderscript.ScriptGroup;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.xiaopo.flying.sticker.DrawableSticker;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class DetailStickerFragment extends Fragment implements AdapterStickerContent.OnStickerContentClicked, OnStickerContentChosen{
    public static final String TAG = DetailStickerFragment.class.getName();
    Context context;
    private int  pos = 0;
    List<Bitmap> list = new ArrayList<>();
    RecyclerView rcv_detail_sticker;
    NewEditActivity newEditActivity;
    AdapterDetailSticker adapterDetailSticker;
    GridLayoutManager gridLayoutManager;
    String[] array;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.detail_sticker_fragment, container, false);
        //
        context = getContext();
        rcv_detail_sticker = v.findViewById(R.id.rcv_sticker_detail);
        newEditActivity = (NewEditActivity) getActivity();
        gridLayoutManager = new GridLayoutManager(context, 4, LinearLayoutManager.VERTICAL, false);
        //
        Bundle bundle = getArguments();
        pos = 0;
        getData(0);
        if(bundle != null)
        {
            Log.e("ktra", "not null");
            pos = bundle.getInt("Sticker Content");
        }
        getData(pos);
        Log.e("pos", pos + "");
        adapterDetailSticker = new AdapterDetailSticker(context, R.layout.custom_detail_sticker, list, new AdapterDetailSticker.OnStickerClicked() {
            // For add sticker to main
            @Override
            public void iStickerClicked(Bitmap bitmap) {
                Drawable d = new BitmapDrawable(getResources(), bitmap);
                DrawableSticker drawableSticker = new DrawableSticker(d);
                newEditActivity.stickerView.addSticker(drawableSticker);
                newEditActivity.stickerView.invalidate();
                getFragmentManager().popBackStack(com.xiaopo.flying.stickerview.StickerFragment.TAG, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            }
        });
        rcv_detail_sticker.setAdapter(adapterDetailSticker);
        rcv_detail_sticker.setLayoutManager(gridLayoutManager);
        adapterDetailSticker.notifyDataSetChanged();

        return v;
    }

    private void getData(int pos) {
        Log.e("pos in detail", pos + "");
        list.clear();
        String path = "subjects/";
        if(pos == 0)
        {
            path += "animals";
            try {
                array = getContext().getAssets().list(path);
                Log.e("String size", array.length + "");
            } catch (IOException e) {
                e.printStackTrace();
            }
            for(int i = 0; i < array.length; i++)
            {
                String directory = path + "/pet (" + String.valueOf(i + 1) + ").png";
                try {
                    InputStream istr = getContext().getAssets().open(directory);
                    Bitmap bitmap = BitmapFactory.decodeStream(istr);
                    list.add(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        else if(pos == 1)
        {
            path += "box";
            try {
                array = getContext().getAssets().list(path);
                Log.e("String size", array.length + "");
            } catch (IOException e) {
                e.printStackTrace();
            }
            for(int i = 0; i < array.length; i++)
            {
                String directory = path + "/box (" + String.valueOf(i + 1) + ").png";
                try {
                    InputStream istr = getContext().getAssets().open(directory);
                    Bitmap bitmap = BitmapFactory.decodeStream(istr);
                    list.add(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        else if(pos == 2)
        {
            path += "brush_stroke";
            try {
                array = getContext().getAssets().list(path);
                Log.e("String size", array.length + "");
            } catch (IOException e) {
                e.printStackTrace();
            }
            for(int i = 0; i < array.length; i++)
            {
                String directory = path + "/brush (" + String.valueOf(i + 1) + ").png";
                try {
                    InputStream istr = getContext().getAssets().open(directory);
                    Bitmap bitmap = BitmapFactory.decodeStream(istr);
                    list.add(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        else if(pos == 3)
        {
            path += "circle";
            try {
                array = getContext().getAssets().list(path);
                Log.e("String size", array.length + "");
            } catch (IOException e) {
                e.printStackTrace();
            }
            for(int i = 0; i < array.length; i++)
            {
                String directory = path + "/circle" + String.valueOf(i + 1) + ".png";
                try {
                    InputStream istr = getContext().getAssets().open(directory);
                    Bitmap bitmap = BitmapFactory.decodeStream(istr);
                    list.add(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        else if(pos == 4)
        {
            path += "collection";
            try {
                array = getContext().getAssets().list(path);
                Log.e("String size", array.length + "");
            } catch (IOException e) {
                e.printStackTrace();
            }
            for(int i = 0; i < array.length; i++)
            {
                String directory = path + "/collection" + String.valueOf(i + 1) + ".png";
                try {
                    InputStream istr = getContext().getAssets().open(directory);
                    Bitmap bitmap = BitmapFactory.decodeStream(istr);
                    list.add(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        else if(pos == 5)
        {
            path += "lens_flare";
            try {
                array = getContext().getAssets().list(path);
                Log.e("String size", array.length + "");
            } catch (IOException e) {
                e.printStackTrace();
            }
            for(int i = 0; i < array.length; i++)
            {
                String directory = path + "/lens (" + String.valueOf(i + 1) + ").png";
                try {
                    InputStream istr = getContext().getAssets().open(directory);
                    Bitmap bitmap = BitmapFactory.decodeStream(istr);
                    list.add(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        else if(pos == 5)
        {
            path += "smiley_face";
            try {
                array = getContext().getAssets().list(path);
                Log.e("String size", array.length + "");
            } catch (IOException e) {
                e.printStackTrace();
            }
            for(int i = 0; i < array.length; i++)
            {
                String directory = path + "/sml (" + String.valueOf(i + 1) + ").png";
                try {
                    InputStream istr = getContext().getAssets().open(directory);
                    Bitmap bitmap = BitmapFactory.decodeStream(istr);
                    list.add(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        else
        {
            path += "troll_face";
            try {
                array = getContext().getAssets().list(path);
                Log.e("String size", array.length + "");
            } catch (IOException e) {
                e.printStackTrace();
            }
            for(int i = 0; i < array.length; i++)
            {
                String directory = path + "/troll_face" + String.valueOf(i + 1) + ".png";
                try {
                    InputStream istr = getContext().getAssets().open(directory);
                    Bitmap bitmap = BitmapFactory.decodeStream(istr);
                    list.add(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        //initAdapter();
    }


    @Override
    public void iStickerContentClicked(int pos) {
    }

    @Override
    public void iStickerContentChosen(int pos) {
    }
}
