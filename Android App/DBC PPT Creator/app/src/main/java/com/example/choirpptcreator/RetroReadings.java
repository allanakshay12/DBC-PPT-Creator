package com.example.choirpptcreator;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class RetroReadings {

    @SerializedName("gospels")
    private List<String> gospels;

    @SerializedName("readings")
    private List<String> readings;

    @SerializedName("response")
    private List<String> response;

    public RetroReadings(List<String> gospels, List<String> readings, List<String> response) {
        this.gospels = gospels;
        this.readings = readings;
        this.response = response;
    }

    public List<String> getGospels() {
        return gospels;
    }

    public List<String> getReadings() {
        return readings;
    }

    public List<String> getResponse() {
        return response;
    }

    public void setGospels(List<String> gospels) {
        this.gospels = gospels;
    }

    public void setReadings(List<String> readings) {
        this.readings = readings;
    }

    public void setResponse(List<String> response) {
        this.response = response;
    }
}