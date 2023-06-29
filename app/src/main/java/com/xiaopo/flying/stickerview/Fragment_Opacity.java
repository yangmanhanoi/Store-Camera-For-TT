package com.xiaopo.flying.stickerview;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.xiaopo.flying.sticker.StickerView;

public class Fragment_Opacity extends Fragment {
    public static final String TAG = Fragment_Opacity.class.getName();
    SeekBar seekBar;
    ImageView btn_done, btn_back;
    Context context;
    TextView tv_opacity;
    private onOpacityChange onOpacityChange;
    NewEditActivity newEditActivity;
    private isChangedAccept listener;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.opacity_fragment, container, false);
        //
        context = getContext();
        seekBar = v.findViewById(R.id.seekBar);
        btn_done = v.findViewById(R.id.btn_done);
        btn_back = v.findViewById(R.id.btn_back);
        tv_opacity = v.findViewById(R.id.iv_opacity);
        newEditActivity = (NewEditActivity) getActivity();
        //
        btn_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = getFragmentManager();
                fm.popBackStackImmediate();
                if(listener != null) listener.isAccepted(true);
            }
        });
        //
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = getFragmentManager();
                fm.popBackStackImmediate();
                if(listener != null) listener.isAccepted(false);

            }
        });
        //
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if(onOpacityChange != null)
                {
                    onOpacityChange.iOpacityProgress(i);
                    tv_opacity.setText(String.valueOf(i));
                }

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        return v;
    }
    public interface onOpacityChange{
        void iOpacityProgress(int i);
    }
    public void setSelected(isChangedAccept listener)
    {
        if(listener != null)
        {
            this.listener = listener;
        }
    }
    public void setChangeSelected(onOpacityChange listener)
    {
        if(listener != null)
        {
            this.onOpacityChange = listener;
        }

    }
//    @Override
//    public void onAttach(@NonNull Context context) {
//        super.onAttach(context);
//        if(context instanceof onOpacityChange)
//        {
//            onOpacityChange = (Fragment_Opacity.onOpacityChange) context;
//        }
//        else throw new RuntimeException(context.toString() + " xxxx");
//    }

    @Override
    public void onDetach() {
        super.onDetach();
        onOpacityChange = null;
        listener = null;
    }
}
