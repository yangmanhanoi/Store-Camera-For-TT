package com.xiaopo.flying.stickerview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.media.Image;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.xiaopo.flying.sticker.DrawableSticker;
import com.xiaopo.flying.sticker.Sticker;
import com.xiaopo.flying.sticker.TextSticker;

import java.util.List;
import java.util.logging.LogRecord;
import java.util.zip.Inflater;

public class AdapterRecyclerLayer extends RecyclerView.Adapter<AdapterRecyclerLayer.ViewHolder> {
    Context context;
    List<Sticker> list;
    int layout;
    private static int flag = -1;
    public OnItemClicked listener;
    public AdapterRecyclerLayer(Context context, List<Sticker> list, int layout, OnItemClicked listener)
    {
        this.context = context;
        this.list = list;
        this.layout = layout;
        this.listener = listener;
    }
    @NonNull
    @Override
    public AdapterRecyclerLayer.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_layer_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterRecyclerLayer.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        if(list.get(position) instanceof DrawableSticker)
        {
            holder.layerItem.setImageDrawable(list.get(position).getDrawable());
            holder.layerItem.setScaleType(ImageView.ScaleType.FIT_CENTER);
            holder.itemName.setText("Sticker");
        }
        else if(list.get(position) instanceof TextSticker)
        {
            holder.itemName.setText(((TextSticker) list.get(position)).getText());
            holder.layerItem.setImageDrawable(context.getResources().getDrawable(R.drawable.text_default));
            holder.layerItem.setScaleType(ImageView.ScaleType.FIT_CENTER);
        }
        holder.img_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.isDelete(list.get(position));
                list.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, list.size());
            }
        });
        if(position == flag)
        {
            holder.cardView.setBackgroundColor(Color.parseColor("#999999"));
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout layout, back;
        ImageView layerItem, img_delete;
        CardView cardView;
        TextView itemName;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.cardLayer);
            back = itemView.findViewById(R.id.back);
            layout = itemView.findViewById(R.id.layout_item);
            layerItem = itemView.findViewById(R.id.itemImage);
            img_delete = itemView.findViewById(R.id.img_delete);
            itemName = itemView.findViewById(R.id.layerName);
            layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    notifyItemChanged(flag);
                    flag = getAdapterPosition();
                    listener.iItemChosen(list.get(getAdapterPosition()));
                    notifyItemChanged(flag);
                }
            });
        }
    }
    public interface OnItemClicked{
        void iItemChosen(Sticker sticker);
        void isDelete(Sticker sticker);
    }
}
