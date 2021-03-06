package com.bignerdranch.android.criminalintent;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by Mohamed Amr on 10/7/2018.
 */

public class DatePickerFragment extends DialogFragment {

    private static final String ARG_DATE = "date";
    public static final String EXTRA_DATE = "com.bignerdranch.android.criminalintent.date";
    private DatePicker mDatePicker;

    public static DatePickerFragment newInstence(Date date){
        Bundle args= new Bundle();
        args.putSerializable(ARG_DATE ,date);
        DatePickerFragment datePickerFragment = new DatePickerFragment();
        datePickerFragment.setArguments(args);
        return datePickerFragment;
    }

    private  void sendResult (int resultCode , Date date){
        if (getTargetFragment() == null){
            return ;
        }
        Intent intent =new Intent();
        intent.putExtra(EXTRA_DATE , date);
        getTargetFragment().onActivityResult(getTargetRequestCode(), resultCode,intent);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        Date date=(Date)getArguments().getSerializable(ARG_DATE);
        final Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int day = calendar.get(calendar.DAY_OF_MONTH);
        int month = calendar.get(calendar.MONTH);
        int year = calendar.get(calendar.YEAR);
        View v= LayoutInflater.from(getActivity()).inflate(R.layout.dialog_date , null);
        mDatePicker = v.findViewById(R.id.dialog_date_date_picker);
        mDatePicker.init(year , month , day ,null );
        return new AlertDialog.Builder(getActivity()).
                setView(v).
                setTitle(R.string.date_picker_title).
                setPositiveButton(android.R.string.ok,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                int year = mDatePicker.getYear();
                                int month = mDatePicker.getMonth();
                                int day = mDatePicker.getDayOfMonth();
                                Date date = new GregorianCalendar(year , month , day).getTime();
                                sendResult(Activity.RESULT_OK , date);
                            }
                        }).
                create();
    }
}
