package com.xiaopo.flying.stickerview;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.core.widget.ContentLoadingProgressBar;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class AdapterDetailSticker extends RecyclerView.Adapter<AdapterDetailSticker.ViewHolder> {
    Context context;
    int layout;
    OnStickerClicked onStickerClicked;
    List<Bitmap> list = new ArrayList<>();
    private int currentSticker = -1;

    AdapterDetailSticker(Context context, int layout, List<Bitmap> list, OnStickerClicked listener)
    {
        this.context = context;
        this.layout = layout;
        this.list = list;
        this.onStickerClicked = listener;
    }
    @NonNull
    @Override
    public AdapterDetailSticker.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.custom_detail_sticker, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterDetailSticker.ViewHolder holder, int position) {
        Glide.with(context).load(list.get(position)).into(holder.sticker_detail);
        if(currentSticker == position)
        {
            holder.btn_download.setVisibility(View.GONE);
            holder.btn_check.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView sticker_detail, btn_download, btn_check, btn_premium;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            btn_check = itemView.findViewById(R.id.btn_check);
            btn_download = itemView.findViewById(R.id.btn_download);
            sticker_detail = itemView.findViewById(R.id.sticker_detail);
            btn_premium = itemView.findViewById(R.id.btn_premium);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //notifyItemChanged(currentSticker);
                    currentSticker = getAdapterPosition();
                    onStickerClicked.iStickerClicked(list.get(getAdapterPosition()));
                    notifyItemChanged(currentSticker);
                }
            });
        }
    }
    public interface OnStickerClicked{
        public void iStickerClicked(Bitmap bitmap);
    }
}
