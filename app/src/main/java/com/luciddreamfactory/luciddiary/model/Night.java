package com.luciddreamfactory.luciddiary.model;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by kevinwetzel on 21.08.16.
 */
public class Night {
    Date date;
    String color;
    ArrayList<Dream> dreams;

    public Night() {
        dreams = new ArrayList<>();
    }

    public Night(Date date, String color, ArrayList<Dream> dreams) {
        this.date = date;
        this.color = color;
        this.dreams = dreams;
    }

    public ArrayList<Dream> getDreams() {
        return dreams;
    }

    public void setDreams(Dream dream) {
        dreams.add(dream);
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
