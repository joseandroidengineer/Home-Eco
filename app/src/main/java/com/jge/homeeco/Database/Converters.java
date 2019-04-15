package com.jge.homeeco.Database;

import android.arch.persistence.room.TypeConverter;

import com.jge.homeeco.Models.Chore;
import com.jge.homeeco.Models.Roommate;


public class Converters {

    @TypeConverter
    public static String fromChore(Chore chore) {
        String choreToString = String.valueOf(chore);
        return choreToString;
    }

    @TypeConverter
    public static String fromRoommate(Roommate roommate) {
        String roommateToString = String.valueOf(roommate);
        return roommateToString;
    }
}
