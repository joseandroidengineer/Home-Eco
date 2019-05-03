package com.jge.homeeco.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import com.jge.homeeco.AppExecutors;
import com.jge.homeeco.Database.AppDatabase;
import com.jge.homeeco.ListItemClickListener;
import com.jge.homeeco.Models.Chore;
import com.jge.homeeco.R;

import java.util.ArrayList;

public class ChoreAdapter  extends RecyclerView.Adapter<ChoreAdapterViewHolder>{

    private ArrayList<Chore> chores;
    private ListItemClickListener mOnClickListener;
    private Context context;
    private AppDatabase mChoreDatabase;
    private boolean mPane;

    public ChoreAdapter(ListItemClickListener mOnClickListener, boolean mPane){
        this.mOnClickListener = mOnClickListener;
        this.mPane = mPane;
    }

    @NonNull
    @Override
    public ChoreAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.chore_list_content;
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(layoutIdForListItem,viewGroup, false);
        return new ChoreAdapterViewHolder(view, mOnClickListener, chores);
    }

    @Override
    public void onBindViewHolder(@NonNull ChoreAdapterViewHolder choreAdapterViewHolder, int i) {
        final Chore chore = chores.get(i);
        mChoreDatabase = AppDatabase.getInstance(context);
        choreAdapterViewHolder.choreCheckBox.setChecked(chore.isCompleted());
        choreAdapterViewHolder.choreCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                chore.setCompleted(b);
                AppExecutors.getInstance().diskIO().execute(new Runnable() {
                    @Override
                    public void run() {
                        mChoreDatabase.choreDao().updateChore(chore);
                    }
                });
            }
        });
        choreAdapterViewHolder.choreNameTextView.setText(chore.getTitle());
    }

    @Override
    public int getItemCount() {
        if(chores == null) return 0;
        return chores.size();

    }
    public void setChoreData(ArrayList<Chore> choreData, Context context){
        chores = choreData;
        this.context = context;
        notifyDataSetChanged();
    }
}
