package com.via.android.winderzproject.data;

import android.net.Uri;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Session implements Serializable {
    String key;
    String title;
    String description;
    int windSpeed;
    String windOrientation;
    String waveSize;
    int wavePeriod;
    boolean favorite;
    String date;
    int hourSession;
    int minSession;
    String hour;
    String uri;



    public Session(){
        //Default constructor required for calls to Datasnapshot.getValue(Session.class)
    }

    public Session(String title, String description, int windSpeed, String windOrientation, String waveSize, int wavePeriod, boolean favorite, String date, String hour, int hourSession, int minSession, String uri) {
        this.title = title;
        this.description = description;
        this.windSpeed = windSpeed;
        this.windOrientation = windOrientation;
        this.waveSize = waveSize;
        this.wavePeriod = wavePeriod;
        this.favorite = favorite;
        this.date=date;
        this.hourSession=hourSession;
        this.minSession=minSession;
        this.hour=hour;
        this.uri = uri;
    }

    public Session(Session updatedSession) {
        this.key = updatedSession.key;
        this.title = updatedSession.title;
        this.description = updatedSession.description;
        this.windSpeed = updatedSession.windSpeed;
        this.windOrientation = updatedSession.windOrientation;
        this.waveSize = updatedSession.waveSize;
        this.wavePeriod = updatedSession.wavePeriod;
        this.favorite = updatedSession.favorite;
        this.date= updatedSession.date;
        this.hour= updatedSession.hour;
        this.hourSession = updatedSession.hourSession;
        this.minSession= updatedSession.minSession;
        this.uri = updatedSession.uri;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
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

    public int getHourSession() {
        return hourSession;
    }

    public void setHourSession(int hourSession) {this.hourSession = hourSession; }

    public int getMinSession() {
        return minSession;
    }

    public void setMinSession(int minSession) {this.minSession = minSession; }

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

    public int getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(int windSpeed) {
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


    public int getWavePeriod() {
        return wavePeriod;
    }

    public void setWavePeriod(int wavePeriod) {
        this.wavePeriod = wavePeriod;
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
                ", wavePeriod='" + wavePeriod + '\'' +
                ", favorite=" + favorite +
                ", date='" + date + '\'' +
                ", hour session='" + hourSession + '\'' +
                ", min session='" + minSession + '\'' +
                ", hour='" + hour + '\'' +
                ", uri='" + uri + '\'' +
                '}';
    }

    public Map<String, Object> toMap(){
        HashMap<String, Object> map = new HashMap<>();
        map.put("title", title);
        map.put("description", description);
        map.put("windSpeed", windSpeed);
        map.put("windOrientation", windOrientation);
        map.put("waveSize", waveSize);
        map.put("wavePeriod", wavePeriod);
        map.put("favorite", favorite);
        map.put("date", date);
        map.put("hour", hour);
        map.put("hourSession", hourSession);
        map.put("minSession", minSession);
        map.put("uri", uri);
        return map;
    }
}
