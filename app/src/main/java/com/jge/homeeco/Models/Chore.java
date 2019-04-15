package com.jge.homeeco.Models;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

@Entity(tableName = "chore")

public class Chore implements Parcelable {



    @PrimaryKey(autoGenerate = true)
    private int id;
    private String roommateAssigned;
    private String title;
    private String description;
    private boolean completed;

    public Chore(){

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }



    public String getRoommateAssigned() {
        return roommateAssigned;
    }

    public void setRoommateAssigned(String roommateAssigned) {
        this.roommateAssigned = roommateAssigned;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(title);
        parcel.writeString(roommateAssigned);
        parcel.writeString(description);

    }

    public static final Parcelable.Creator<Chore> CREATOR = new Parcelable.Creator<Chore>() {
        public Chore createFromParcel(Parcel in){
            return new Chore(in);
        }

        public Chore[] newArray(int size){
            return new Chore[size];
        }
    };

    public Chore(Parcel in){
        id = in.readInt();
        title = in.readString();
        roommateAssigned = in.readString();
        description = in.readString();
    }
}
