package com.xiaopo.flying.stickerview;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FragmentDetailTextFont extends Fragment {
    public  static  final String TAG = FragmentDetailTextFont.class.getName();
    RecyclerView rcl_textFont;
    ImageView btn_back, btn_done;
    Context context;
    AdapterTextFont adapterTextFont;
    String[] assetManager;
    List<Typeface> listFont = new ArrayList<>();
    List<String> list = new ArrayList<>();
    ischosenFont listener;
    public interface ischosenFont
    {
        void onFontchosensent(Typeface typeface);
    }
    public FragmentDetailTextFont(){}
    public FragmentDetailTextFont(Context context)
    {
        this.context = context;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_detail_text_font, container, false);
        //
        rcl_textFont = v.findViewById(R.id.rcl_detail_font);
        context = getContext();
        //
        if(list.size() == 0) getData();
        if(listFont.size() == 0) getTypeface();
        //
        //
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, true);
        rcl_textFont.setLayoutManager(linearLayoutManager);
        adapterTextFont = new AdapterTextFont(getContext(), R.layout.custom_text_font, listFont, new AdapterTextFont.iClickFontName() {
            @Override
            public void onClickFontName(String s) {
                Log.e("String s", "" + s);
                if(s != null)
                {
                    Handler handler = new Handler();
                    Runnable runnable = new Runnable() {
                        @Override
                        public void run() {
                            if(getFragmentManager() != null)
                            {
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
        rcl_textFont.setAdapter(adapterTextFont);
        adapterTextFont.notifyDataSetChanged();
        //
        return v;
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


    @Override
    public void onAttach(@NonNull Activity activity) {
        super.onAttach(activity);
        try{
            listener = (ischosenFont) activity;
        }catch (ClassCastException e){

        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }
}