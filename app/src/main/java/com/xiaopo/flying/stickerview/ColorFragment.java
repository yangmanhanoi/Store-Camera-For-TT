package com.xiaopo.flying.stickerview;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.xiaopo.flying.sticker.Sticker;
import com.xiaopo.flying.sticker.StickerView;
import com.xiaopo.flying.sticker.TextSticker;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class ColorFragment extends Fragment {
    public static final String TAG = ColorFragment.class.getName();
    List<Integer> listColor = new ArrayList<>();
    RecyclerView rcl_color;
    Context context;
    ImageView btn_done, btn_back;
    private isColorChosenSent listener;
    StickerView stickerView;
    TextView txt_name;
    Sticker st;
    NewEditActivity newEditActivity;
    private isChangedAccept mlistener;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_color, container, false);
        context = getContext();
        getListColor();
        //
        newEditActivity = (NewEditActivity) getActivity();
        txt_name = v.findViewById(R.id.textName);
        stickerView = getActivity().findViewById(R.id.sticker);
        st = stickerView.getCurrentSticker();
        btn_done = v.findViewById(R.id.btn_done);
        btn_back = v.findViewById(R.id.btn_back);
        btn_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = getFragmentManager();
                mlistener.isAccepted(true);
                fm.popBackStackImmediate();
            }
        });
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = getFragmentManager();
                mlistener.isAccepted(false);
                fm.popBackStackImmediate();
            }
        });
        rcl_color = v.findViewById(R.id.detail_color);
        rcl_color.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        AdapterRecyclerColor adapterRecyclerColor = new AdapterRecyclerColor(context, R.layout.custom_detail_color, listColor, new AdapterRecyclerColor.ClickColorListener() {
            @Override
            public void clickColor(int color) {
                listener.ColorSent(color);
            }
        });
        rcl_color.setAdapter(adapterRecyclerColor);
        adapterRecyclerColor.notifyDataSetChanged();
        //
        return v;
    }
    public void getListColor()
    {
        if(listColor.size() > 0) return;
        TypedArray typedArray = context.getResources().obtainTypedArray(R.array.origil_colors);
        for(int i = 0; i < typedArray.length(); i++)
        {
            listColor.add(typedArray.getColor(i, 0));
        }
        typedArray.recycle();
    }

    public  interface isColorChosenSent{
        void ColorSent(int color);
    }

//    @Override
//    public void onAttach(@NonNull Context context) {
//        super.onAttach(context);
//        if(context instanceof isColorChosenSent)
//        {
//            listener = (isColorChosenSent) context;
//        }
//        else throw new RuntimeException(context.toString() + " xxxx");
//    }

    public void setSelected(isChangedAccept listener)
    {
        this.mlistener = listener;
    }
    public void setChangeSelected(isColorChosenSent listener){
        if(listener != null)
        {
            this.listener = listener;
        }
}
    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

}
