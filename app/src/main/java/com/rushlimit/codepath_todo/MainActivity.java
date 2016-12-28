package com.rushlimit.codepath_todo;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.util.ArrayList;

import database.Todo;

public class MainActivity extends AppCompatActivity implements EditTodo.OnFragmentInteractionListener {
    private static final String TAG = MainActivity.class.getSimpleName();
    private static final String WELCOME_VIEW = "welcome_view";
    private EditText todoToAdd;

    private ArrayList<Todo> items;
    private ArrayAdapter<Todo> itemsAdapter;
    private ListView lvItems;

    private EditTodo editTodo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // If Welcome View was already shown, don't show it again.
        if (getSharedPreferences().getBoolean(WELCOME_VIEW, false)) {
            findViewById(R.id.welcome_todo_getting_started).setVisibility(View.GONE);
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        todoToAdd = (EditText) findViewById(R.id.todoToAdd);
        lvItems = (ListView) findViewById(R.id.todoListView);

        setupListView();
        setupListViewListener();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void setupListView() {
        items = (ArrayList<Todo>) SQLite.select().from(Todo.class).queryList();
        itemsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, items);
        lvItems.setAdapter(itemsAdapter);
    }

    private void setupListViewListener() {
        lvItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int pos, long id) {
                Todo todo = items.get(pos);
                items.remove(pos);
                todo.delete();
                itemsAdapter.notifyDataSetChanged();
                return true;
            }
        });

        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long id) {
                editTodo = EditTodo.newInstance(items.get(pos));
                editTodo.show(getSupportFragmentManager(), EditTodo.EDIT_TODO);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
//            case R.id.miCompose:
//                Toast.makeText(MainActivity.this, "miCompose", Toast.LENGTH_SHORT).show();
//                return true;
//            case R.id.miProfile:
//                Toast.makeText(MainActivity.this, "miProfile", Toast.LENGTH_SHORT).show();
//                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /*
     * Linked to R.id.todoAddButton in activity_main.xml
     * If view is clicked, check that the EditText todoAdd is not null and is not empty
     * Add new todoTask if this is the case and reset the EditText to take new input
     */
    public void addItem(View view) {
        if (todoToAdd == null) {
            Toast.makeText(MainActivity.this,
                    R.string.something_went_wrong, Toast.LENGTH_SHORT).show();
            return;
        }

        String textToAdd = todoToAdd.getText().toString();
        if (textToAdd.isEmpty()) {
            Toast.makeText(MainActivity.this,
                    R.string.all_todos_complete, Toast.LENGTH_SHORT).show();
            return;
        }

        setWelcomeViewDone();

        // TODO: 12/14/16 add todo detail when app is fleshed out
        Todo todo = new Todo();
        todo.setTodo(textToAdd);
        todo.save();

        itemsAdapter.add(todo);
        todoToAdd.setText("");
    }

    public void removeWelcomeView(View view) {
        setWelcomeViewDone();
        view.setVisibility(View.GONE);
    }

    private SharedPreferences getSharedPreferences() {
        return getSharedPreferences(TAG, MODE_PRIVATE);
    }

    private void setWelcomeViewDone() {
        if (!getSharedPreferences().getBoolean(WELCOME_VIEW, false)) {
            getSharedPreferences().edit().putBoolean(WELCOME_VIEW, true).apply();
        }
    }

    @Override
    public void updateListView() {
        itemsAdapter.notifyDataSetChanged();
    }
}
