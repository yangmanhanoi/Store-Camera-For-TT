package com.xiaopo.flying.stickerview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdapterRecyclerItem extends RecyclerView.Adapter<AdapterRecyclerItem.ViewHolder>{
    Context context;
    int layout;
    ArrayList<String> arrayList = new ArrayList<>();
    boolean isActivated = false;
    private IClickFontListener iClickFontListener;
    int pos;
    NewEditActivity newEditActivity = new NewEditActivity();
    FontFragment fontFragment = new FontFragment();
    public AdapterRecyclerItem(Context context, int layout, ArrayList<String> arrayList, IClickFontListener listener)
    {
        this.context = context;
        this.layout = layout;
        this.arrayList = arrayList;
        this.iClickFontListener = listener;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
        return new ViewHolder(v);
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        //
        int id = context.getResources().getIdentifier(arrayList.get(position), "drawable", context.getPackageName());
        Drawable drawable = context.getResources().getDrawable(id);
        holder.imageView.setImageDrawable(drawable);
        //
        int id2 = context.getResources().getIdentifier(arrayList.get(position), "string", context.getPackageName());
        holder.tv.setText(context.getResources().getString(id2));
        //
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iClickFontListener.onClickFont("item" + (position + ""));
                Log.e("check", "item" + (position + ""));
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout layout;
        ImageView imageView;
        TextView tv;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.imageItem);
            tv = itemView.findViewById(R.id.name);
            layout = itemView.findViewById(R.id.layout_custom);
        }
    }
    public void setActivated(boolean activated)
    {
        isActivated = activated;
    }
}
