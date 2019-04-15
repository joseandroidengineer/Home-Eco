package com.jge.homeeco.Adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.jge.homeeco.ListItemClickListener;
import com.jge.homeeco.Models.Chore;
import com.jge.homeeco.R;

import java.util.ArrayList;

public class ChoreAdapterViewHolder extends RecyclerView.ViewHolder {

    private final ListItemClickListener mOnClickListener;
    private ArrayList<Chore> chores;
    public TextView choreNameTextView;
    public ChoreAdapterViewHolder(@NonNull View itemView, final ListItemClickListener mOnClickListener, final ArrayList<Chore> chores ) {
        super(itemView);
        this.mOnClickListener = mOnClickListener;
        this.chores = chores;
        choreNameTextView = itemView.findViewById(R.id.content);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = getAdapterPosition();
                mOnClickListener.onListItemClick(chores.get(position));
            }
        });
    }


}
