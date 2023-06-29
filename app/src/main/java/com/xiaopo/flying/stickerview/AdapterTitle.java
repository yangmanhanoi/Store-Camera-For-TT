package com.xiaopo.flying.stickerview;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdapterTitle extends RecyclerView.Adapter<AdapterTitle.ViewHolder> {
    public int pos = 0;
    Context context;
    int layout;
    public onTitleClicked listener;
    ArrayList<String> arrTemplateType = new ArrayList<>();
    public AdapterTitle(Context context, int layout, ArrayList<String> arrTemplateType, onTitleClicked listener)
    {
        this.context = context;
        this.layout = layout;
        this.arrTemplateType = arrTemplateType;
        this.listener = listener;
    }
    @NonNull
    @Override
    public AdapterTitle.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
        return new AdapterTitle.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterTitle.ViewHolder holder, int position) {
        Log.e("ktra", position + "");
        String type = arrTemplateType.get(position);
        holder.bnt_display.setText(type);
        if(position == pos)
        {
            holder.bnt_display.setSelected(true);
        }
        else
        {
            holder.bnt_display.setSelected(false);
        }
        if(holder.bnt_display.isSelected())
        {
            listener.iTitleClick(position);
        }
    }

    @Override
    public int getItemCount() {
        return arrTemplateType.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView bnt_display;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            bnt_display = itemView.findViewById(R.id.btn_display);
            bnt_display.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    notifyItemChanged(pos);
                    pos = getAdapterPosition();
                    notifyItemChanged(pos);
                }
            });
        }
    }
    public interface onTitleClicked{
        void iTitleClick(int pos);
    }
}
