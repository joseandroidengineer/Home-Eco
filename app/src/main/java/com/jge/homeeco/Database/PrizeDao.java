package com.jge.homeeco.Database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.jge.homeeco.Models.Prize;

import java.util.List;

@Dao
public interface PrizeDao {
    @Query("SELECT * FROM prize ORDER BY id")
    LiveData<List<Prize>> loadAllPrizes();

    @Insert
    void insertPrize(Prize prize);

    @Update
    void updatePrize(Prize prize);

    @Delete
    void deletePrize(Prize prize);

    @Query("SELECT * FROM prize WHERE id = :id")
    LiveData<Prize> loadPrizeById(int id);

}
