package com.jge.homeeco.Models;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

@Entity(tableName = "prize")

public class Prize implements Parcelable {
    @PrimaryKey
    private int id;
    private int points;
    private String name;

    public Prize(){

    }




    public static final Creator<Prize> CREATOR = new Creator<Prize>() {
        @Override
        public Prize createFromParcel(Parcel in) {
            return new Prize(in);
        }

        @Override
        public Prize[] newArray(int size) {
            return new Prize[size];
        }
    };

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(int id){
        this.id = id;
    }

    public int getId(){
        return id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(points);
        parcel.writeInt(id);
        parcel.writeString(name);

    }
    private Prize(Parcel in) {
        id = in.readInt();
        points = in.readInt();
        name = in.readString();
    }
}
