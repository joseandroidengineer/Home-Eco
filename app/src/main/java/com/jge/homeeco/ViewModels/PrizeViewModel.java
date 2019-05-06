package com.jge.homeeco.ViewModels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import android.util.Log;

import com.jge.homeeco.Database.AppDatabase;
import com.jge.homeeco.Models.Prize;

import java.util.List;

public class PrizeViewModel extends AndroidViewModel {
    private static final String TAG = PrizeViewModel.class.getSimpleName();
    private LiveData<List<Prize>> prizes;
    private AppDatabase database;


    public PrizeViewModel(@NonNull Application application) {
        super(application);
        database = AppDatabase.getInstance(this.getApplication());
        Log.e(TAG, "Retrieving Prizes from Database");
        prizes = database.prizeDao().loadAllPrizes();
    }

    public LiveData<List<Prize>> getPrizes(){
        return prizes;
    }
}
