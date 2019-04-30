package com.jge.homeeco;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v7.widget.Toolbar;

import com.jge.homeeco.Database.AppDatabase;
import com.jge.homeeco.Models.Person;

public class PersonDetailActivity extends AppCompatActivity {

    AppDatabase database;
    Person person;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_detail);
        person = getIntent().getExtras().getParcelable("person");
        if(getActionBar()!= null){
            getActionBar().setTitle(person.getName());
        }
        database = AppDatabase.getInstance(this);



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
            //Should open up an alert box to add points to the user.
            return true;
        }else if (id == R.id.action_claim_prize) {

            Intent intent = new Intent(this, PrizeActivity.class);
            startActivity(intent);
            return true;
        }else if (id == R.id.action_remove_user) {
            //Should be able to remove and delete user from the Dao database.
            AppExecutors.getInstance().diskIO().execute(new Runnable() {
                @Override
                public void run() {
                    database.personDao().deletePerson(person);
                }
            });
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
