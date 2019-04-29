package com.jge.homeeco.Models;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

@Entity(tableName = "person")
public class Person implements Parcelable {
    private String name;

    @PrimaryKey(autoGenerate =  true)
    private int id;
    private int pointsAssigned;

    public Person(){

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPointsAssigned() {
        return pointsAssigned;
    }

    public void setPointsAssigned(int pointsAssigned) {
        this.pointsAssigned = pointsAssigned;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(name);
        parcel.writeInt(pointsAssigned);

    }

    public static final Parcelable.Creator<Person> CREATOR = new Parcelable.Creator<Person>() {
        public Person createFromParcel(Parcel in){
            return new Person(in);
        }

        public Person[] newArray(int size){
            return new Person[size];
        }
    };

    private Person(Parcel in){
        id = in.readInt();
        name = in.readString();
        pointsAssigned = in.readInt();
    }
}
