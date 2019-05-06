package com.jge.homeeco.Adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.jge.homeeco.ListItemClickListener;
import com.jge.homeeco.Models.Prize;
import com.jge.homeeco.R;

import java.util.ArrayList;

public class PrizeAdapterViewHolder extends RecyclerView.ViewHolder {

    public TextView nameOfPrizeTV;
    public TextView amountOfPoints;

    public PrizeAdapterViewHolder(@NonNull View itemView, final ListItemClickListener mOnListItemClickListerner, final ArrayList<Prize> prizes) {
        super(itemView);
         nameOfPrizeTV = itemView.findViewById(R.id.prize_name);
         amountOfPoints = itemView.findViewById(R.id.prize_amount);
         itemView.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 int position = getAdapterPosition();
                 mOnListItemClickListerner.onListItemClick(prizes.get(position));
             }
         });
    }
}
