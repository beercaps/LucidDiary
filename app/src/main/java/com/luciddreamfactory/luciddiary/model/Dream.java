package com.luciddreamfactory.luciddiary.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by kevinwetzel on 21.08.16.
 */
public class Dream {

    private long dreamID;
    private String title;
    private String content;
    private Date date;
    private List<Tag> tags = new ArrayList<>();
    //private List<Audio> audio = new ArrayList<>();
    //private List<Drawing> drawings = new ArrayList<>();
    private boolean withoutDate;
    private boolean wihtoutTime;
    private int color;



    public Dream() {

    }

    public Dream(String title, String content, Date date) {
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTag(Tag tag) {
       tags.add(tag);
    }


    public void setTags(List<Tag> tags) {
        this.tags.addAll(tags);
    }

    public boolean isWithoutDate() {
        return withoutDate;
    }

    public void setWithoutDate(boolean withoutDate) {
        this.withoutDate = withoutDate;
    }

    public boolean isWithoutTime() {
        return wihtoutTime;
    }

    public void setWithoutTime(boolean wihtoutTime) {
        this.wihtoutTime = wihtoutTime;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

   /* public List<Audio> getAudio() {
        return audio;
    }

    public void setAudio(ArrayList<Audio> audio) {
        this.audio.addAll(audio);
    }

    public List<Drawing> getDrawings() {
        return drawings;
    }

    public void setDrawings(ArrayList<Drawing> drawings) {
        this.drawings.addAll(drawings);
    }*/

    public long getDreamID() {
        return dreamID;
    }

    public void setDreamID(long dreamID) {
        this.dreamID = dreamID;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(", ID: ");
        sb.append(this.getDreamID());
        sb.append(", Title: ");
        sb.append(this.getTitle());
        sb.append(", Content: ");
        sb.append(this.getContent());
        sb.append(", Tags:");
        sb.append(this.getTags().toString());
        sb.append(", Date: ");
        sb.append(this.getDate().toString());
        sb.append(" Without date: ");
        sb.append(this.isWithoutDate());
        sb.append(", Without time: ");
        sb.append(this.isWithoutTime());
        sb.append(", Color: ");
        sb.append(this.getColor());

        return sb.toString();
    }
}
