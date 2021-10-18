package com.antkumachev.androidlab19.models;

import java.io.Serializable;
import java.sql.Time;

public class Note implements Serializable {
    private String caption;
    private String content;
    private Time creationTime;
    private Priority priority;

    public Note(String caption, String content, long creationTime, Priority priority) {

        this.caption = caption;
        this.content = content;
        this.creationTime = new Time(creationTime);
        this.priority = priority;
    }

    public Note(String caption, String content, long creationTime) {
        this(caption, content, creationTime, Priority.Default);
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

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }
}
