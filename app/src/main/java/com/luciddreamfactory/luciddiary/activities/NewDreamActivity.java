package com.luciddreamfactory.luciddiary.activities;


import android.app.TimePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.MultiAutoCompleteTextView;
import android.widget.ScrollView;
import android.widget.TimePicker;
import android.widget.ToggleButton;

import com.android.colorpicker.ColorPickerDialog;
import com.android.colorpicker.ColorPickerSwatch;
import com.android.ex.chips.BaseRecipientAdapter;
import com.android.ex.chips.RecipientEditTextView;
import com.android.ex.chips.RecipientEntry;
import com.luciddreamfactory.luciddiary.R;
import com.luciddreamfactory.luciddiary.interfaces.DatePickerCallBack;
import com.luciddreamfactory.luciddiary.model.Dream;

import java.text.SimpleDateFormat;
import java.util.Calendar;


public class NewDreamActivity extends AppCompatActivity implements View.OnClickListener,
                                                                   CompoundButton.OnCheckedChangeListener,
                                                                   TimePickerDialog.OnTimeSetListener,
        RecipientEditTextView.RecipientChipAddedListener{
    private static final String TAG = NewDreamActivity.class.getSimpleName();

    private ImageButton colorPicker;
    private RecipientEditTextView tokens;
    private ToggleButton withoutDate;
    private ToggleButton withoutTime;
    private ScrollView newDreamScrollView;

    private EditText datePicker;
    private EditText dreamTitle;
    private EditText dreamContent;
    private EditText timePicker;
    private Dream dream;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dream = new Dream();

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

        // Initialisierungen von Widgets
        datePicker = (EditText) findViewById(R.id.et_datePicker);
        datePicker.setText(getFormattedDate(getCalendarOfYesterday()));
        datePicker.setOnClickListener(this);
        dream.setDate(getCalendarOfYesterday());

        colorPicker = (ImageButton) findViewById(R.id.bt_color_picker);
        colorPicker.setOnClickListener(this);

        timePicker = (EditText) findViewById(R.id.et_time_picker);
        timePicker.setOnClickListener(this);

        withoutDate = (ToggleButton) findViewById(R.id.toggle_without_Date);
        withoutDate.setOnCheckedChangeListener(this);

        withoutTime = (ToggleButton) findViewById(R.id.toggle_without_time);
        withoutTime.setOnCheckedChangeListener(this);

        tokens = (RecipientEditTextView) findViewById(R.id.ret_tags);

        tokens.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());
      //  tokens.setAdapter(baseadapter);
        tokens.setRecipientChipAddedListener(this);

        //TODO chips zum laufen bringen

        dreamContent = (EditText) findViewById(R.id.et_dreamcontent);
        dreamTitle = (EditText) findViewById(R.id.et_dream_title);

        //TODO FAB der sich ausklappt für FARBE, SPEECH und evtl nächster Traum erfassen


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.action_menu_cancel:
                Log.d(TAG, "onClick: cancel");
                finish();
                break;
            
            case R.id.action_menu_save:
                Log.d(TAG, "onClick: save");  
                break;

            case R.id.bt_color_picker:
                Log.d(TAG, "onClick: color Picker");
                showColorPicker();
                break;

            case R.id.et_datePicker:
                Log.d(TAG, "onClick: datePicker");
                showDatePicker();
                break;

            case R.id.et_time_picker:
                Log.d(TAG, "onClick: timePicker");
                showTimePicker();
                break;
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
        switch (compoundButton.getId()) {
            case R.id.toggle_without_Date:
                Log.d(TAG, "onClick: toggleButton without Date");

                if (isChecked) {
                    Log.d(TAG, "onCheckedChanged: Date checked"); //on
                    datePicker.setEnabled(true);
                    dream.setWihtoutDate(false);

                } else {
                    Log.d(TAG, "onCheckedChanged: Date is not checked"); //off
                    datePicker.setEnabled(false);
                    dream.setWihtoutDate(true);
                }
                break;
            /////////////////////
            case R.id.toggle_without_time:
                Log.d(TAG, "onCheckedChanged: without Time");
                if (isChecked){
                    Log.d(TAG, "onCheckedChanged: Time checked");
                    timePicker.setEnabled(true);
                    dream.setWihtoutTime(false);
                }else{
                    Log.d(TAG, "onCheckedChanged: Time not checked");
                    timePicker.setEnabled(false);
                    dream.setWihtoutTime(true);
                }
                break;
        }
    }



    //called when DatePicker button is clicked
    public void showDatePicker() {
        DatePickerFragment newFragment = new DatePickerFragment();
        newFragment.setCallback(new DatePickerCallBack() {
            @Override
            public void onDateChosen(Calendar calendar) {
                datePicker.setText(getFormattedDate(calendar));
                dream.setDate(calendar);
            }
        });
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
                dream.setColor(color);
            }
        });
        colorPickerDialog.show(getFragmentManager(), "colorPicker");
    }

    private void showTimePicker(){
        TimePickerDialog timePickerDialog = new TimePickerDialog(
                this, this, 0, 0, true);

        timePickerDialog.show();
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

    private String getFormattedDate(Calendar calendar){
        SimpleDateFormat sdf = new SimpleDateFormat("EEE, MMM d, yyyy");
        Log.d(TAG, "getFormattedDate: "+ sdf.format(calendar.getTime()));
        return sdf.format(calendar.getTime());
    }

    private Calendar getCalendarOfYesterday(){
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, -1);
        Log.d(TAG, "getCalendarOfYesterday: "+ cal.toString());
        return cal;
    }


    private void saveDream() {
        //has date ->ignore former added Date if false
        //has time ->ignore former added time if false

        dream.setTitle(dreamTitle.getText().toString().trim());
        dream.setContent(dreamContent.getText().toString().trim());
      //  dream.setTime(timePicker.getText().);
        //TODO add tags
        //TODO add time

    }

    @Override
    public void onTimeSet(TimePicker timePicker, int i, int i1) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, timePicker.getHour());
        cal.set(Calendar.MINUTE, timePicker.getMinute());
        dream.setTime(cal);

        StringBuilder sb = new StringBuilder();

        //add second 0 if hour or minute is 0 (0:0 -> 00:00)
        if (timePicker.getHour()== 0){
            sb.append("00");
        }else{
            sb.append(timePicker.getHour());
        }
        sb.append(":");
        if (timePicker.getMinute() == 0){
            sb.append("00");
        }else{
            sb.append(timePicker.getMinute());
        }

        this.timePicker.setText(sb.toString());

        Log.d(TAG, "onTimeSet: "+cal.toString());
    }

    @Override
    public void onRecipientChipAdded(RecipientEntry entry) {

    }
}

