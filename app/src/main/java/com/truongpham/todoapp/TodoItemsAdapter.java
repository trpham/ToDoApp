package com.truongpham.todoapp;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.joda.time.LocalDate;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


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
            holder.dueMonth = (TextView) convertView.findViewById(R.id.dueMonth_todoItem);
            holder.dueDay = (TextView) convertView.findViewById(R.id.dueDay_todoItem);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder)convertView.getTag();
        }

        holder.task.setText(todoItemList.get(position).get_task());
        holder.notes.setText(todoItemList.get(position).get_notes());

        String passedDate = todoItemList.get(position).get_dueDate();

        String month = theMonth(Integer.parseInt(todoItemList.get(position).get_dueDate().substring(5,7)));

        int catalog_outdated = 0;

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        try {
            Date currDate = new LocalDate().toDate();

            if (currDate.before(sdf.parse(passedDate))) {
                catalog_outdated = 1;

            } else if (currDate.after(sdf.parse(passedDate))){
                catalog_outdated = -1;
            } else {
                catalog_outdated = 0;
            }
        } catch (Exception e) {}

        if(todoItemList.get(position).get_status().equals("Done")) {
            convertView.setBackgroundColor(Color.LTGRAY);
            holder.dueMonth.setText("");
            holder.dueDay.setText("DONE");
        } else {
            if(catalog_outdated == 0) {
                holder.dueMonth.setText("");
                holder.dueDay.setText("TODAY");
            }
            else if(catalog_outdated == -1) {
                holder.dueMonth.setText("");
                holder.dueDay.setText("OVERDUE");
            }
            else {
                holder.dueMonth.setText(month);
                holder.dueDay.setText(todoItemList.get(position).get_dueDate().substring(8));
            }
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


    public static String theMonth(int month){
        String[] monthNames = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
        return monthNames[month];
    }

    public class ViewHolder {
        TextView task;
        TextView notes;
        TextView dueMonth;
        TextView dueDay;
    }
}