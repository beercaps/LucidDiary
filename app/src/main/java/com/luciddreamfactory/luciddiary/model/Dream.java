package com.luciddreamfactory.luciddiary.model;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by kevinwetzel on 21.08.16.
 */
public class Dream {

    private String title;
    private String content;
    private Calendar date;
    private Calendar time;
    private ArrayList<Tag> tags;
    private ArrayList<Audio> audio;
    private ArrayList<Drawing> drawings;
    private boolean wihtoutDate;
    private boolean wihtoutTime;
    private int color;



    public Dream() {

    }

    public Dream(String title, String content, Calendar date) {
        this.title = title;
        this.content = content;
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Calendar getDate() {
        return date;
    }

    public void setDate(Calendar date) {
        this.date = date;
    }

    public ArrayList<Tag> getTags() {
        return tags;
    }

    public void setTag(Tag tag) {
       tags.add(tag);
    }

    public Calendar getTime() {
        return time;
    }

    public void setTime(Calendar time) {
        this.time = time;
    }

    public void setTags(ArrayList<Tag> tags) {
        this.tags.addAll(tags);
    }

    public boolean isWihtoutDate() {
        return wihtoutDate;
    }

    public void setWihtoutDate(boolean wihtoutDate) {
        this.wihtoutDate = wihtoutDate;
    }

    public boolean isWihtoutTime() {
        return wihtoutTime;
    }

    public void setWihtoutTime(boolean wihtoutTime) {
        this.wihtoutTime = wihtoutTime;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public ArrayList<Audio> getAudio() {
        return audio;
    }

    public void setAudio(ArrayList<Audio> audio) {
        this.audio.addAll(audio);
    }

    public ArrayList<Drawing> getDrawings() {
        return drawings;
    }

    public void setDrawings(ArrayList<Drawing> drawings) {
        this.drawings.addAll(drawings);
    }
}
