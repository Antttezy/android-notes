package com.antkumachev.androidlab19;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;

import com.antkumachev.androidlab19.models.Note;

import java.text.MessageFormat;

public class EditorActivity extends ActivityBase implements TextWatcher {

    private EditText editor;
    private AppCompatButton saveBtn;
    private int action_id;
    private Note note;

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
                note = (Note) getIntent().getSerializableExtra(EXTRA_NOTE);
                editor.setText(MessageFormat.format("{0}{2}{1}", note.getCaption(), note.getContent(), System.lineSeparator()));
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

        getIntent().putExtra(getString(R.string.action_id_res), action_id);
        getIntent().putExtra(getString(R.string.extra_id_res), EXTRA_ID);
        EXTRA_TEXT = editor.getText().toString();

        int captionSeparator = EXTRA_TEXT.indexOf('\n');

        if (captionSeparator >= 0) {

            String caption = EXTRA_TEXT.substring(0, captionSeparator);
            String content = EXTRA_TEXT.substring(captionSeparator + 1);
            getIntent().putExtra(EXTRA_NOTE, new Note(caption, content, System.currentTimeMillis()));
        } else {

            getIntent().putExtra(EXTRA_NOTE, new Note(EXTRA_TEXT, "", System.currentTimeMillis()));
        }

        setResult(RESULT_OK, getIntent());
        onBackPressed();
    }

    public void cancel(View view) {
        setResult(RESULT_CANCELED);
        onBackPressed();
    }
}
