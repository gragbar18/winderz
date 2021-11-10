package com.via.android.winderzproject.data;

public class Session {
    String title;
    String description;
    String windSpeed;
    String windOrientation;
    String waveSize;
    String waveFrequency;

    public Session(String title, String description, String windSpeed, String windOrientation, String waveSize, String waveFrequency) {
        this.title = title;
        this.description = description;
        this.windSpeed = windSpeed;
        this.windOrientation = windOrientation;
        this.waveSize = waveSize;
        this.waveFrequency = waveFrequency;
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
}
