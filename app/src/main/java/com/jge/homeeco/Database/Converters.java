package com.jge.homeeco.Database;

import android.arch.persistence.room.TypeConverter;

import com.jge.homeeco.Models.Chore;
import com.jge.homeeco.Models.Person;

import java.lang.reflect.Type;
import java.util.ArrayList;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


public class Converters {

    @TypeConverter
    public static ArrayList<Chore> fromStringChore(String value) {
        Type listType = new TypeToken<ArrayList<Chore>>() {}.getType();
        return new Gson().fromJson(value, listType);
    }

    @TypeConverter
    public static String fromArrayList(ArrayList<Chore> list) {
        Gson gson = new Gson();
        String json = gson.toJson(list);
        return json;
    }

    @TypeConverter
    public static String fromPerson(Person person) {
        String roommateToString = String.valueOf(person);
        return roommateToString;
    }
}
