package com.jge.homeeco.Adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.jge.homeeco.ListItemClickListener;
import com.jge.homeeco.Models.Person;
import com.jge.homeeco.R;

import java.util.ArrayList;

public class PersonAdapterViewHolder extends RecyclerView.ViewHolder {

    public TextView personNameTextView;
    public PersonAdapterViewHolder(@NonNull View itemView, final ListItemClickListener mOnClickListener, final ArrayList<Person> persons) {
        super(itemView);
        personNameTextView = itemView.findViewById(R.id.person_name_tv);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = getAdapterPosition();
                mOnClickListener.onListItemClick(persons.get(position));
            }
        });
    }
}
