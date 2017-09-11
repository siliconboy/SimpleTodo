package com.codepath.simpletodo.models;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.codepath.simpletodo.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import static com.codepath.simpletodo.R.id.tvDate;
import static com.codepath.simpletodo.R.id.tvName;
import static com.codepath.simpletodo.R.id.tvPriority;

/**
 * Created by yingbwan on 8/13/2017.
 */

public class TodoAdapter extends ArrayAdapter<Todo> {
    public TodoAdapter(Context context, ArrayList<Todo> todos) {
        super(context, 0, todos);

    }

    // View lookup cache
    private static class ViewHolder {

        TextView tvName;
        TextView tvPriority;
        TextView tvDate;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // Get the data item for this position

        Todo todo = getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());

            convertView =inflater.inflate(R.layout.todo_item, parent, false);
            viewHolder.tvName = (TextView) convertView.findViewById(tvName);
            viewHolder.tvPriority = (TextView) convertView.findViewById(tvPriority);
            viewHolder.tvDate = (TextView) convertView.findViewById(tvDate);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        // Lookup view for data population

      //  TextView tvName = (TextView) convertView.findViewById(R.id.tvName);
     //   TextView tvPriority = (TextView) convertView.findViewById(R.id.tvPriority);
      //  TextView tvDate = (TextView) convertView.findViewById(R.id.tvDate);


        viewHolder.tvName.setText(todo.name);

        viewHolder.tvPriority.setText(todo.priority);
        String[] priority_array = getContext().getResources().getStringArray(R.array.priority_array);
        int txtColor = Color.GREEN;
        if(todo.priority.equalsIgnoreCase(priority_array[0])){
            txtColor = Color.RED;
        }
        if(todo.priority.equalsIgnoreCase(priority_array[1])) {
            txtColor = Color.YELLOW;
        }
        viewHolder.tvPriority.setTextColor(txtColor);

        SimpleDateFormat ft = new SimpleDateFormat("MM/dd/yyyy");
        viewHolder.tvDate.setText(ft.format(todo.dueDate));

        // Return the completed view to render on screen

        return convertView;

    }
}
