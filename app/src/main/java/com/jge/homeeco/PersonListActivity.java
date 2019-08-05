package com.jge.homeeco;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.jge.homeeco.Adapters.PersonAdapter;
import com.jge.homeeco.Database.Converters;
import com.jge.homeeco.Models.Chore;
import com.jge.homeeco.Models.Person;
import com.jge.homeeco.Models.Prize;
import com.jge.homeeco.ViewModels.ChoreViewModel;
import com.jge.homeeco.ViewModels.PersonViewModel;

import java.util.ArrayList;
import java.util.List;

public class PersonListActivity extends AppCompatActivity implements ListItemClickListener {

    private PersonViewModel personViewModel;
    private ChoreViewModel choreViewModel;
    private Chore choreAssigned;
    private int id;

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
        choreViewModel = ViewModelProviders.of(this).get(ChoreViewModel.class);
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("choreIdBundle");
        id = bundle.getInt("choreId");
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
    public void onListItemClick(final Person personIndexClicked) {
        choreViewModel.getChoreById(id).observe(this, new Observer<Chore>() {
            @Override
            public void onChanged(@Nullable Chore chore) {
                chore.setRoommateAssigned(personIndexClicked.getName());
                ArrayList<Chore> choreArrayList;
                String stringChoreList;
                if(personIndexClicked.getChoresAssigned() == null){
                    choreArrayList = new ArrayList<>();
                }else{
                    stringChoreList = personIndexClicked.getChoresAssigned();
                    choreArrayList = Converters.fromStringChore(stringChoreList);
                }
                choreArrayList.add(chore);
                String string = Converters.fromArrayList(choreArrayList);
                personIndexClicked.setChoresAssigned(string);
                personViewModel.updatePerson(personIndexClicked);
                Toast.makeText(getApplicationContext(),chore.getTitle() +" assigned to "+personIndexClicked.getName(),Toast.LENGTH_LONG).show();
            }
        });

        Log.e("PERSON:", personIndexClicked.getName());
        onBackPressed();


    }

    @Override
    public void onListItemClick(Prize prizeItemClicked) {

    }
}
