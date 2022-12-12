package com.example.elaislami.Adapter;
import com.example.elaislami.Listener.SurahListener;
import com.example.elaislami.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.elaislami.RoomDBModels.SurahDBModel;

import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;

public class SurahListAdapter extends RecyclerView.Adapter<SurahListAdapter.ViewHolder>{
    List<SurahDBModel> surahsModelInternal;
    Context context;
    SurahListener surahListener;

    public SurahListAdapter(List<SurahDBModel> surahsModelInternals, Context context,SurahListener surahListener) {
        if(surahsModelInternals == null){
            this.surahsModelInternal = new ArrayList<>();
        }
        else{
            this.surahsModelInternal = surahsModelInternals;
        }
        this.context = context;
        this.surahListener=surahListener;


    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent,int viewType){
        LayoutInflater inflater=LayoutInflater.from(context);
        View view=inflater.inflate(R.layout.surah_item,parent,false);
        ViewHolder viewHolder=new ViewHolder(view,surahListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder,int position){
        if (surahsModelInternal != null) {
            holder.english_Name.setText(surahsModelInternal.get(position).getEnglishName());
            holder.arabic_Name.setText(surahsModelInternal.get(position).getName());
            holder.number.setText(String.valueOf(surahsModelInternal.get(position).getNumber()));
        }
        else {
            Toasty.warning(context, "Loading Surah list", Toasty.LENGTH_LONG, true).show();

        }
    }

    public void setSurahs(List<SurahDBModel> surahModels){
        surahsModelInternal = surahModels;
        notifyDataSetChanged();
    }
    
    @Override
    public int getItemCount(){
        return surahsModelInternal.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView english_Name;
        TextView arabic_Name;
        TextView number;


        public ViewHolder(@NonNull View itemView, SurahListener surahListener) {
            super(itemView);
            english_Name = itemView.findViewById(R.id.english_name);
            arabic_Name = itemView.findViewById(R.id.arabicName);
            number = itemView.findViewById(R.id.surah_number);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    surahListener.onSurahListener(getAdapterPosition());
                }
            });
        }

    }
}




