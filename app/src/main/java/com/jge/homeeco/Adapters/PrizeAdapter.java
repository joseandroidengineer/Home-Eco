package com.jge.homeeco.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jge.homeeco.ListItemClickListener;
import com.jge.homeeco.Models.Prize;
import com.jge.homeeco.R;

import java.util.ArrayList;

public class PrizeAdapter extends RecyclerView.Adapter<PrizeAdapterViewHolder> {
    private ArrayList<Prize> prizes;
    private ListItemClickListener mOnClickListener;

    public PrizeAdapter(ListItemClickListener mOnClickListener){
        this.mOnClickListener = mOnClickListener;
    }


    @NonNull
    @Override
    public PrizeAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        int layoutForListItem = R.layout.prize_list_item;
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(layoutForListItem, viewGroup,false);
        return new PrizeAdapterViewHolder(view, mOnClickListener, prizes);
    }

    @Override
    public void onBindViewHolder(@NonNull PrizeAdapterViewHolder prizeAdapterViewHolder, int i) {
        Prize prize = prizes.get(i);
        prizeAdapterViewHolder.nameOfPrizeTV.setText(prize.getName());
        //TODO: Need to fix this
//        prizeAdapterViewHolder.amountOfPoints.setText(prize.getPoints());

    }

    @Override
    public int getItemCount() {
        if(prizes == null) return 0;
        return prizes.size();
    }

    public void setPrizesData(ArrayList prizesData){
        prizes = prizesData;
        notifyDataSetChanged();
    }
}
