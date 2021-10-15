package com.antkumachev.androidlab19;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.LayoutRes;

public class ToastHelper {
    private final @LayoutRes int toastLayout;
    private final Context context;

    public ToastHelper(@LayoutRes int layout, Context context) {
        toastLayout = layout;
        this.context = context;
    }

    public void show(String message) {
        Toast toast = new Toast(context);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM, 0, 50);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View toastView = inflater.inflate(toastLayout, null);
        TextView text = toastView.findViewById(R.id.toast_string);
        text.setText(message);
        toast.setView(toastView);
        toast.show();
    }
}
