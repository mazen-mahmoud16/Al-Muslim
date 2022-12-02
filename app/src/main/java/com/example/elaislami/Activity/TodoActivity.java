package com.example.elaislami.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.example.elaislami.Adapter.SurahsListAdapter;
import com.example.elaislami.Adapter.TodoListAdapter;
import com.example.elaislami.Listener.TodoListener;
import com.example.elaislami.Model.TodoItem;
import com.example.elaislami.R;

import java.util.ArrayList;

public class TodoActivity extends AppCompatActivity implements TodoListener {

    int backState;
    ImageButton back_btn;
    RecyclerView rv_todo;
    ArrayList<TodoItem> todoItems=new ArrayList<>();
    TodoListAdapter todoListAdapter;

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
        todoItems.add(new TodoItem("Read EL Qahf Sura 1 Time"));
        todoItems.add(new TodoItem("Read Azkar El Sabah"));
        todoItems.add(new TodoItem("Pray 5 Times"));
        todoItems.add(new TodoItem("Read Azkar El Masaa"));
        todoItems.add(new TodoItem("Read EL Qahf Sura 1 Time Read EL Qahf Sura 1 Time Read EL Qahf Sura 1 Time Read EL Qahf Sura 1 Time"));
        todoItems.add(new TodoItem("Read EL Qahf Sura 1 Time"));

        todoListAdapter=new TodoListAdapter(todoItems,this, this);
        rv_todo.setLayoutManager(new LinearLayoutManager(this));
        rv_todo.setAdapter(todoListAdapter);

    }

    @Override
    public void onDeleteTodoItem(int position) {
        todoItems.remove(position);
        rv_todo.setAdapter(new TodoListAdapter(todoItems,this, this));
    }
}