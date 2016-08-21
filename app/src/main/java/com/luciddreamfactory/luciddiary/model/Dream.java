package com.luciddreamfactory.luciddiary.model;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by kevinwetzel on 21.08.16.
 */
public class Dream {

    String title;
    String content;
    Date date;
    ArrayList<Tag> tags;
    ArrayList<Category> categories;



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

    public ArrayList<Tag> getTags() {
        return tags;
    }

    public void setTag(Tag tag) {
       tags.add(tag);
    }

    public ArrayList<Category> getCategories() {
        return categories;
    }

    public void setCategory(Category category) {
        categories.add(category);
    }
}
