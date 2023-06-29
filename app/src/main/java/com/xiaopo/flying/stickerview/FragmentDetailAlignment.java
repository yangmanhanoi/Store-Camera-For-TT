package com.xiaopo.flying.stickerview;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class FragmentDetailAlignment extends Fragment implements View.OnClickListener{
    public static final String TAG = FragmentDetailAlignment.class.getName();
    ImageView img_left, img_center, img_right, img_jscenter;
    ImageView btn_done;
    iClickAlignment myIclick;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_detail_alignment, container, false);
        //
        img_left = v.findViewById(R.id.img_left);
        img_center = v.findViewById(R.id.img_center);
        img_right = v.findViewById(R.id.img_right);
        img_jscenter = v.findViewById(R.id.img_jscenter);
        btn_done = v.findViewById(R.id.btn_done);
        //
        img_left.setOnClickListener(this);
        img_center.setOnClickListener(this);
        img_right.setOnClickListener(this);
        img_jscenter.setOnClickListener(this);
        btn_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().popBackStackImmediate();
            }
        });
        return v;
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        int pos;
        if(id == R.id.img_left) pos = 1;
        else if(id == R.id.img_center) pos = 2;
        else if(id == R.id.img_right) pos = 3;
        else pos = 4;
        Log.e("pos", pos + "");
        myIclick.isChosenAlignment(pos);
    }
    public  interface iClickAlignment
    {
        public void isChosenAlignment(int pos);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof iClickAlignment)
        {
            myIclick = (iClickAlignment) context;
        }
        else throw new RuntimeException(context.toString() + " xxxx");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        myIclick = null;
    }
}
