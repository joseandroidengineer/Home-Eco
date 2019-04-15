package com.jge.homeeco;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import android.util.Log;

import com.jge.homeeco.Database.AppDatabase;
import com.jge.homeeco.Models.Chore;

import java.util.List;

public class ChoreViewModel extends AndroidViewModel {
    private static final String TAG = ChoreViewModel.class.getSimpleName();
    private LiveData<List<Chore>> chores;

    public ChoreViewModel(@NonNull Application application) {
        super(application);
        AppDatabase database= AppDatabase.getInstance(this.getApplication());
        Log.e(TAG, "Retrieving Chores from Database");
        chores = database.choreDao().loadAllChores();

    }

    public LiveData<List<Chore>> getChores(){
        return chores;
    }
}
