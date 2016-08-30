package com.luciddreamfactory.luciddiary.model;

/**
 * Created by kevinwetzel on 21.08.16.
 */
public class Tag {

   private  String tagName;
   private long amountUsed;

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public long getAmountUsed() {
        return amountUsed;
    }

    public void setAmountUsed(long amountUsed) {
        this.amountUsed = amountUsed;
    }
}
