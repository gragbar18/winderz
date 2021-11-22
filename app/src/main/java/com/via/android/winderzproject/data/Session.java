package com.via.android.winderzproject.data;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Session implements Serializable {
    String key;
    String title;
    String description;
    String windSpeed;
    String windOrientation;
    String waveSize;
    String waveFrequency;
    boolean favorite;
    String date;
    String hourSession;
    String minSession;
    String hour;

    public Session(){
        //Default constructor required for calls to Datasnapshot.getValue(Session.class)
    }

    public Session(String title, String description, String windSpeed, String windOrientation, String waveSize, String waveFrequency, boolean favorite, String date, String hour, String hourSession, String minSession) {
        this.title = title;
        this.description = description;
        this.windSpeed = windSpeed;
        this.windOrientation = windOrientation;
        this.waveSize = waveSize;
        this.waveFrequency = waveFrequency;
        this.favorite = favorite;
        this.date=date;
        this.hourSession=hourSession;
        this.minSession=minSession;
        this.hour=hour;
    }

    public Session(Session updatedSession) {
        this.title = updatedSession.title;
        this.description = updatedSession.description;
        this.windSpeed = updatedSession.windSpeed;
        this.windOrientation = updatedSession.windOrientation;
        this.waveSize = updatedSession.waveSize;
        this.waveFrequency = updatedSession.waveFrequency;
        this.favorite = updatedSession.favorite;
        this.date= updatedSession.date;
        this.hour= updatedSession.hour;
        this.hourSession=hourSession;
        this.minSession=minSession;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {this.date = date; }

    public String getHourSession() {
        return hourSession;
    }

    public void setHourSession(String hourSession) {this.hourSession = hourSession; }

    public String getMinSession() {
        return minSession;
    }

    public void setMinSession(String minSession) {this.minSession = minSession; }

    public boolean getFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
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

    public String getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(String windSpeed) {
        this.windSpeed = windSpeed;
    }

    public String getWindOrientation() {
        return windOrientation;
    }

    public void setWindOrientation(String windOrientation) {
        this.windOrientation = windOrientation;
    }

    public String getWaveSize() {
        return waveSize;
    }

    public void setWaveSize(String waveSize) {
        this.waveSize = waveSize;
    }

    public String getWaveFrequency() {
        return waveFrequency;
    }

    public void setWaveFrequency(String waveFrequency) {
        this.waveFrequency = waveFrequency;
    }

    @NonNull
    @Override
    public String toString() {
        return "Session{" +
                "key='" + key + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", windSpeed='" + windSpeed + '\'' +
                ", windOrientation='" + windOrientation + '\'' +
                ", waveSize='" + waveSize + '\'' +
                ", waveFrequency='" + waveFrequency + '\'' +
                ", favorite=" + favorite +
                ", date='" + date + '\'' +
                ", hour session='" + hourSession + '\'' +
                ", min session='" + minSession + '\'' +
                ", hour='" + hour + '\'' +
                '}';
    }

    public Map<String, Object> toMap(){
        HashMap<String, Object> map = new HashMap<>();
        map.put("title", title);
        map.put("description", description);
        map.put("windSpeed", windSpeed);
        map.put("windOrientation", windOrientation);
        map.put("waveSize", waveSize);
        map.put("waveFrequency", waveFrequency);
        map.put("favorite", favorite);
        map.put("date", date);
        map.put("hour", hour);
        map.put("hourSession", date);
        map.put("minSession", hour);
        return map;
    }
}
