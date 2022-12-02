package com.example.elaislami.Adapter;


import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.elaislami.Listener.TodoListener;
import com.example.elaislami.Model.TodoItem;
import com.example.elaislami.R;
import java.util.ArrayList;


public class TodoListAdapter extends RecyclerView.Adapter<TodoListAdapter.ViewHolder> {
    ArrayList<TodoItem> todoItems;
    Context context;
    static TodoListener todoListener;

    public TodoListAdapter(ArrayList<TodoItem> todoItems, Context context, TodoListener todoListener) {
        this.todoItems = todoItems;
        this.context = context;
        this.todoListener = todoListener;
    }

    @NonNull
    @Override
    public TodoListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.todo_item, parent, false);
        return new TodoListAdapter.ViewHolder(view,todoListener);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull TodoListAdapter.ViewHolder holder, int position) {


        holder.itemNum.setText("\u2022 ");
        holder.itemContent.setText(todoItems.get(position).getContent());

    }

    @Override
    public int getItemCount() {
        return todoItems.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView itemNum;
        TextView itemContent;
        ImageButton delete;

        public ViewHolder(@NonNull View itemView, TodoListener todoListener) {
            super(itemView);
            itemNum = itemView.findViewById(R.id.item_num);
            itemContent = itemView.findViewById(R.id.item_content);
            delete = itemView.findViewById(R.id.item_delete);

            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    todoListener.onDeleteTodoItem(getAdapterPosition());
                }
            });
        }


    }

}
