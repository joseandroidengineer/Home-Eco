package com.jge.homeeco.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jge.homeeco.ListItemClickListener;
import com.jge.homeeco.Models.Person;
import com.jge.homeeco.R;

import java.util.ArrayList;

public class PersonAdapter extends RecyclerView.Adapter<PersonAdapterViewHolder> {
    private ArrayList<Person> persons;
    private ListItemClickListener mOnClickListener;

    public PersonAdapter(ListItemClickListener mOnClickListener){
        this.mOnClickListener = mOnClickListener;
    }

    @NonNull
    @Override
    public PersonAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        int layoutForListItem = R.layout.person_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(layoutForListItem,viewGroup,false);
        return new PersonAdapterViewHolder(view, mOnClickListener, persons);
    }

    @Override
    public void onBindViewHolder(@NonNull PersonAdapterViewHolder personAdapterViewHolder, int i) {
        Person person = persons.get(i);
        personAdapterViewHolder.personNameTextView.setText(person.getName());
    }

    @Override
    public int getItemCount() {
        if(persons == null) return 0;
        return persons.size();
    }

    public void setPersonData(ArrayList personData){
        persons = personData;
        notifyDataSetChanged();
    }
}
