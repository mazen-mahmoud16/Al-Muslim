package com.example.elaislami.Activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.elaislami.Adapter.TodoListAdapter;
import com.example.elaislami.Listener.TodoListener;
import com.example.elaislami.Model.TodoItem;
import com.example.elaislami.R;
import com.example.elaislami.RoomDBModels.SurahDBModel;
import com.example.elaislami.RoomDBModels.TodoItemDBModel;
import com.example.elaislami.ViewModel.SurahViewModel;

import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;

public class TodoActivity extends AppCompatActivity implements TodoListener {

    int backState;
    ImageButton back_btn;
    RecyclerView rv_todo;
    TodoListAdapter todoListAdapter;
    List<TodoItemDBModel> todoItems=new ArrayList<>();
    SurahViewModel surahViewModel;
    EditText addedItem;
    Button add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo);

        backState=getIntent().getIntExtra("back_state",-1);

        Toolbar toolbar = findViewById(R.id.tool_bar_todo);

        setSupportActionBar(toolbar);

        back_btn=findViewById(R.id.back_btn);

        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(TodoActivity.this,MainActivity.class);
                i.putExtra("back_state",0);
                startActivity(i);

            }
        });



        rv_todo = findViewById(R.id.list_rec);

        surahViewModel = new ViewModelProvider(this).get(SurahViewModel.class);

        todoListAdapter=new TodoListAdapter(todoItems,this, this);
        rv_todo.setLayoutManager(new LinearLayoutManager(this));
        rv_todo.setAdapter(todoListAdapter);

        surahViewModel.getAllTodoList().observe(this, new Observer<List<TodoItemDBModel>>() {
            @Override
            public void onChanged(@Nullable final List<TodoItemDBModel> todoModels) {
                // Update the cached copy of the words in the adapter.
                todoListAdapter.setTodoItems(todoModels);
                todoItems=todoModels;
            }
        });

        addedItem = findViewById(R.id.todo_item);
        add = findViewById(R.id.add_todo);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String addedText = addedItem.getText().toString();
                if(addedText.isEmpty()){
                    Toasty.error(TodoActivity.this, "Please enter a todo item to be added", Toasty.LENGTH_LONG, true).show();
                }
                else{
                    TodoItemDBModel newItem = new TodoItemDBModel();
                    newItem.setContent(addedText);
                    surahViewModel.insertTodoItem(newItem);
                }
            }
        });

    }

    @Override
    public void onDeleteTodoItem(int position) {
        surahViewModel.deleteTodoItem(position);
    }
}