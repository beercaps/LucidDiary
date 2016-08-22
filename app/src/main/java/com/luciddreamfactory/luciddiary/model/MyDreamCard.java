package com.luciddreamfactory.luciddiary.model;

import android.support.design.widget.TextInputEditText;
import android.view.View;

/**
 * Created by kevinwetzel on 22.08.16.
 */
public class MyDreamCard {

    private View myDreamCardView;
    private TextInputEditText et_dreamTitle;
    private TextInputEditText et_dreamContent;


    public MyDreamCard() {
    }

    public MyDreamCard(TextInputEditText et_dreamTitle, TextInputEditText et_dreamContent) {
        this.et_dreamTitle = et_dreamTitle;
        this.et_dreamContent = et_dreamContent;
    }

    public TextInputEditText getEt_dreamTitle() {
        return et_dreamTitle;
    }

    public void setEt_dreamTitle(TextInputEditText et_dreamTitle) {
        this.et_dreamTitle = et_dreamTitle;
    }

    public TextInputEditText getEt_dreamContent() {
        return et_dreamContent;
    }

    public void setEt_dreamContent(TextInputEditText et_dreamContent) {
        this.et_dreamContent = et_dreamContent;
    }

    public View getMyDreamCardView() {
        return myDreamCardView;
    }

    public void setMyDreamCardView(View myDreamCardView) {
        this.myDreamCardView = myDreamCardView;
    }
}
