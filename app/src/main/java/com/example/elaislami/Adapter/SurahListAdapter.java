package com.example.elaislami.Adapter;
import com.example.elaislami.Activity.MainActivity;
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

import java.util.List;

import es.dmoral.toasty.Toasty;

public class SurahListAdapter extends RecyclerView.Adapter<SurahListAdapter.ViewHolder>{
    List<SurahDBModel> SurahsModelInternal;
    Context context;
    SurahListener surahListener;

    public SurahListAdapter(List<SurahDBModel> SurahsModelInternals, Context context) {
        this.SurahsModelInternal = SurahsModelInternals;
        this.context = context;


    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent,int viewType){
        LayoutInflater inflater=LayoutInflater.from(context);
        View view=inflater.inflate(R.layout.surah_item,parent,false);
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder,int position){
        if (SurahsModelInternal != null) {
            holder.english_Name.setText(SurahsModelInternal.get(position).getEnglishName());
            holder.arabic_Name.setText(SurahsModelInternal.get(position).getName());
            holder.number.setText(String.valueOf(SurahsModelInternal.get(position).getNumber()));
        }
        else {
            holder.english_Name.setText("SurahsModelInternal.get(position).getEnglishName()");
            holder.arabic_Name.setText("SurahsModelInternal.get(position).getName()");
            holder.number.setText("String.valueOf(SurahsModelInternal.get(position).getNumber())");

        }
    }

    public void setSurahs(List<SurahDBModel> surahModels){
        SurahsModelInternal = surahModels;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount(){
        return SurahsModelInternal.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView english_Name;
        TextView arabic_Name;
        TextView number;


        public ViewHolder(@NonNull View itemView) {
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




