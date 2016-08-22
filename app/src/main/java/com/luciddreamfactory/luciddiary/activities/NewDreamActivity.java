package com.luciddreamfactory.luciddiary.activities;


import android.support.design.widget.TextInputEditText;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.luciddreamfactory.luciddiary.R;
import com.luciddreamfactory.luciddiary.model.MyDreamCard;

import java.util.ArrayList;


public class NewDreamActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = NewDreamActivity.class.getSimpleName();

    private EditText datePicker;
    private ViewGroup linearlayout_dreamcards;
    private ArrayList<MyDreamCard> myDreamCardsArrayList;
   // private ArrayList<View> dreamCardviewArrayList;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myDreamCardsArrayList = new ArrayList<>();
        //dreamCardviewArrayList = new ArrayList<>();
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
        // remove myDreamCardsArrayList.add(addDreamCard());
        addDreamCard(true);




    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.action_menu_cancel:
                Log.d(TAG, "onClick: cancel"); addDreamCard(true);  break;
            case R.id.action_menu_save:
                Log.d(TAG, "onClick: save"); readCards(myDreamCardsArrayList); break;
        }
    }

    //called when DatePicker button is clicked
    public void onDateChosen(View view) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    private void addDreamCard(boolean setFocus){
        View card = LayoutInflater.from(this).inflate(R.layout.new_dream_cardview, linearlayout_dreamcards, false);

        //get the EditTexts (currently position 4 and 8)
        final ArrayList<View> allViews = getAllChildren(card);
        //for (int i = 0; i < allViews.size(); i++) {
        //    Log.d(TAG, "addDreamCard: "+i+" "+allViews.get(i).toString());
        //}
        // CHANGE IF ADDED MORE VIEWS TO CARDVIEW!!!!

          final MyDreamCard myDreamCard = new MyDreamCard((TextInputEditText) allViews.get(4), (TextInputEditText) allViews.get(8));
          myDreamCard.setMyDreamCardView(card);
          myDreamCardsArrayList.add(myDreamCard);

        ((TextInputEditText) myDreamCard.getEt_dreamContent()).addTextChangedListener(new TextWatcher() {
            private String helper = "";
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!editable.toString().equals("")){
                    //when text is entered add a new card

                    if (helper.equals("")) {
                        helper = editable.toString();
                        addDreamCard(false);
                        //myDreamCardsArrayList.add(myDreamCard);
                        Log.d(TAG, "added dreamcard to List new Size "+ myDreamCardsArrayList.size());
                    }

                }else{
                        //remove last added card if it is empty
                        int index = myDreamCardsArrayList.indexOf(myDreamCard)+1;
                    //check if title or content of last added card is empty -> then delete
                        if (myDreamCardsArrayList.get(index).getEt_dreamContent().getText().toString().trim().length() == 0 &&
                            myDreamCardsArrayList.get(index).getEt_dreamTitle().getText().toString().trim().length() == 0   ) {

                            linearlayout_dreamcards.removeView(myDreamCardsArrayList.get(index).getMyDreamCardView());
                            myDreamCardsArrayList.remove(index);
                            Log.d(TAG, "after removed index "+(index+1));
                        }
                }
            }
        });


        linearlayout_dreamcards.addView(myDreamCard.getMyDreamCardView());




        // add space for the next card
        View space = LayoutInflater.from(this).inflate(R.layout.space, linearlayout_dreamcards, false);
        linearlayout_dreamcards.addView(space);

        //set focus to title
        if (setFocus) {
            myDreamCard.getEt_dreamTitle().requestFocus();
        }




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


    public void readCards(ArrayList<MyDreamCard> list){
        for (int i = 0; i <list.size() ; i++) {
            Log.d(TAG, "readCards: Title "+(i+1)+" "+list.get(i).getEt_dreamTitle().getText());
            Log.d(TAG, "readCards: Content "+(i+1)+" "+ list.get(i).getEt_dreamContent().getText());

        }
    }

}
