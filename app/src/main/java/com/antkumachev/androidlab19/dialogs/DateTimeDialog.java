package com.antkumachev.androidlab19.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.FragmentManager;

import com.antkumachev.androidlab19.R;

import java.sql.Date;
import java.sql.Time;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class DateTimeDialog extends AppCompatDialogFragment {

    private final DialogInterface.OnClickListener okListener;
    private final DialogInterface.OnClickListener cancelListener;
    private View content;

    public DateTimeDialog(DialogInterface.OnClickListener okListener, DialogInterface.OnClickListener cancelListener) {

        this.okListener = okListener;
        this.cancelListener = cancelListener;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        content = inflater.inflate(R.layout.activity_select_time, null);
        builder
                .setView(content)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", cancelListener);

        return builder.create();
    }

    public Time getSelectedTime() {

        DatePicker datePicker = content.findViewById(R.id.pick_date);
        TimePicker timePicker = content.findViewById(R.id.pick_time);

        GregorianCalendar calendar = new GregorianCalendar(datePicker.getYear(), datePicker.getMonth(), datePicker.getDayOfMonth(), timePicker.getHour(), timePicker.getMinute());
        return new Time(calendar.getTime().getTime());
    }
}
