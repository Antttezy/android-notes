package com.antkumachev.androidlab19.models;

import java.io.Serializable;
import java.sql.Time;

public class Note implements Serializable {
    private String caption;
    private String content;
    private Time creationTime;

    public Note(String caption, String content, long creationTime) {

        this.caption = caption;
        this.content = content;
        this.creationTime = new Time(creationTime);
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Time getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Time creationTime) {
        this.creationTime = creationTime;
    }
}
