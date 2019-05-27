package com.example.a1.bottombar;

import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder>{

    private ArrayList<CardItem> cardItem;
    private ItemAdapter(ArrayList<CardItem> item){
        cardItem = item;
    }
    private int mode;
    private boolean image;

    OnItemClickListener onItemClickListener;

    ItemAdapter(OnItemClickListener onItemClickListener, ArrayList<CardItem> item, int mode) {
        this.onItemClickListener = onItemClickListener;
        cardItem = item;
        this.mode = mode;
    }

    ItemAdapter(OnItemClickListener onItemClickListener, ArrayList<CardItem> item, int mode, boolean image) {
        this(onItemClickListener,item,mode);
        this.image = image;
    }

    public interface OnItemClickListener {
        void onItemClick(CardItem item, TextView v);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public ItemViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(mode, viewGroup, false);;
        ItemViewHolder tm = new ItemViewHolder(v);
        return tm;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder itemViewHolder, int i) {
        itemViewHolder.title.setText(cardItem.get(i).title);
        itemViewHolder.text.setText(cardItem.get(i).text);

        if (image) {
            itemViewHolder.imageView.setImageAlpha(80);
            itemViewHolder.imageView.setImageResource(cardItem.get(i).image);
        }
    }

    @Override
    public int getItemCount() {
        return cardItem.size();
    }

    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }


    public class ItemViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView title;
        TextView text;
        ImageView imageView;
        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        ItemViewHolder(View itemView) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.card);
            title = (TextView)itemView.findViewById(R.id.card_title);
            text = (TextView)itemView.findViewById(R.id.card_text);
            if (image) {
                imageView = (ImageView) itemView.findViewById(R.id.image);
            }

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CardItem item = cardItem.get(getLayoutPosition());
                    onItemClickListener.onItemClick(item, title);
                }
            });
        }

    }
}