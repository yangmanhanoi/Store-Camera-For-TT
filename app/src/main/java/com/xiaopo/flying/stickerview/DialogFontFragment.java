package com.xiaopo.flying.stickerview;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DialogFontFragment extends DialogFragment {
    private int width = 0;
    private int height = 0;
    Context context;
    RecyclerView rcl_textFont;
    AdapterTextFont adapterTextFont;
    String[] assetManager;
    List<Typeface> listFont = new ArrayList<>();
    List<String> list = new ArrayList<>();
    ischosenFont listener;
    public interface ischosenFont
    {
        void onFontchosensent(Typeface typeface);
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.dialog_add_text, container, false);
        context = getContext();
        rcl_textFont = v.findViewById(R.id.rcl_detail_font);
        if(list.size() == 0) getData();
        if(listFont.size() == 0) getTypeface();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, true);
        rcl_textFont.setLayoutManager(linearLayoutManager);
        adapterTextFont = new AdapterTextFont(getContext(), R.layout.custom_text_font, listFont, new AdapterTextFont.iClickFontName() {
            @Override
            public void onClickFontName(String s) {
                Log.e("String s", "" + s);
                if(s != null) {
                    Handler handler = new Handler();
                    Runnable runnable = new Runnable() {
                        @Override
                        public void run() {
                            if (getFragmentManager() != null) {
                                // Change to fontfragment
                                getFragmentManager().popBackStack();
                            }

                        }
                    };
                    handler.postDelayed(runnable, 1000);
                    listener.onFontchosensent(listFont.get(Integer.parseInt(s)));
                }
            }
        });
        hideNavBar();
        return v;
    }

    private void hideNavBar() {
        if (getDialog() != null && getDialog().getWindow() != null) {
            getDialog().getWindow()
                    .setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
            getDialog().getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
            width = 400;
            height =150;
            getDialog().getWindow().setLayout(width, height);
        }
    }
    public void getData()
    {
        try {
            assetManager = getContext().getAssets().list("fonts");
        } catch (IOException e) {
            e.printStackTrace();
        }
        list = Arrays.asList(assetManager);
        Log.e("List size", String.valueOf(list.size()));
    }
    public void getTypeface()
    {
        for(int i = 0; i < list.size(); i++)
        {
            Typeface res = Typeface.createFromAsset(getContext().getAssets(), "fonts/" + list.get(i));
            listFont.add(res);
        }
    }
}
