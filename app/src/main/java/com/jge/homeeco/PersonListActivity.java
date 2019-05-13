package com.jge.homeeco;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.jge.homeeco.Adapters.PersonAdapter;
import com.jge.homeeco.Models.Chore;
import com.jge.homeeco.Models.Person;
import com.jge.homeeco.Models.Prize;
import com.jge.homeeco.ViewModels.PersonViewModel;

import java.util.ArrayList;
import java.util.List;

public class PersonListActivity extends AppCompatActivity implements ListItemClickListener {

    private PersonViewModel personViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_list);
        RecyclerView recyclerView = findViewById(R.id.person_list_rv);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        final PersonAdapter personAdapter = new PersonAdapter(this);
        personViewModel = ViewModelProviders.of(this).get(PersonViewModel.class);

        personViewModel.getPersons().observe(this, new Observer<List<Person>>() {
            @Override
            public void onChanged(@Nullable List<Person> people) {
                personAdapter.setPersonData((ArrayList) people);
            }
        });
        recyclerView.setAdapter(personAdapter);
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
