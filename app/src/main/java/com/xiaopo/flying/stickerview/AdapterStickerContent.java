package com.xiaopo.flying.stickerview;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class AdapterStickerContent extends RecyclerView.Adapter<AdapterStickerContent.ViewHolder> {
    Context context;
    private static int flag = 0;
    int layout;
    List<String> list = new ArrayList<>();
    public OnStickerContentClicked onStickerContentClicked;
    public AdapterStickerContent(Context context, int layout, List<String> list, OnStickerContentClicked listener)
    {
        this.context = context;
        this.layout = layout;
        this.list = list;
        this.onStickerContentClicked = listener;
    }
    @NonNull
    @Override
    public AdapterStickerContent.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.custom_sticker_content, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterStickerContent.ViewHolder holder, int position) {
        holder.txt.setText(list.get(position));
        if(position != flag)
        {
            holder.txt.setSelected(false);
            holder.txt.setTextColor(Color.WHITE);
            holder.v.setVisibility(View.GONE);
        }
        else
        {
            holder.txt.setTextColor(Color.parseColor("#514EB6"));
            holder.v.setVisibility(View.VISIBLE);
            onStickerContentClicked.iStickerContentClicked(position);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txt;
        View v;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txt = itemView.findViewById(R.id.txt_sticker_content);
            v = itemView.findViewById(R.id.view);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    notifyItemChanged(flag);
                    flag = getAdapterPosition();
                    itemView.setSelected(true);
                    //if(flag != -1) onStickerContentClicked.iStickerContentClicked(flag);
                    notifyItemChanged(flag);
                }
            });
        }
    }
    public interface OnStickerContentClicked{
        void iStickerContentClicked(int pos);
    }
}
