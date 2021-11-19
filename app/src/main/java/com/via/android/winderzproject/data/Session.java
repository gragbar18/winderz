package com.via.android.winderzproject.data;

public class Session {
    String key;
    String title;
    String description;
    String windSpeed;
    String windOrientation;
    String waveSize;
    String waveFrequency;
    boolean favorite;
    String date;
    String time;
    String hour;

    public Session(){
        //Default constructor required for calls to Datasnapshot.getValue(Session.class)
    }

    public Session(String title, String description, String windSpeed, String windOrientation, String waveSize, String waveFrequency, boolean favorite, String date, String hour, String time) {
        this.title = title;
        this.description = description;
        this.windSpeed = windSpeed;
        this.windOrientation = windOrientation;
        this.waveSize = waveSize;
        this.waveFrequency = waveFrequency;
        this.favorite = favorite;
        this.date=date;
        this.time=time;
        this.hour=hour;
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

    public String getTime() {
        return time;
    }

    public void setTime(String time) {this.time = time; }

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
                ", time='" + time + '\'' +
                ", hour='" + hour + '\'' +
                '}';
    }
}
