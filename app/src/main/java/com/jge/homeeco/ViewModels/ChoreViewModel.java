package com.jge.homeeco.ViewModels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import android.util.Log;

import com.jge.homeeco.AppExecutors;
import com.jge.homeeco.Database.AppDatabase;
import com.jge.homeeco.Models.Chore;

import java.util.List;

public class ChoreViewModel extends AndroidViewModel {
    private static final String TAG = ChoreViewModel.class.getSimpleName();
    private LiveData<List<Chore>> chores;
    private AppDatabase database;

    public ChoreViewModel(@NonNull Application application) {
        super(application);
        database= AppDatabase.getInstance(this.getApplication());
        Log.e(TAG, "Retrieving Chores from Database");
        chores = database.choreDao().loadAllChores();
    }

    public LiveData<List<Chore>> getChores(){
        return chores;
    }

    public void updateChore(final Chore chore){
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                database.choreDao().updateChore(chore);
            }
        });
    }

    public LiveData<Chore> getChoreById(int id){
        return database.choreDao().loadChoreById(id);
    }
}
