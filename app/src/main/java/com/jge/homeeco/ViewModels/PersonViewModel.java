package com.jge.homeeco.ViewModels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import android.util.Log;

import com.jge.homeeco.Database.AppDatabase;
import com.jge.homeeco.Models.Person;

import java.util.List;

public class PersonViewModel extends AndroidViewModel {
    private static final String TAG = PersonViewModel.class.getSimpleName();
    private LiveData<List<Person>> persons;
    private AppDatabase database;


    public PersonViewModel(@NonNull Application application) {
        super(application);
        database= AppDatabase.getInstance(this.getApplication());
        Log.e(TAG, "Retrieving People from Database");
        persons = database.personDao().loadAllPersons();
    }

    public LiveData<List<Person>> getPersons(){
        return persons;
    }
}
