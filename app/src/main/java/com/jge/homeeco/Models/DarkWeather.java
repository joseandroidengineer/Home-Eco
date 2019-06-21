package com.jge.homeeco.Models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class DarkWeather implements Parcelable {

    @SerializedName("temperature")
    private float temperature;

    @SerializedName("icon")
    private String icon;

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(float temperature) {
        this.temperature = temperature;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeDouble(temperature);
        parcel.writeString(icon);

    }

    public static final Parcelable.Creator<DarkWeather> CREATOR = new Parcelable.Creator<DarkWeather>() {
        public DarkWeather createFromParcel(Parcel in){
            return new DarkWeather(in);
        }

        public DarkWeather[] newArray(int size){
            return new DarkWeather[size];
        }
    };

    private DarkWeather(Parcel in){
        temperature = in.readFloat();
        icon = in.readString();
    }
}
