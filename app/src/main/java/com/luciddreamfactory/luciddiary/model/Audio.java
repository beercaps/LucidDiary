package com.luciddreamfactory.luciddiary.model;

/**
 * Created by kevinwetzel on 21.08.16.
 */
public class Audio {

    private long length;
    private String audioTitle;
    private String path;


    public Audio() {
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
