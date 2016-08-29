package com.luciddreamfactory.luciddiary.activities;

import android.app.DatePickerDialog;
import android.app.Dialog;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.widget.DatePicker;
import android.widget.EditText;

import com.luciddreamfactory.luciddiary.R;
import com.luciddreamfactory.luciddiary.interfaces.DatePickerCallBack;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DatePickerFragment extends DialogFragment
        implements DatePickerDialog.OnDateSetListener {



    private DatePickerCallBack callback = null;
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
        // call callback Method
        if (callback != null) {
            callback.onDateChosen(getFormattedDate(view.getDayOfMonth(),view.getMonth(),view.getYear()));
        } else {
            Log.e(TAG, "onDateSet: DatePickerCallBack callback ist NULL!");
        }
        }


    public Calendar getFormattedDate(int day, int month, int year){
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_MONTH, day);
        cal.set(Calendar.MONTH, month);
        cal.set(Calendar.YEAR, year);


        return cal;
    }

    public DatePickerCallBack getCallback() {
        return callback;
    }

    public void setCallback(DatePickerCallBack callback) {
        this.callback = callback;
    }
}