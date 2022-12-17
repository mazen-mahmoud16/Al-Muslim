package com.example.elaislami.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.elaislami.Model.AzkarModel;
import com.example.elaislami.R;

import java.util.List;

public class AzkarAdapter extends RecyclerView.Adapter<AzkarAdapter.ViewHolder>{
    List<AzkarModel> azkarModelModelInternal;

    public AzkarAdapter(List<AzkarModel> azkarModelModelInternal) {
        this.azkarModelModelInternal = azkarModelModelInternal;
    }
    @NonNull
    @Override
    public AzkarAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        Context context=parent.getContext();
        LayoutInflater inflater=LayoutInflater.from(context);
        View view=inflater.inflate(R.layout.azkar_item,parent,false);
        return new AzkarAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AzkarAdapter.ViewHolder holder, int position){

        holder.zekrContent.setText(azkarModelModelInternal.get(position).getContent());

        int count=Integer.parseInt(azkarModelModelInternal.get(position).getCount());

        if(count<10){
            holder.noOfTimes.setText(String.valueOf(azkarModelModelInternal.get(position).getCount()).replace("0",""));

        }else{
            holder.noOfTimes.setText(String.valueOf(azkarModelModelInternal.get(position).getCount()));

        }
    }

    @Override
    public int getItemCount(){
        return azkarModelModelInternal.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView zekrContent;
        TextView noOfTimes;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            zekrContent = itemView.findViewById(R.id.zekr_content);
            noOfTimes = itemView.findViewById(R.id.zekr_no_of_times);

        }

    }
}