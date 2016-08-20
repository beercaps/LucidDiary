package com.luciddreamfactory.luciddiary.activities;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.widget.DatePicker;
import android.widget.EditText;

import com.luciddreamfactory.luciddiary.R;

public class DatePickerFragment extends DialogFragment
        implements DatePickerDialog.OnDateSetListener {
    private static final String TAG = DatePickerFragment.class.getSimpleName();

    private  int year;
    private  int month;
    private  int day;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH)-1;

        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
        Log.d(TAG, "onDateSet: day "+ day);
        Log.d(TAG, "onDateSet: day "+ view.getDayOfMonth());
        // Do something with the date chosen by the user
        EditText et_datePicker= (EditText) getActivity().findViewById(R.id.datePicker);
        if (view.getDayOfMonth() == this.day && view.getMonth()== this.month && view.getYear()==this.year){
            et_datePicker.setText(R.string.last_night);

        }else{
            et_datePicker.setText(view.getDayOfMonth()+"."+view.getMonth()+"."+view.getYear());


        }
    }

}