package com.xiaopo.flying.stickerview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class AdapterMyprojects extends RecyclerView.Adapter<AdapterMyprojects.ViewHolder>{

    File[] listImage;
    Context context;

    public AdapterMyprojects(Context context, File[] listFile)
    {
        this.context = context;
        this.listImage = listFile;
    }
    @NonNull
    @Override
    public AdapterMyprojects.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.layout_recyclerview_photos, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterMyprojects.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        if (listImage.length>0){
            Bitmap bitmap = BitmapFactory.decodeFile(String.valueOf(listImage[position]));
            Glide.with(context).load(bitmap).into(holder.img_templates);
        }

    }

    @Override
    public int getItemCount() {
        //return listImage.length;
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img_templates;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img_templates = itemView.findViewById(R.id.imagePhotos);
        }
    }
}
