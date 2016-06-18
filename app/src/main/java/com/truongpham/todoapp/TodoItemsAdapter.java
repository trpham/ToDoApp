package com.truongpham.todoapp;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by ts-truong.pham on 6/15/16.
 */
public class TodoItemsAdapter extends BaseAdapter {
    private ArrayList<TodoItem> todoItemList;
    private LayoutInflater mInflater;

    public TodoItemsAdapter(Context context, ArrayList<TodoItem> comments) {
        this.todoItemList = comments;
        this.mInflater = LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return todoItemList.size();
    }

    @Override
    public Object getItem(int position) {
        return todoItemList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.custom_row, null);
            holder = new ViewHolder();

            holder.task = (TextView)convertView.findViewById(R.id.tvCustom);
            holder.notes = (TextView) convertView.findViewById(R.id.tvNotes);
            holder.priority = (TextView) convertView.findViewById(R.id.priority_todoItem);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder)convertView.getTag();
        }

        holder.task.setText(todoItemList.get(position).get_task());
        holder.notes.setText(todoItemList.get(position).get_notes());
        holder.priority.setText(todoItemList.get(position).get_priority());

        if(todoItemList.get(position).get_status().equals("Done")) {
            convertView.setBackgroundColor(Color.LTGRAY);
        } else {
            if (todoItemList.get(position).get_priority().equals("High")) {
                convertView.setBackgroundColor(Color.parseColor("#da5344"));
            }
            if (todoItemList.get(position).get_priority().equals("Medium")) {
                convertView.setBackgroundColor(Color.parseColor("#eca9a1"));
            }
            if (todoItemList.get(position).get_priority().equals("Low")) {
                convertView.setBackgroundColor(Color.parseColor("#fbebea"));
            }
        }
        return convertView;
    }

    public class ViewHolder {
        TextView task;
        TextView notes;
        TextView priority;

    }
}