package com.jge.homeeco.Models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Roommate implements Parcelable {
    private String name;
    private int id;
    private int pointsAssigned;
    private ArrayList<Chore> chores;

    public Roommate(){

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

    public ArrayList<Chore> getChores() {
        return chores;
    }

    public void setChores(ArrayList<Chore> chores) {
        this.chores = chores;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(name);
        parcel.writeArray(chores.toArray());
        parcel.writeInt(pointsAssigned);

    }

    public static final Parcelable.Creator<Roommate> CREATOR = new Parcelable.Creator<Roommate>() {
        public Roommate createFromParcel(Parcel in){
            return new Roommate(in);
        }

        public Roommate[] newArray(int size){
            return new Roommate[size];
        }
    };

    private Roommate(Parcel in){
        id = in.readInt();
        name = in.readString();
        chores = in.readArrayList(Chore.class.getClassLoader());
        pointsAssigned = in.readInt();
    }
}
