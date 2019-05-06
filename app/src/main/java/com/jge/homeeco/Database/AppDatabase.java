package com.jge.homeeco.Database;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.db.SupportSQLiteOpenHelper;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.DatabaseConfiguration;
import android.arch.persistence.room.InvalidationTracker;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.arch.persistence.room.migration.Migration;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.jge.homeeco.Models.Chore;
import com.jge.homeeco.Models.Person;
import com.jge.homeeco.Models.Prize;

@Database(entities = {Chore.class, Person.class, Prize.class}, version = 3, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {


    private static final String LOG_TAG = AppDatabase.class.getSimpleName();
    private static final Object LOCK = new Object();
    private static final String DATABASE_NAME = "choredatabase";
    private static AppDatabase sInstance;

    public static AppDatabase getInstance(Context context){
        if(sInstance == null){
            synchronized (LOCK){
                Log.d(LOG_TAG, "Creating new database instance");
                sInstance = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, AppDatabase.DATABASE_NAME).addMigrations(MIGRATION_2_3).build();
            }
        }
        Log.e(LOG_TAG, "Getting the database instance");
        return sInstance;
    }

    static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL("CREATE TABLE IF NOT EXISTS 'person'('id' INTEGER, PRIMARY KEY('id'))");
        }
    };

    static final Migration MIGRATION_2_3 = new Migration(2,3){
        @Override
        public void migrate(SupportSQLiteDatabase database){
            database.execSQL("CREATE TABLE IF NOT EXISTS 'prize'('id' INTEGER NOT NULL," +"'name' TEXT,"+"'points' INTEGER NOT NULL, PRIMARY KEY('id'))");
        }
    };



    public abstract ChoreDao choreDao();

    public abstract PersonDao personDao();

    public abstract PrizeDao prizeDao();
}
