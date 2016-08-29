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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.MultiAutoCompleteTextView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

import com.android.colorpicker.ColorPickerDialog;
import com.android.colorpicker.ColorPickerSwatch;
import com.android.ex.chips.BaseRecipientAdapter;
import com.android.ex.chips.RecipientEditTextView;
import com.luciddreamfactory.luciddiary.R;
import com.luciddreamfactory.luciddiary.model.MyDreamCard;

import java.util.ArrayList;


public class NewDreamActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = NewDreamActivity.class.getSimpleName();

    private EditText datePicker;
    private ImageButton colorPicker;

    private ScrollView newDreamScrollView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_new_dream);
        newDreamScrollView = (ScrollView) findViewById(R.id.new_dream_scroll_view);

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

        colorPicker = (ImageButton) findViewById(R.id.bt_color_picker);
        colorPicker.setOnClickListener(this);



        RecipientEditTextView myRetv =
                (RecipientEditTextView) findViewById(R.id.ret_tags);

        myRetv.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());

        myRetv.setAdapter(new BaseRecipientAdapter(this));



    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.action_menu_cancel:
                Log.d(TAG, "onClick: cancel");
                finish();
                break;
            case R.id.action_menu_save:
                Log.d(TAG, "onClick: save");  break;

            case R.id.bt_color_picker:
                Log.d(TAG, "onClick: color Picker");
                showColorPicker();



        }
    }

    //called when DatePicker button is clicked
    public void onDateChosen(View view) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    private void showColorPicker(){
        int[] colorsArray = createColorArray();
        ColorPickerDialog colorPickerDialog = new ColorPickerDialog();
        colorPickerDialog.initialize(R.string.color_title, colorsArray,0, 5,colorsArray.length );
        colorPickerDialog.setOnColorSelectedListener(new ColorPickerSwatch.OnColorSelectedListener() {
            @Override
            public void onColorSelected(int color) {
                Log.d(TAG, "onColorSelected: "+color);
                newDreamScrollView.setBackgroundColor(color);
            }
        });
        colorPickerDialog.show(getFragmentManager(), "colorPicker");
    }

private int[] createColorArray(){
    int[] colors = {
            getColor(R.color.red),
            getColor(R.color.pink),
            getColor(R.color.purple),
            getColor(R.color.deep_purple),
            getColor(R.color.indigo),
            getColor(R.color.blue),
            getColor(R.color.light_blue),
            getColor(R.color.cyan),
            getColor(R.color.teal),
            getColor( R.color.green),
            getColor(R.color.light_green),
            getColor(R.color.lime),
            getColor(R.color.yellow),
            getColor(R.color.amber),
            getColor(R.color.orange),
            getColor(R.color.deep_orange),
            getColor(R.color.brown),
            getColor(R.color.grey),
            getColor(R.color.blue_grey),
            getColor(R.color.white)
    };

    return colors;

}




}

