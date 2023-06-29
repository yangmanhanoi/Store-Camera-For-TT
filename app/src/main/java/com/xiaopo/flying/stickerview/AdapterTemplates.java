package com.xiaopo.flying.stickerview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class AdapterTemplates extends RecyclerView.Adapter<AdapterTemplates.ViewHolder> {
    Context context;
    int layout;
    ArrayList<Pair<String, Integer>> list = new ArrayList<>();
    public onTemplatesClicked listener;
    public AdapterTemplates(Context context, int layout, ArrayList<Pair<String, Integer>> list, onTemplatesClicked listener)
    {
        this.context = context;
        this.layout = layout;
        this.list = list;
        this.listener = listener;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
        return new AdapterTemplates.ViewHolder(v);
    }

    @SuppressLint("ResourceType")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        String link = list.get(position).first;
        Integer isActive = list.get(position).second;
        Glide.with(context).load(link).into(holder.imageView);
        if(isActive == 0)
        {
            holder.frame_not_active.setVisibility(View.VISIBLE);
            holder.btn_download.setVisibility(View.VISIBLE);
            holder.btn_premium.setVisibility(View.VISIBLE);
        }
        else
        {
            holder.frame_not_active.setVisibility(View.GONE);
            holder.btn_download.setVisibility(View.GONE);
            holder.btn_premium.setVisibility(View.GONE);
        }
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.iTemplatesClicked(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView, btn_premium, btn_check, btn_download;
        FrameLayout frame_not_active;
        ConstraintLayout layout_outside;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            frame_not_active = itemView.findViewById(R.id.frame_not_active);
            imageView = itemView.findViewById(R.id.image);
            btn_premium = itemView.findViewById(R.id.btn_premium);
            btn_check = itemView.findViewById(R.id.btn_check);
            btn_download = itemView.findViewById(R.id.btn_download);
        }
    }
    public interface onTemplatesClicked{
        public void iTemplatesClicked(int posTemp);
    }
}