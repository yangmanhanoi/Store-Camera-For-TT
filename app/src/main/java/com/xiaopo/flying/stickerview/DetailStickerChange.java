package com.xiaopo.flying.stickerview;

import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.xiaopo.flying.sticker.DrawableSticker;
import com.xiaopo.flying.sticker.Sticker;
import com.xiaopo.flying.sticker.StickerView;

public class DetailStickerChange extends Fragment implements View.OnClickListener, ColorFragment.isColorChosenSent, Fragment_Opacity.onOpacityChange{
    public static final String TAG = DetailStickerChange.class.getName();
    ImageView color, bolder, opacity, horizontal, flip, copy;
    ColorFragment colorFragment = new ColorFragment();
    Fragment_Opacity fragment_opacity = new Fragment_Opacity();
    NewEditActivity newEditActivity;
    StickerView stickerView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_sticker_detail, container, false);
        //
        newEditActivity = (NewEditActivity) getActivity();
        fragment_opacity.setChangeSelected(newEditActivity);
        colorFragment.setChangeSelected(newEditActivity);
        color = v.findViewById(R.id.iv_color);
        bolder = v.findViewById(R.id.iv_bolder);
        opacity = v.findViewById(R.id.iv_opacity);
        horizontal = v.findViewById(R.id.iv_horizontal);
        flip = v.findViewById(R.id.iv_vertical);
        copy = v.findViewById(R.id.iv_copy);
        stickerView = getActivity().findViewById(R.id.sticker);
        //
        color.setOnClickListener(this);
        bolder.setOnClickListener(this);
        opacity.setOnClickListener(this);
        horizontal.setOnClickListener(this);
        flip.setOnClickListener(this);
        copy.setOnClickListener(this);
        //
        return v;
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if(id == R.id.iv_color)
        {
            ReplaceFragmentBottom(colorFragment, ColorFragment.TAG);
        }
        else if(id == R.id.iv_bolder)
        {
            Log.e("isclicked", "yes");
            Sticker st = newEditActivity.stickerView.getCurrentSticker();
            if(st instanceof DrawableSticker)
            {
                //newEditActivity.st.setDrawableStroke(-205 ,50);
            }
        }
        else if(id == R.id.iv_opacity)
        {
            ReplaceFragmentBottom(fragment_opacity, Fragment_Opacity.TAG);

        }
        else if(id == R.id.iv_horizontal)
        {
            stickerView.flip(stickerView.getCurrentSticker(), stickerView.FLIP_HORIZONTALLY);
        }
        else if(id == R.id.iv_vertical)
        {
            stickerView.flip(stickerView.getCurrentSticker(),stickerView.FLIP_VERTICALLY);
        }
        else
        {
            Drawable drawable = stickerView.getCurrentSticker().getDrawable();
            //int oca = stickerView.getCurrentSticker().getDrawable().getAlpha();
            Drawable.ConstantState constantState = drawable.getConstantState();
            if(constantState != null)
            {
                Drawable copyDrawableSticker = constantState.newDrawable().mutate();
                DrawableSticker newSticker = new DrawableSticker(copyDrawableSticker);
                stickerView.mAddStickertoSpeceficPosition(newSticker, stickerView.getStickerPoints(stickerView.getCurrentSticker())[0] + 50, stickerView.getStickerPoints(stickerView.getCurrentSticker())[1] + 50);
            }

        }
    }

    public void ReplaceFragmentBottom(Fragment fragment, String tag)
    {
        FragmentManager fm = getFragmentManager();
        fm.beginTransaction().replace(R.id.frame_sticker_change, fragment).addToBackStack(tag).commit();
    }

    @Override
    public void ColorSent(int color) {

    }

    @Override
    public void iOpacityProgress(int i) {

    }
}