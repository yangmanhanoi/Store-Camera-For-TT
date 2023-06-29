package com.xiaopo.flying.stickerview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class AdapterRecyclerColor extends RecyclerView.Adapter<AdapterRecyclerColor.ViewHolder> {
    List<Integer> listColor = new ArrayList<>();
    Context context;
    int layout;
    public static int flag = -1;
    private ClickColorListener iClickListener;
    public AdapterRecyclerColor(Context context, int layout, List<Integer> list, ClickColorListener listener)
    {
        this.context = context;
        this.layout = layout;
        this.listColor = list;
        this.iClickListener = listener;
    }
    @NonNull
    @Override
    public AdapterRecyclerColor.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.custom_detail_color, parent, false);
        return new AdapterRecyclerColor.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterRecyclerColor.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.img.setBackgroundColor(listColor.get(position));
        if (flag == position) holder.img.setSelected(true);
        else holder.img.setSelected(false);
    }

    @Override
    public int getItemCount() {
        return listColor.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.img_color);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if( flag != getAdapterPosition() || flag == -1)
                    {
                        notifyItemChanged(flag);
                        flag = getAdapterPosition();
                        iClickListener.clickColor(listColor.get(getAdapterPosition()));
                        Log.e("is_clicked", String.valueOf(listColor.get(getAdapterPosition())));
                        notifyItemChanged(flag);
                    }

                }
            });
        }
    }
    public interface ClickColorListener{
        public void clickColor(int color);
    }
}
