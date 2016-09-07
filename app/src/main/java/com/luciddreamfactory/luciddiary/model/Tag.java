package com.luciddreamfactory.luciddiary.model;

/**
 * Created by kevinwetzel on 21.08.16.
 */
public class Tag {

   private long tagID;
   private  String tagName;

    public Tag() {
    }

    public Tag(String tagName) {
        this.tagName = tagName;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public long getTagID() {
        return tagID;
    }

    public void setTagID(long tagID) {
        this.tagID = tagID;
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append(" ID: ");
        sb.append(this.getTagID());
        sb.append(" Name: ");
        sb.append(this.getTagName());
        return sb.toString();
    }
}

