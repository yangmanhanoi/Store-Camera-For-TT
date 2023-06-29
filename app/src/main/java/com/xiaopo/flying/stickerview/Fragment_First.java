package com.xiaopo.flying.stickerview;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.xiaopo.flying.sticker.TextSticker;

public class Fragment_First extends Fragment implements View.OnClickListener{
    ImageView layer, addText, sticker, addPhoto;
    TextSticker textSticker;
    private ClickFuncListener clickFuncListener;
    public void setClickFuncListener(ClickFuncListener clickFuncListener) {
        this.clickFuncListener = clickFuncListener;
    }
    public static Fragment_First newInstance() {
        Bundle args = new Bundle();

        Fragment_First fragment = new Fragment_First();
        fragment.setArguments(args);
        return fragment;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_first, container, false);
        //
        layer =  v.findViewById(R.id.btn_layer);
        addText =  v.findViewById(R.id.btn_addtext);
        sticker =  v.findViewById(R.id.btn_sticker);
        addPhoto =  v.findViewById(R.id.btn_addphoto);
        //

        return v;
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if(id == R.id.btn_addtext)
        {
            if(clickFuncListener != null) clickFuncListener.clickNewText();
        }
        else if(id == R.id.btn_layer)
        {
            if(clickFuncListener != null) clickFuncListener.clickNewLayer();
        }
        else if(id == R.id.btn_addphoto)
        {
            if(clickFuncListener != null) clickFuncListener.clickNewPhoto();
        }
        else
        {
            if(clickFuncListener != null) clickFuncListener.clickNewSticker();
        }
    }
    public interface ClickFuncListener{
        void clickNewSticker();
        void clickNewText();
        void clickNewPhoto();
        void clickNewLayer();
    }
}
