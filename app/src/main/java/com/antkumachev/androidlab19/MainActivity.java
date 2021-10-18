package com.antkumachev.androidlab19;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.Nullable;

import com.antkumachev.androidlab19.models.Note;

public class MainActivity extends ActivityBase implements AdapterView.OnItemClickListener {
    private ListView list;
    private NoteAdapter noteAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        appContext = (MyApp) getApplicationContext();
        list = findViewById(R.id.note_list);
        noteAdapter = new NoteAdapter(appContext);
        list.setAdapter(noteAdapter);
        list.setOnItemClickListener(this);
        noteAdapter.notifyDataSetChanged();
    }

    public void addNew(View view) {
        Intent intent = new Intent(this, EditorActivity.class);
        intent.putExtra(getString(R.string.action_id_res), CREATE_ACTION);
        startActivityIfNeeded(intent, CREATE_ACTION);
    }

    public void editSelected(View view) {
        if (EXTRA_ID >= 0) {
            Intent intent = new Intent(this, EditorActivity.class);
            intent.putExtra(getString(R.string.action_id_res), EDIT_ACTION);
            intent.putExtra(getString(R.string.extra_id_res), EXTRA_ID);
            intent.putExtra(EXTRA_NOTE, appContext.get(EXTRA_ID));
            startActivityIfNeeded(intent, EDIT_ACTION);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            int actionId = data.getExtras().getInt(getString(R.string.action_id_res));
            switch (actionId) {
                case CREATE_ACTION:
                    appContext.add((Note) data.getExtras().getSerializable(EXTRA_NOTE));
                    break;

                case EDIT_ACTION:
                    appContext.set(data.getExtras().getInt(getString(R.string.extra_id_res)),
                            (Note) data.getExtras().getSerializable(EXTRA_NOTE));
                    break;

                default:
                    break;
            }

            list.invalidateViews();
        }

        EXTRA_ID = -1;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        EXTRA_ID = position;
    }
}
