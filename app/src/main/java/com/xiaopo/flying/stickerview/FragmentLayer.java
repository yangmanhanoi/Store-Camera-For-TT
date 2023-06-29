package com.xiaopo.flying.stickerview;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Bundle;
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

import com.xiaopo.flying.sticker.Sticker;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class FragmentLayer extends Fragment implements View.OnClickListener{
    public static final String TAG = FragmentLayer.class.getName();
    Context context;
    RecyclerView rcl_layer;
    ImageView layerUp, layerDown;
    NewEditActivity newEditActivity;
    AdapterRecyclerLayer adapterRecyclerLayer;
    int count = 0;
    int flag = -1;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_layer, container, false);
        context = getContext();
        rcl_layer = v.findViewById(R.id.rcl_layer);
        layerDown = v.findViewById(R.id.layerDown);
        layerUp = v.findViewById(R.id.layerUp);
        newEditActivity = (NewEditActivity) getActivity();

        layerUp.setOnClickListener(this);
        layerDown.setOnClickListener(this);

        //Collections.reverse(newEditActivity.stickerView.stickers);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        rcl_layer.setLayoutManager(linearLayoutManager);
        adapterRecyclerLayer = new AdapterRecyclerLayer(context, newEditActivity.stickerView.stickers, R.layout.custom_layer_item, new AdapterRecyclerLayer.OnItemClicked() {
            @Override
            public void iItemChosen(Sticker sticker) {
                newEditActivity.stickerView.handlingSticker = sticker;
                newEditActivity.customStickerView();
                count = newEditActivity.stickerView.getStickerCount();
            }

            @Override
            public void isDelete(Sticker sticker) {
                newEditActivity.stickerView.remove(sticker);
                newEditActivity.stickerView.invalidate();
            }
        });
        rcl_layer.setAdapter(adapterRecyclerLayer);
        adapterRecyclerLayer.notifyDataSetChanged();
        return v;
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        int stickerID = newEditActivity.stickerView.stickers.indexOf(newEditActivity.stickerView.handlingSticker);
        RectF currentRect = new RectF();
        currentRect = newEditActivity.stickerView.handlingSticker.getBound();
        if(id == R.id.layerDown)
        {
            for(int i = 0; i < newEditActivity.stickerView.stickers.size(); i++)
            {
                if(i != stickerID)
                {
                    RectF targetRectF = new RectF();
                    targetRectF = newEditActivity.stickerView.stickers.get(i).getBound();
                    if(targetRectF.intersect(currentRect))
                    {
                        if(targetRectF.contains(currentRect))
                        {
                            Collections.swap(newEditActivity.stickerView.stickers, stickerID, i);
                            newEditActivity.stickerView.invalidate();
                            adapterRecyclerLayer.notifyItemMoved(stickerID, i);
                            break;
                        }
                    }

                }

            }
        }
        else if(id == R.id.layerUp)
        {
            for(int i = 0; i < newEditActivity.stickerView.stickers.size(); i++)
            {
                if(i != stickerID)
                {
                    RectF targetRectF = new RectF();
                    targetRectF = newEditActivity.stickerView.stickers.get(i).getBound();
                    if(targetRectF.intersect(currentRect))
                    {
                        if(currentRect.contains(targetRectF))
                        {
                            Collections.swap(newEditActivity.stickerView.stickers, stickerID, i);
                            newEditActivity.stickerView.invalidate();
                            adapterRecyclerLayer.notifyItemMoved(stickerID, i);
                            break;
                        }
                    }

                }
            }
        }
//        Collections.swap(newEditActivity.stickerView.stickers, stickerID, targetID);
//        newEditActivity.stickerView.invalidate();
    }
}
