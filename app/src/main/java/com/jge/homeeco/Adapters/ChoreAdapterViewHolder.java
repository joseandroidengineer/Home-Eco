package com.jge.homeeco.Adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.jge.homeeco.ListItemClickListener;
import com.jge.homeeco.Models.Chore;
import com.jge.homeeco.R;

import java.util.ArrayList;

public class ChoreAdapterViewHolder extends RecyclerView.ViewHolder {

    public TextView choreNameTextView;
    public CheckBox choreCheckBox;
    public ChoreAdapterViewHolder(@NonNull View itemView, final ListItemClickListener mOnClickListener, final ArrayList<Chore> chores ) {
        super(itemView);
        choreNameTextView = itemView.findViewById(R.id.content);
        choreCheckBox = itemView.findViewById(R.id.checkbox);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = getAdapterPosition();
                mOnClickListener.onListItemClick(chores.get(position));
            }
        });
    }


}
