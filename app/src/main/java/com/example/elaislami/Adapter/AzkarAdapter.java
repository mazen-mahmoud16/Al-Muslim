package com.example.elaislami.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.elaislami.Model.AzkarModel;
import com.example.elaislami.R;

import java.util.List;

/*
 * This is the azkar list adapter for the recycler view
 */
public class AzkarAdapter extends RecyclerView.Adapter<AzkarAdapter.ViewHolder>{

    /*
     * attributes for adapter
     */
    List<AzkarModel> azkarList;

    // Here is the constructor
    public AzkarAdapter(List<AzkarModel> azkarList) {
        this.azkarList = azkarList;
    }

    /*
     * Here is the on create view holder method to inflate the view
     */
    @NonNull
    @Override
    public AzkarAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        Context context=parent.getContext();
        LayoutInflater inflater=LayoutInflater.from(context);
        View view=inflater.inflate(R.layout.azkar_item,parent,false);
        return new AzkarAdapter.ViewHolder(view);
    }

    /*
     * Here is the om bind view holder function to set the text with the content
     */
    @Override
    public void onBindViewHolder(@NonNull AzkarAdapter.ViewHolder holder, int position){

        holder.zekrContent.setText(azkarList.get(position).getContent());

        int count=Integer.parseInt(azkarList.get(position).getCount());

        if(count<10){
            holder.noOfTimes.setText(String.valueOf(azkarList.get(position).getCount()).replace("0",""));
        }else{
            holder.noOfTimes.setText(String.valueOf(azkarList.get(position).getCount()));

        }
    }

    /*
     * Get size of azkar array
     */
    @Override
    public int getItemCount(){
        return azkarList.size();
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