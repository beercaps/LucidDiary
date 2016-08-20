package com.luciddreamfactory.luciddiary.activities;


import android.app.ActionBar;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;

import com.luciddreamfactory.luciddiary.R;

import java.util.zip.Inflater;


public class NewDreamActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = NewDreamActivity.class.getSimpleName();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_dream);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);

        LayoutInflater inflater = getLayoutInflater();

        View actionBarButtons = inflater.inflate(R.layout.edit_event_custom_actionbar,
                new LinearLayout(this), false);
        View cancelActionView = actionBarButtons.findViewById(R.id.action_menu_cancel);
        cancelActionView.setOnClickListener(this);
        View doneActionView = actionBarButtons.findViewById(R.id.action_menu_save);
        doneActionView.setOnClickListener(this);


        myToolbar.addView(actionBarButtons);
        setSupportActionBar(myToolbar);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.action_menu_cancel:
                Log.d(TAG, "onClick: cancel"); break;
            case R.id.action_menu_save:
                Log.d(TAG, "onClick: save"); break;
        }
    }
}
