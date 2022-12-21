package com.example.elaislami.View.Activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.elaislami.Adapter.TodoListAdapter;
import com.example.elaislami.Listener.TodoListener;
import com.example.elaislami.R;
import com.example.elaislami.RoomDBManager.RoomDBModel.TodoItemDBModel;
import com.example.elaislami.RoomDBManager.ViewModel.TodoListViewModel;

import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;

/*
 * Here is Todo activity that is triggered when we choose todo list option from home fragment
 */
public class TodoActivity extends AppCompatActivity implements TodoListener {

    /*
     * Edit texts, image buttons, recycler view and buttons used
     */
    private ImageButton imgBackBtn;
    private EditText etAddedItem;
    private Button add;
    private RecyclerView rv_todo;
    private Toolbar toolbar;

    // Declare adapter for recycler view
    private TodoListAdapter todoListAdapter;

    // Declare an empty list to be passed to adapter
    private List<TodoItemDBModel> todoItems=new ArrayList<>();

    // Maintain a reference to the todo list view model
    private TodoListViewModel todoListViewModel;


    /*
     * Here is on create function
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo);

        /*
         * Assign elements in layout
         */
        toolbar = findViewById(R.id.tool_bar_todo);
        imgBackBtn =findViewById(R.id.back_btn);
        rv_todo = findViewById(R.id.list_rec);
        etAddedItem = findViewById(R.id.add_todo_item);
        add = findViewById(R.id.add_todo);

        // Set toolbar
        setSupportActionBar(toolbar);

        /*
         * Handle when user clicks back
         */
        imgBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(TodoActivity.this,MainActivity.class);
                i.putExtra("back_state",0);
                startActivity(i);

            }
        });

        /*
         * Initialize vew model and adapter to pass it to recycler view
         */
        todoListViewModel = new ViewModelProvider(this).get(TodoListViewModel.class);
        todoListAdapter=new TodoListAdapter(todoItems, this);
        rv_todo.setLayoutManager(new LinearLayoutManager(this));
        rv_todo.setAdapter(todoListAdapter);

        /*
         * Observe on the live data change
         */
        todoListViewModel.getAllTodoList().observe(this, new Observer<List<TodoItemDBModel>>() {
            @Override
            public void onChanged(@Nullable final List<TodoItemDBModel> todoModels) {
                // Update the cached copy of the words in the adapter.
                todoListAdapter.setTodoItems(todoModels);
                todoItems = todoModels;
            }
        });

        /*
         * Handle when user wants to enter a new todo item
         */
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String addedText = etAddedItem.getText().toString();

                // Check whether it is empty or not
                if(addedText.isEmpty()){
                    Toasty.error(TodoActivity.this, "Please enter a todo item to be added", Toasty.LENGTH_LONG, true).show();
                }
                else{
                    TodoItemDBModel newItem = new TodoItemDBModel();
                    newItem.setContent(addedText);
                    todoListViewModel.insertTodoItem(newItem);
                    etAddedItem.setText("");
                    Toasty.success(TodoActivity.this, "Item added successfully", Toasty.LENGTH_SHORT, true).show();
                }
            }
        });

    }

    /*
     * Handle when user wants to delete a specific todo item
     */
    @Override
    public void onDeleteTodoItem(int position) {
        todoListViewModel.deleteTodoItem(todoItems.get(position).getId());
    }
}