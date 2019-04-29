package com.jge.homeeco.Database;

import android.arch.persistence.room.TypeConverter;

import com.jge.homeeco.Models.Chore;
import com.jge.homeeco.Models.Person;


public class Converters {

    @TypeConverter
    public static String fromChore(Chore chore) {
        String choreToString = String.valueOf(chore);
        return choreToString;
    }

    @TypeConverter
    public static String fromPerson(Person person) {
        String roommateToString = String.valueOf(person);
        return roommateToString;
    }
}
