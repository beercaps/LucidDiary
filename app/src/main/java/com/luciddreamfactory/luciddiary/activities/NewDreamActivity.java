package com.luciddreamfactory.luciddiary.activities;


import android.support.v4.app.DialogFragment;
import android.support.v4.widget.Space;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.luciddreamfactory.luciddiary.R;

import java.util.ArrayList;


public class NewDreamActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = NewDreamActivity.class.getSimpleName();

    private EditText datePicker;
    private ViewGroup linearlayout_dreamcards;
    private ArrayList<EditText[]> dreamCardArrayList;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dreamCardArrayList = new ArrayList<>();
        setContentView(R.layout.activity_new_dream);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);

        LayoutInflater inflater = getLayoutInflater();

        View actionBarButtons = inflater.inflate(R.layout.done_cancel_custom_actionbar,
                new LinearLayout(this), false);
        View cancelActionView = actionBarButtons.findViewById(R.id.action_menu_cancel);
        cancelActionView.setOnClickListener(this);
        View doneActionView = actionBarButtons.findViewById(R.id.action_menu_save);
        doneActionView.setOnClickListener(this);
        myToolbar.addView(actionBarButtons);
        setSupportActionBar(myToolbar);

        datePicker = (EditText) findViewById(R.id.datePicker);
        datePicker.setText(R.string.last_night);

        linearlayout_dreamcards = (ViewGroup) findViewById(R.id.dream_cards);
        dreamCardArrayList.add(addDreamCard());




    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.action_menu_cancel:
                Log.d(TAG, "onClick: cancel"); dreamCardArrayList.add(addDreamCard());  break;
            case R.id.action_menu_save:
                Log.d(TAG, "onClick: save"); readCards(dreamCardArrayList); break;
        }
    }

    //called when DatePicker button is clicked
    public void onDateChosen(View view) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    private EditText[] addDreamCard(){
        EditText[] cardEditTexts = new EditText[2];

        View card = LayoutInflater.from(this).inflate(R.layout.new_dream_cardview, linearlayout_dreamcards, false);
        linearlayout_dreamcards.addView(card);

        //get the EditTexts (currently position 4 and 8)
        ArrayList<View> allViews = getAllChildren(card);
        //for (int i = 0; i < allViews.size(); i++) {
        //    Log.d(TAG, "addDreamCard: "+i+" "+allViews.get(i).toString());
        //}
        // CHANGE IF ADDED MORE VIEWS TO CARDVIEW!!!!
       // EditText et_title = (EditText) allViews.get(4);
       // EditText et_content = (EditText) allViews.get(8);

        cardEditTexts[0] = (EditText) allViews.get(4);
        cardEditTexts[1] = (EditText) allViews.get(8);


        // add space for the next card
        View space = LayoutInflater.from(this).inflate(R.layout.space, linearlayout_dreamcards, false);
        linearlayout_dreamcards.addView(space);

    return cardEditTexts;
    }

    private ArrayList<View> getAllChildren(View v) {

        if (!(v instanceof ViewGroup)) {
            ArrayList<View> viewArrayList = new ArrayList<View>();
            viewArrayList.add(v);
            return viewArrayList;
        }

        ArrayList<View> result = new ArrayList<View>();

        ViewGroup vg = (ViewGroup) v;
        for (int i = 0; i < vg.getChildCount(); i++) {

            View child = vg.getChildAt(i);

            ArrayList<View> viewArrayList = new ArrayList<View>();
            viewArrayList.add(v);
            viewArrayList.addAll(getAllChildren(child));

            result.addAll(viewArrayList);
        }
        return result;
    }


    public void readCards(ArrayList<EditText[]> list){
        for (int i = 0; i <list.size() ; i++) {
            Log.d(TAG, "readCards: Title "+(i+1)+" "+list.get(i)[0].getText());
            Log.d(TAG, "readCards: Content "+(i+1)+" "+ list.get(i)[1].getText());

        }
    }

}
