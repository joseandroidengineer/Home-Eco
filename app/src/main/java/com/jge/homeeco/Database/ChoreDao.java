package com.jge.homeeco.Database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.jge.homeeco.Models.Chore;

import java.util.List;

@Dao
public interface ChoreDao {
    @Query("SELECT * FROM chore ORDER BY id")
    LiveData<List<Chore>> loadAllChores();

    @Insert
    void insertChore(Chore chore);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateChore(Chore chore);

    @Delete
    void deleteChore(Chore chore);

    @Query("SELECT * FROM chore WHERE id = :id")
    LiveData<Chore> loadChoreById(int id);
}
