package com.xiaopo.flying.stickerview;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class AdapterFontRVAdapter extends RecyclerView.Adapter<AdapterFontRVAdapter.ViewHolder> {
    private Context context;
    List<Typeface> listFont;
    ArrayList<String> files;
    ClickToFontTextItem clickToFontTextItem;
    private int selectedIndex = -1;
    public AdapterFontRVAdapter(Context context, List<Typeface> listFont, ClickToFontTextItem clickToFontTextItem)
    {
        this.context = context;
        this.listFont = listFont;
        this.clickToFontTextItem = clickToFontTextItem;
    }
    public void setSelectedIndex(int selectedIndex){this.selectedIndex = selectedIndex;}
    @NonNull
    @Override
    public AdapterFontRVAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_font, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterFontRVAdapter.ViewHolder viewHolder, int i) {
        viewHolder.tv_font.setTypeface(listFont.get(i));
        viewHolder.tv_font.setText("Font");
        if(selectedIndex != i)
        {
            viewHolder.tv_font.setTextColor(context.getResources().getColor(R.color.colorPrimary));
        }
        else
        {
            viewHolder.tv_font.setTextColor(context.getResources().getColor(R.color.main_color));
        }
    }

    @Override
    public int getItemCount() {
        return listFont.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView tv_font;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_font = itemView.findViewById(R.id.text_font);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            try {
                int pos =getAbsoluteAdapterPosition();
                selectedIndex = pos;
                if(clickToFontTextItem != null)
                {
                    clickToFontTextItem.onClickFontTexItem(listFont.get(pos), pos);
                }
                notifyDataSetChanged();
            }catch (ArrayIndexOutOfBoundsException e)
            {
                e.printStackTrace();
            }
        }
    }
    public interface ClickToFontTextItem {
        void onClickFontTexItem(Typeface font, int posInList);
    }
}
