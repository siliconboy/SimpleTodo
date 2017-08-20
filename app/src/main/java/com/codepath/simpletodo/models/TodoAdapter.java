package com.codepath.simpletodo.models;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.codepath.simpletodo.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Created by yingbwan on 8/13/2017.
 */

public class TodoAdapter extends ArrayAdapter<Todo> {
    public TodoAdapter(Context context, ArrayList<Todo> todos) {
        super(context, 0, todos);

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // Get the data item for this position

        Todo todo = getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view

        if (convertView == null) {

            convertView = LayoutInflater.from(getContext()).inflate(R.layout.todo_item, parent, false);

        }

        // Lookup view for data population

        TextView tvName = (TextView) convertView.findViewById(R.id.tvName);
        TextView tvPriority = (TextView) convertView.findViewById(R.id.tvPriority);
        TextView tvDate = (TextView) convertView.findViewById(R.id.tvDate);


        tvName.setText(todo.name);

        tvPriority.setText(todo.priority);

        SimpleDateFormat ft = new SimpleDateFormat("MM/dd/yyyy");
        tvDate.setText(ft.format(todo.dueDate));

        // Return the completed view to render on screen

        return convertView;

    }
}
