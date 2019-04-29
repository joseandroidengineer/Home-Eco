package com.jge.homeeco.Database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.jge.homeeco.Models.Person;

import java.util.List;

@Dao
public interface PersonDao {
    @Query("SELECT * FROM person ORDER BY id")
    LiveData<List<Person>> loadAllPersons();

    @Insert
    void insertPerson(Person person);

    @Update
    void updatePerson(Person person);

    @Delete
    void deletePerson(Person person);

    @Query("SELECT * FROM person WHERE id = :id")
    LiveData<Person> loadPersonById(int id);

}
