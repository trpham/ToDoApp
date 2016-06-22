package com.truongpham.todoapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

/**
 * Created by ts-truong.pham on 6/13/16.
 */
public class CreateTodoItemsActivity extends AppCompatActivity {

    private int year;
    private int month;
    private int day;

    private EditText etTask;
    private DatePicker dpDueDate;
    private EditText etNotes;
    private Spinner sPriority;
    private Spinner sStatus;

    private final String EDIT_TASK = "TASK";
    private final String EDIT_DUEDATE = "DUEDATE";
    private final String EDIT_NOTES = "NOTES";
    private final String EDIT_PRIORITY = "PRIORITY";
    private final String EDIT_STATUS = "STATUS";
    private final String EDIT_ID = "ID";
    private int id = -1;
    private String operation = "";
    private final String onSave = "Save";

    TodoItem todoItem = null;

    private final HashMap<String, Integer> pMap = new HashMap<String, Integer>() {{
        put("High", 0);
        put("Medium", 1);
        put("Low", 2);

    }};

    private final HashMap<String, Integer> sMap = new HashMap<String, Integer>() {{
        put("To-do", 0);
        put("Done", 1);

    }};

    private DatePicker datePicker;
    private TodoItemsDbHelper toDoItemsDbHandler;
    String[] status_level = new String[]{"To-do", "Done"};
    String[] priority_level = new String[]{"High", "Medium", "Low"};
    ArrayAdapter<String> status_adapter = null;
    ArrayAdapter<String> priority_adapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_item);

        etTask = (EditText) findViewById(R.id.taskName);
        dpDueDate = (DatePicker) findViewById(R.id.duedate);
        etNotes = (EditText) findViewById(R.id.taskNotes);
        sPriority = (Spinner) findViewById(R.id.priority_spin);
        sStatus = (Spinner) findViewById(R.id.status_spin);
        status_adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, status_level);
        priority_adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, priority_level);

        toDoItemsDbHandler = new TodoItemsDbHelper(this);


        Intent intent = getIntent();
        if (intent.getStringExtra(EDIT_TASK) != null) {
            setTitle("Edit Task");
            String editedTask = intent.getStringExtra(EDIT_TASK);
            String editedDueDate = intent.getStringExtra(EDIT_DUEDATE);
            String editedNotes = intent.getStringExtra(EDIT_NOTES);
            String editedPriority = intent.getStringExtra(EDIT_PRIORITY);
            String editedStatus = intent.getStringExtra(EDIT_STATUS);
            id = intent.getIntExtra(EDIT_ID, -1);
            operation = intent.getStringExtra(onSave);

            etTask.setText(editedTask);
            etNotes.setText(editedNotes);

            sPriority.setAdapter(priority_adapter);
            sPriority.setSelection(pMap.get(editedPriority));
            sStatus.setAdapter(status_adapter);
            sStatus.setSelection(sMap.get(editedStatus));

            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Calendar calendar = Calendar.getInstance();
            try {
                calendar.setTime(format.parse(editedDueDate));
            } catch (Exception e) {}

            datePicker = (DatePicker) findViewById(R.id.duedate);
            datePicker.updateDate(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        }
        else {
            setTitle("Add Task");
            Spinner status_spinner = (Spinner) findViewById(R.id.status_spin);
            status_spinner.setAdapter(status_adapter);
            Spinner prioriry_spinner = (Spinner) findViewById(R.id.priority_spin);
            prioriry_spinner.setAdapter(priority_adapter);

            final Calendar calendar = Calendar.getInstance();
            year = calendar.get(Calendar.YEAR);
            month = calendar.get(Calendar.MONTH);
            day = calendar.get(Calendar.DAY_OF_MONTH);

            datePicker = (DatePicker) findViewById(R.id.duedate);
            datePicker.init(year, month, day, null);
       }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.new_item_screen, menu);
        return true;
    }

    public void onSaveItem() {
        String task = etTask.getText().toString();
        String notes = etNotes.getText().toString();
        String priority = sPriority.getSelectedItem().toString();
        String status = sStatus.getSelectedItem().toString();

        Calendar calendar = Calendar.getInstance();
        calendar.set(dpDueDate.getYear(), dpDueDate.getMonth(), dpDueDate.getDayOfMonth());
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String date = format.format(calendar.getTime());

        TodoItem todoItem = new TodoItem(task, date, notes, priority, status);

        if(operation.equals("Update")) {
            if (id > 0) {
                todoItem.setiD(id);
                toDoItemsDbHandler.updateData(todoItem);
            }
        } else {
            toDoItemsDbHandler.addData(todoItem);
        }

        Intent addIntent = new Intent(CreateTodoItemsActivity.this, MainActivity.class);
        startActivity(addIntent);
    }

    public void onCancelItem() {
        Intent addIntent = new Intent(CreateTodoItemsActivity.this, MainActivity.class);
        startActivity(addIntent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_save:
                onSaveItem();
                return true;
            case R.id.action_cancel:
                onCancelItem();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}



