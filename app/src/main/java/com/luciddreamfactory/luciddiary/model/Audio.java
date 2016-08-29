package com.luciddreamfactory.luciddiary.model;

/**
 * Created by kevinwetzel on 21.08.16.
 */
public class Audio {

    long length;
    long size;
    String audioTitle;
    String path;

    public Audio() {
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public String getAudioTitle() {
        return audioTitle;
    }

    public void setAudioTitle(String audioTitle) {
        this.audioTitle = audioTitle;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public long getLength() {
        return length;
    }

    public void setLength(long length) {
        this.length = length;
    }
}
