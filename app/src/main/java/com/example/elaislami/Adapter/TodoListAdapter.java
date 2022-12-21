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
import com.example.elaislami.R;
import com.example.elaislami.RoomDBManager.RoomDBModels.TodoItemDBModel;

import java.util.List;

/*
 * This is the todolist adapter for the recycler view
 */
public class TodoListAdapter extends RecyclerView.Adapter<TodoListAdapter.ViewHolder> {

    /*
     * attributes for adapter
     */
    private List<TodoItemDBModel> todoItems;
    private static TodoListener todoListener;

    // Here is the constructor
    public TodoListAdapter(List<TodoItemDBModel> todoItems, TodoListener todoListener) {
        this.todoItems = todoItems;
        TodoListAdapter.todoListener = todoListener;
    }

    /*
     * Here is the on create view holder method to inflate the view
     */
    @NonNull
    @Override
    public TodoListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.todo_item, parent, false);
        return new TodoListAdapter.ViewHolder(view,todoListener);
    }

    /*
     * Here is the om bind view holder function to set the text with the content
     */
    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull TodoListAdapter.ViewHolder holder, int position) {
        holder.itemNum.setText("\u2022 ");
        holder.itemContent.setText(todoItems.get(position).getContent());
    }

    /*
     * As we deal with live data so we need to update the current list
     */
    @SuppressLint("NotifyDataSetChanged")
    public void setTodoItems(List<TodoItemDBModel> todoModels){
        todoItems = todoModels;
        notifyDataSetChanged();
    }

    /*
     * Get size of todo items array
     */
    @Override
    public int getItemCount() {
        return todoItems.size();
    }

    /*
     * Class view holder to bind with the text views and image buttons in view
     */
    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView itemNum;
        TextView itemContent;
        ImageButton delete;

        public ViewHolder(@NonNull View itemView, TodoListener todoListener) {
            super(itemView);
            itemNum = itemView.findViewById(R.id.item_num);
            itemContent = itemView.findViewById(R.id.item_content);
            delete = itemView.findViewById(R.id.item_delete);

            // When delete is pressed call function in todo activity
            delete.setOnClickListener(view -> todoListener.onDeleteTodoItem(getAdapterPosition()));
        }


    }

}
