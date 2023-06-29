package com.xiaopo.flying.stickerview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.icu.lang.UProperty;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.io.File;
import java.util.ArrayList;

public class AdapterPhotos extends RecyclerView.Adapter<AdapterPhotos.ViewHolderPhotos> {

    ArrayList<String> listImage = new ArrayList<>();

    Context context;
    public AdapterPhotos(Context context,ArrayList<String> listImage) {
        this.listImage=listImage;
        this.context=context;
    }

    public class ViewHolderPhotos extends RecyclerView.ViewHolder{

        ImageView imageView;

        public ViewHolderPhotos(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imagePhotos);
        }
    }

    @NonNull
    @Override
    public AdapterPhotos.ViewHolderPhotos onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_recyclerview_photos, parent, false);
        return new ViewHolderPhotos(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterPhotos.ViewHolderPhotos holderPhotos, @SuppressLint("RecyclerView") int position) {
        if (listImage.size()>0){
            Bitmap bitmap = BitmapFactory.decodeFile(listImage.get(position));
            Glide.with(context).load(bitmap).into(holderPhotos.imageView);

            holderPhotos.imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent editImageIntent = new Intent(context, EditPhotoActivity.class);
                    editImageIntent.putExtra("photoPath",listImage.get(position));
                    context.startActivities(new Intent[]{editImageIntent});
                }
            });
        }
    }


    @Override
    public int getItemCount() {
        if (listImage.size()>30){
            return 30;
        } else return listImage.size();
    }
}
