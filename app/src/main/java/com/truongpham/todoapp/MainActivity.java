package com.truongpham.todoapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView lvItems;
    TodoItemsAdapter adapter;
    private GoogleApiClient client;
    private final String EDIT_TASK = "TASK";
    private final String EDIT_DUEDATE = "DUEDATE";
    private final String EDIT_NOTES = "NOTEs";
    private final String EDIT_PRIORITY = "PRIORITY";
    private final String EDIT_STATUS = "STATUS";
    private final String EDIT_ID = "ID";
    private final String onSave = "Save";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvItems = (ListView) findViewById(R.id.lvItems);
        final TodoItemsDbHelper dbHelper = new TodoItemsDbHelper(this);
        final ArrayList<TodoItem> todoItemList = dbHelper.getData();

        adapter = new TodoItemsAdapter(this, todoItemList);
        lvItems.setAdapter(adapter);
//        lvItems.setOnItemClickListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapter, View view, int position, long arg) {
//                Intent addIntent = new Intent(MainActivity.this, CreateTodoItemsActivity.class);
//                startActivity(addIntent);
//            }
////        });
//
        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                Intent addIntent = new Intent(MainActivity.this, CreateTodoItemsActivity.class);
                TodoItem todoItem = todoItemList.get(position);

                String editedTask = todoItem.get_task();
                String editedDueDate = todoItem.get_dueDate();
                String editedNotes = todoItem.get_notes();
                String editedPriority = todoItem.get_priority();
                String editedStatus = todoItem.get_status();
                int editedId = todoItem.getiD();

                addIntent.putExtra(EDIT_TASK, editedTask);
                addIntent.putExtra(EDIT_DUEDATE, editedDueDate);
                addIntent.putExtra(EDIT_NOTES, editedNotes);
                addIntent.putExtra(EDIT_PRIORITY, editedPriority);
                addIntent.putExtra(EDIT_STATUS, editedStatus);
                addIntent.putExtra(EDIT_ID, editedId);
                addIntent.putExtra(onSave, "Update");

                startActivity(addIntent);

            }
        });


        lvItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                dbHelper.deleteData(todoItemList.get(position).getiD());
                todoItemList.remove(position);
                adapter.notifyDataSetChanged();
                return true;
            }
        });
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    public void onAddItem() {
        Intent addIntent = new Intent(MainActivity.this, CreateTodoItemsActivity.class);
        startActivity(addIntent);
    }

    @Override
    public void onStart() {
        super.onStart();
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.truongpham.todoapp/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.truongpham.todoapp/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_screen, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_plus:
                onAddItem();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
