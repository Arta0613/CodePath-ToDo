package com.rushlimit.codepath_todo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private Button todoAddButton;
    private EditText todoToAdd;

    private ArrayList<String> items;
    private ArrayAdapter<String> itemsAdapter;
    private ListView lvItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        todoAddButton = (Button) findViewById(R.id.todoAddButton);
        todoToAdd = (EditText) findViewById(R.id.todoToAdd);

        lvItems = (ListView) findViewById(R.id.todoListView);
        items = new ArrayList<>();
        itemsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, items);
        lvItems.setAdapter(itemsAdapter);
        setupListViewListener();
    }

    private void setupListViewListener() {
        lvItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int pos, long id) {
                items.remove(pos);
                itemsAdapter.notifyDataSetChanged();
                return true;
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
            case R.id.miCompose:
                Toast.makeText(MainActivity.this, "miCompose", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.miProfile:
                Toast.makeText(MainActivity.this, "miProfile", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void addItem(View view) {
        if (todoToAdd == null) {
            Toast.makeText(MainActivity.this,
                    R.string.something_went_wrong, Toast.LENGTH_SHORT).show();
            return;
        }

        String textToAdd = todoToAdd.getText().toString();
        if (textToAdd.isEmpty()) {
            Toast.makeText(MainActivity.this,
                    R.string.all_tasks_complete, Toast.LENGTH_SHORT).show();
            return;
        }

        itemsAdapter.add(textToAdd);
        todoToAdd.setText("");
    }
}
