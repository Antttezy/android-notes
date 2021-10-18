package com.antkumachev.androidlab19;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.antkumachev.androidlab19.models.Note;
import com.antkumachev.androidlab19.models.Priority;
import com.antkumachev.androidlab19.views.ColoredView;

import java.util.ArrayList;
import java.util.Random;

public class NoteAdapter extends BaseAdapter {
    private final MyApp context;
    private final Random random;

    public NoteAdapter(Context context) {
        this.context = (MyApp) context.getApplicationContext();
        random = new Random();
    }

    @Override
    public int getCount() {
        return context.size();
    }

    @Override
    public Object getItem(int position) {
        return context.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_item, parent, false);
            ColoredView color = convertView.findViewById(R.id.color_square);
            color.setColor(randomColor());
        }

        Note note = context.get(position);

        TextView caption = convertView.findViewById(R.id.note_string);
        caption.setText(note.getCaption());

        ImageView importantSign = convertView.findViewById(R.id.priority_view);

        if (note.getPriority() == Priority.Important) {
            importantSign.setImageResource(R.drawable.warning);
        } else {
            importantSign.setImageDrawable(null);
        }

        return convertView;
    }

    private int randomColor() {
        return Color.rgb(random.nextInt(256), random.nextInt(256), random.nextInt(256));
    }
}
