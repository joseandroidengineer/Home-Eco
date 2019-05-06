package com.jge.homeeco;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.jge.homeeco.Adapters.PrizeAdapter;
import com.jge.homeeco.Database.AppDatabase;
import com.jge.homeeco.Models.Chore;
import com.jge.homeeco.Models.Person;
import com.jge.homeeco.Models.Prize;
import com.jge.homeeco.ViewModels.PrizeViewModel;

import java.util.ArrayList;
import java.util.List;

public class PrizeActivity extends AppCompatActivity implements ListItemClickListener{

    private AppDatabase mAppDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prize);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mAppDatabase = AppDatabase.getInstance(getApplicationContext());
        final PrizeAdapter prizeAdapter = new PrizeAdapter(this);
        PrizeViewModel prizeViewModel = ViewModelProviders.of(this).get(PrizeViewModel.class);
        prizeViewModel.getPrizes().observe(this, new Observer<List<Prize>>() {
            @Override
            public void onChanged(@Nullable List<Prize> prizes) {
                ArrayList<Prize> prizeArrayList = new ArrayList<>(prizes);
                prizeAdapter.setPrizesData(prizeArrayList);
            }
        });
        RecyclerView recyclerView = findViewById(R.id.prize_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        prizeAdapter.notifyDataSetChanged();
        recyclerView.setAdapter(prizeAdapter);


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utilities.addPrize("Add Prize",
                        PrizeActivity.this,
                        "Add prize",
                        "Cancel",
                        "Prize has been added",
                        "Adding prize has been cancelled",
                        "You must add a name of the prize!").show();
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    private void setUpRecyclerView(){

    }

    @Override
    public void onListItemClick(Chore choreIndexClicked) {

    }

    @Override
    public void onListItemClick(Person personIndexClicked) {

    }

    @Override
    public void onListItemClick(Prize prizeItemClicked) {

    }
}
