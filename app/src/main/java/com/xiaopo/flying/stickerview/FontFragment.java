package com.xiaopo.flying.stickerview;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.fonts.Font;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.xiaopo.flying.sticker.Sticker;
import com.xiaopo.flying.sticker.TextSticker;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FontFragment extends Fragment implements AdapterFontRVAdapter.ClickToFontTextItem, FragmentDetailTextFont.ischosenFont {
    public static final String TAG = FontFragment.class.getName();
    RecyclerView rcl_font;
    AdapterFontRVAdapter adapterFontRVAdapter;
    AdapterFontRVAdapter.ClickToFontTextItem clickFontTextItem;
    List<Typeface> list = new ArrayList<>();
    ImageView btn_done, btn_back;
    Button btn_detail;
    FragmentDetailTextFont fragmentDetailTextFont = new FragmentDetailTextFont();
    String tmp = "-1";
    onFontsent listener;
    private isChangedAccept mListener;
    public static int x = 0;

    // Get from FragmentDetailTextFont
    @Override
    public void onFontchosensent(Typeface typeface) {
        btn_detail.setText("Your text here!");
        this.btn_detail.setTypeface(typeface);
        btn_detail.invalidate();
    }
// Get from AdapterTextFont
//    @Override
//    public void onClickFontName(String s) {
//        btn_detail.setText("Your text here!");
//        btn_detail.setTypeface(Typeface.createFromAsset(getContext().getAssets(), "fonts/" + s + ".ttf"));
//        btn_detail.invalidate();
//    }

    public interface onFontsent
    {
        void sendTypeface(String s);
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_font, container, false);
        //
//        newEditActivity = (NewEditActivity) getActivity();
//        if(savedInstanceState != null)
//        {
//            tf_before = (Typeface) savedInstanceState.getParcelable("typeface");
//        }
//        else
//        {
//            Sticker curr = newEditActivity.stickerView.getCurrentSticker();
//            if( curr instanceof TextSticker)
//            {
//                tf_before = ((TextSticker) curr).getTypeface();
//                Log.e("bf", tf_before.toString());
//            }
//        }
        btn_back = v.findViewById(R.id.btn_back);
        btn_detail = v.findViewById(R.id.detail_text_font);
        btn_done = v.findViewById(R.id.btn_done);
        btn_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //listener.sendTypeface("YES");
                FragmentManager fm = getFragmentManager();
                fm.beginTransaction().setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).replace(R.id.main_bottom_container, fragmentDetailTextFont).addToBackStack(FragmentDetailTextFont.TAG).commit();
            }
        });
        //
        btn_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getFragmentManager() != null) {
                    getFragmentManager().popBackStackImmediate();
                    mListener.isAccepted(true);
                }
            }
        });
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getFragmentManager() != null) {
                    getFragmentManager().popBackStack();
                    mListener.isAccepted(false);
                }
            }
        });
        return v;
    }

    @Override
    public void onClickFontTexItem(Typeface font, int posInList) {
        if(clickFontTextItem != null)
        {
            clickFontTextItem.onClickFontTexItem(font, posInList);
        }
        rcl_font.scrollToPosition(posInList);
    }
    public interface ClickFontTextItem{
        void onClickFontTextItem(Typeface typeface, int posInList);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);

    }

//    @Override
//    public void onAttach(@NonNull Context context) {
//        super.onAttach(context);
//        if(context instanceof  onFontsent)
//        {
//            listener = (onFontsent) context;
//        }
//        else throw new RuntimeException(context.toString() + " xxxx");
//    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }
    public void setSelected(isChangedAccept listener)
    {
        this.mListener = listener;
    }
}
