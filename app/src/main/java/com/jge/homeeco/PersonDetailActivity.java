package com.jge.homeeco;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.jge.homeeco.Adapters.ChoreAdapter;
import com.jge.homeeco.Database.AppDatabase;
import com.jge.homeeco.Models.Chore;
import com.jge.homeeco.Models.Person;
import com.jge.homeeco.Models.Prize;
import com.jge.homeeco.ViewModels.ChoreViewModel;
import com.jge.homeeco.ViewModels.PersonViewModel;

import java.util.ArrayList;
import java.util.List;

public class PersonDetailActivity extends AppCompatActivity implements ListItemClickListener {

    private AppDatabase database;
    private Person person;
    private TextView amtOfPoints;
    private PersonViewModel personViewModel;
    private ArrayList<Chore> choreList;
    private ChoreViewModel choreViewModel;
    private RecyclerView mChoreRecyclerView;
    private ChoreAdapter mChoreAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_detail);
        LinearLayoutManager layoutManager  = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        person = getIntent().getExtras().getParcelable("person");
        personViewModel = ViewModelProviders.of(this).get(PersonViewModel.class);
        choreViewModel = ViewModelProviders.of(this).get(ChoreViewModel.class);
        mChoreRecyclerView  = findViewById(R.id.chores_assigned_list);
        mChoreRecyclerView.setLayoutManager(layoutManager);
        mChoreRecyclerView.setHasFixedSize(true);
        mChoreAdapter = new ChoreAdapter(this, false);
        if(getActionBar()!= null){
            getActionBar().setTitle(person.getName());
        }
        database = AppDatabase.getInstance(this);
        amtOfPoints = findViewById(R.id.amt_of_points);
    }

    @Override
    protected void onResume() {
        super.onResume();
        personViewModel.getPersonById(person.getId()).observe(this, new Observer<Person>() {
            @Override
            public void onChanged(@Nullable Person person) {
                if(amtOfPoints != null && person != null){
                    amtOfPoints.setText("Amount of points: " + person.getPointsAssigned());
                }
            }
        });

        choreViewModel.getChores().observe(this, new Observer<List<Chore>>() {
            @Override
            public void onChanged(@Nullable List<Chore> chores) {
                choreList = (ArrayList)chores;
                mChoreAdapter.setChoreData(choreList,getBaseContext());
                mChoreAdapter.notifyDataSetChanged();
            }
        });
        mChoreRecyclerView.setAdapter(mChoreAdapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.person_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_award_points) {
            AlertDialog alertDialog = Utilities.addPoints("Add Points", this,"Add points", "Cancel","Points added", person, "Adding points cancelled","Make sure to add points",amtOfPoints, personViewModel ).create();
            alertDialog.show();
            return true;
        }else if (id == R.id.action_claim_prize) {

            Intent intent = new Intent(this, PrizeActivity.class);
            startActivity(intent);
            return true;
        }else if (id == R.id.action_remove_user) {
            //Should be able to remove and delete user from the Dao database.
            personViewModel.deletePerson(person);
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt("points",person.getPointsAssigned());
        super.onSaveInstanceState(outState);
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
