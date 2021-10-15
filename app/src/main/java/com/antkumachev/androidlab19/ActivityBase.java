package com.antkumachev.androidlab19;

import androidx.appcompat.app.AppCompatActivity;

public class ActivityBase extends AppCompatActivity {
    protected final int CREATE_ACTION = 0x13;
    protected final int EDIT_ACTION  = 0x14;
    protected String EXTRA_TEXT = "";
    protected int EXTRA_ID = -1;

    protected MyApp appContext;
}
