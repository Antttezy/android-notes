package com.antkumachev.androidlab19;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;

public class EditorActivity extends ActivityBase implements TextWatcher {

    private EditText editor;
    private AppCompatButton saveBtn;
    private int action_id;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);
        editor = findViewById(R.id.note_text);
        saveBtn = findViewById(R.id.save_btn);
        editor.addTextChangedListener(this);

        action_id = getIntent().getIntExtra(getString(R.string.action_id_res), -1);

        switch (action_id) {

            case CREATE_ACTION:
                break;

            case EDIT_ACTION:
                EXTRA_ID = getIntent().getIntExtra(getString(R.string.extra_id_res), -1);
                EXTRA_TEXT = getIntent().getStringExtra(getString(R.string.extra_text_res));
                editor.setText(EXTRA_TEXT);
                break;

            default:
                onBackPressed();
                break;
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        saveBtn.setEnabled(s.toString().trim().length() > 0);
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    public void save(View view) {
        EXTRA_TEXT = editor.getText().toString();
        getIntent().putExtra(getString(R.string.extra_text_res), EXTRA_TEXT);
        getIntent().putExtra(getString(R.string.action_id_res), action_id);
        getIntent().putExtra(getString(R.string.extra_id_res), EXTRA_ID);
        setResult(RESULT_OK, getIntent());
        onBackPressed();
    }

    public void cancel(View view) {
        setResult(RESULT_CANCELED);
        onBackPressed();
    }
}

