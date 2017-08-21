package com.codepath.simpletodo.ui.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.codepath.simpletodo.R;
import com.codepath.simpletodo.interfaces.EditTodoDialogListener;
import com.codepath.simpletodo.interfaces.NoticeDialogListener;
import com.codepath.simpletodo.models.Todo;
import com.codepath.simpletodo.models.TodoAdapter;
import com.codepath.simpletodo.ui.dialogs.EditTodoDialogFragment;
import com.codepath.simpletodo.ui.dialogs.MyAlertDialogFragment;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NoticeDialogListener, EditTodoDialogListener {
    ArrayList<Todo> items = new ArrayList<Todo>();
    TodoAdapter itemsAdapter;
    ListView lvItems;

    private boolean editMode = false;
    private int currentPostion = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        readItems();
        itemsAdapter = new TodoAdapter(this, items);
        lvItems = (ListView) findViewById(R.id.lvItems);
        lvItems.setAdapter(itemsAdapter);
        setupListViewListener();
    }

    // show new/edit dialog page
    private void showEditDialog(boolean edit) {
        EditTodoDialogFragment editFragment;
        FragmentManager fm = getSupportFragmentManager();
        editMode = edit;

        if (edit) {
            Todo td = items.get(currentPostion);
            editFragment = EditTodoDialogFragment.newInstance(td);
        } else {
            editFragment = EditTodoDialogFragment.newInstance(null);
        }
        editFragment.show(fm, "todo_edit_item");
    }

    private void showAlertDialog() {
        FragmentManager fm = getSupportFragmentManager();
        MyAlertDialogFragment alertFragment = MyAlertDialogFragment.newInstance("Delete Item");
        alertFragment.show(fm, "fragment_alert");
    }


    public void setupListViewListener() {
        //remove item
        lvItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                currentPostion = position;
                showAlertDialog();
                return true;
            }
        });
        //edit item
        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                currentPostion = position;
                showEditDialog(true);
            }
        });
    }

    private void readItems() {
        List<Todo> todos = SQLite.select().from(Todo.class).queryList();
        for (Todo td : todos) {
            items.add(td);
        }
    }

    @Override
    public void onDialogPositiveClick(String result) {
        Todo td = items.get(currentPostion);
        td.delete();
        items.remove(currentPostion);
        itemsAdapter.notifyDataSetChanged();
    }

    @Override
    public void onDialogNegativeClick(String result) {

    }

    @Override
    public void onFinishDialog(Todo td) {
        if (editMode) {
            td.save();
            itemsAdapter.notifyDataSetChanged();
        } else {
            td.save();
            itemsAdapter.add(td);
            itemsAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
     //   Drawable icon = menu.getItem(0).getIcon();
       // icon.mutate();
       // icon.setColorFilter(getResources().getColor(android.R.color.white, PorterDuff.Mode.SRC_IN));
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_add_item:
                showEditDialog(false);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
