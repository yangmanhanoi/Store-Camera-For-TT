package com.xiaopo.flying.stickerview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AdapterTextFont extends RecyclerView.Adapter<AdapterTextFont.ViewHolder> {
    Context context;
    List<Typeface> list = new ArrayList<>();
    List<String> name = new ArrayList<>();
    int layout;
    public static int flag = -1;
    private onBackKeyListener onclicked;
    private iClickFontName myIclickFontName;
    public interface iClickFontName
    {
        void onClickFontName(String s);
    }
//    public AdapterTextFont(Context context, int layout, List<Typeface> list,onBackKeyListener listener)
//    {
//        this.context = context;
//        this.layout = layout;
//        this.list = list;
//        this.onclicked = listener;
//    }
    public AdapterTextFont(Context context, int layout, List<Typeface> list,iClickFontName listener)
    {
        this.context = context;
        this.layout = layout;
        this.list = list;
        this.myIclickFontName = listener;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        holder.txt_fontName.setText("Your text here");
        holder.txt_fontName.setTypeface(list.get(position));
        if(flag == position)
        {
            holder.ischosen.setVisibility(View.VISIBLE);
        }
        else holder.ischosen.setVisibility(View.GONE);
//        holder.layout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                notifyItemChanged(flag);
//                flag = position;
//                //myIclickFontName.onClickFontName(String.valueOf(position));
//                onclicked.onBackPressed(String.valueOf(position));
//                notifyItemChanged(flag);
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ischosen;
        TextView txt_fontName;
        LinearLayout layout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ischosen = itemView.findViewById(R.id.ischosen);
            txt_fontName = itemView.findViewById(R.id.font_text_name);
            layout = itemView.findViewById(R.id.layout_text_font);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(flag != getAdapterPosition() || flag == -1)
                    {
                        notifyItemChanged(flag);
                        flag = getAdapterPosition();
                        Log.e("item is clicked", flag + "");
                        //onclicked.onBackPressed(String.valueOf(flag));
                        myIclickFontName.onClickFontName(String.valueOf(flag));
                        notifyItemChanged(flag);
                    }
                }
            });
        }
    }
}
