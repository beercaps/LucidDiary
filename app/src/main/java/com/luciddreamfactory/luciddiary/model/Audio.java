package com.luciddreamfactory.luciddiary.model;

/**
 * Created by kevinwetzel on 21.08.16.
 */
public class Audio {

    long length;
    long size;

    public Audio() {
    }

    public Audio(long length, long size) {
        this.length = length;
        this.size = size;
    }

    public long getLength() {
        return length;
    }

    public void setLength(long length) {
        this.length = length;
    }
}
