package com.xiaopo.flying.stickerview;

import android.content.Context;
import android.content.Intent;
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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Fragment_Second extends Fragment implements Fragment_First.ClickFuncListener, onBackKeyListener{
    public static final  String TAG = Fragment_Second.class.getName();
    RecyclerView rcl;
    Context context;
    ImageView btn_back;
    ArrayList<String> arrayList = new ArrayList<>();
    fragmentSecondOutput listener;
    NewEditActivity newEditActivity;
    @Override
    public void onBackPressed(String s) {
        FragmentManager fm = getFragmentManager();
        fm.popBackStackImmediate();
    }

    public interface fragmentSecondOutput
    {
        void onF2Listener(String s);
    }
    public static Fragment_Second newInstance(){
        return new Fragment_Second();
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_second,container, false);
//
        rcl = v.findViewById(R.id.recycler_addText);
        context = getContext();
        newEditActivity = (NewEditActivity) getActivity();
        btn_back = v.findViewById(R.id.back_to_addtext);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newEditActivity.onBackPressed("YES");
//                newEditActivity.changeSticker.setVisibility(View.VISIBLE);
//                newEditActivity.content.setVisibility(View.GONE);
                //onBackPressed("YEs");
            }
        });
//
        addResources();
//
        // Update reverseLayout
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        //
        rcl.setLayoutManager(linearLayoutManager);
        AdapterRecyclerItem adapterRecyclerItem = new AdapterRecyclerItem(context,R.layout.custom_layout_addtext, arrayList, new IClickFontListener() {
            @Override
            public void onClickFont(String s) {
                Log.e("isclicked", s);
                listener.onF2Listener(s);
            }
        });
        rcl.setAdapter(adapterRecyclerItem);
        adapterRecyclerItem.notifyDataSetChanged();

        return v;
    }
    private void addResources()
    {
        if(arrayList.size() == 0)
        {
            String x = "item";
            for(int i = 7; i >= 1; i--)
            {
                if(i != 2) arrayList.add(x + String.valueOf(i));
            }
        }
    }
    @Override
    public void clickNewSticker() {

    }

    @Override
    public void clickNewText() {

    }

    @Override
    public void clickNewPhoto() {

    }

    @Override
    public void clickNewLayer() {

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof fragmentSecondOutput)
        {
            listener = (fragmentSecondOutput) context;
        }
        else
        {
            throw new RuntimeException(context.toString() + " xxxx");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

}
