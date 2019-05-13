package com.jge.homeeco;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.jge.homeeco.Database.AppDatabase;
import com.jge.homeeco.Models.Person;
import com.jge.homeeco.ViewModels.PersonViewModel;

public class PersonDetailActivity extends AppCompatActivity {

    private AppDatabase database;
    private Person person;
    private TextView amtOfPoints;
    private PersonViewModel personViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_detail);
        person = getIntent().getExtras().getParcelable("person");
        personViewModel = ViewModelProviders.of(this).get(PersonViewModel.class);
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
            //TODO: Need to fix saving the points correctly. May need to implement onSavedInstance.
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
}
