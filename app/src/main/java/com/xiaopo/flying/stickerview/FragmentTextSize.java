package com.xiaopo.flying.stickerview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Matrix;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.xiaopo.flying.sticker.Sticker;
import com.xiaopo.flying.sticker.StickerView;
import com.xiaopo.flying.sticker.TextSticker;
import com.xiaopo.flying.sticker.ZoomIconEvent;

public class FragmentTextSize extends Fragment implements SeekBar.OnSeekBarChangeListener{
    public static final String TAG = FragmentTextSize.class.getName();
    SeekBar seekBar;
    TextView txt_size;
    Sticker sticker;
    ImageView btn_done, btn_back;
    public onTextSizeChange listener;
    private isChangedAccept mlistener;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_text_size, container, false);
        seekBar = v.findViewById(R.id.seekBar);
        txt_size = v.findViewById(R.id.txt_size);
        NewEditActivity newEditActivity = (NewEditActivity) getActivity();
        sticker = newEditActivity.stickerView.getCurrentSticker();
        btn_done = v.findViewById(R.id.btn_done);
        btn_back = v.findViewById(R.id.btn_back);
        btn_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = getFragmentManager();
                fm.popBackStackImmediate();
                mlistener.isAccepted(true);
            }
        });
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = getFragmentManager();
                fm.popBackStackImmediate();
                mlistener.isAccepted(false);
            }
        });
        seekBar.setOnSeekBarChangeListener(this);
        return v;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof onTextSizeChange)
        {
            listener = (onTextSizeChange) context;
        }
        else throw new RuntimeException(context.toString() + "xxxx");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    @Override
    public void onResume() {
        super.onResume();
        NewEditActivity newEditActivity = (NewEditActivity) getActivity();
        Sticker sticker = newEditActivity.stickerView.getCurrentSticker();
        if(sticker != null)
        {
            float scale = sticker.getCurrentScale();
            if(scale > 5) scale = 5.1f;
            else if(scale < 0.1) scale = 0.1f;
            @SuppressLint("DefaultLocale") String str=String.format("%.02f", scale);
            seekBar.setProgress((int) (scale*100));
            txt_size.setText("x " + str);
        }
    }
    public void setSelected(isChangedAccept mlistener)
    {
        this.mlistener = mlistener;
    }
    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
        if(b)
        {
            NewEditActivity newEditActivity = (NewEditActivity) getActivity();
            int t = seekBar.getProgress() + 10;
            float s = t/100f;
            Sticker st = newEditActivity.stickerView.getCurrentSticker();
            if(st != null)
            {
                st.getMatrix().postScale(s/st.getCurrentScale(), s/st.getCurrentScale(), st.getMappedCenterPoint().x, st.getMappedCenterPoint().y);
                newEditActivity.stickerView.invalidate();
            }
            @SuppressLint("DefaultLocale") String str = String.format("%.02f", s);
            txt_size.setText("x " + str);
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }


    public interface onTextSizeChange{
        void onSizeChange(float value);
    }

}
