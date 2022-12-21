package com.example.elaislami.Adapter;
import com.example.elaislami.Listener.SurahListener;
import com.example.elaislami.R;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.elaislami.RoomDBManager.RoomDBModel.SurahDBModel;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;

/*
 * This is the surah list adapter for the recycler view
 */
public class SurahListAdapter extends RecyclerView.Adapter<SurahListAdapter.ViewHolder>{

    /*
     * attributes for adapter
     */
    private List<SurahDBModel> surahDBmodels;
    private final Context context;
    private final SurahListener surahListener;

    // Here is the constructor
    public SurahListAdapter(List<SurahDBModel> surahsModelInternals, Context context,SurahListener surahListener) {

        // We first check whether it is empty or not
        if(surahsModelInternals == null){
            this.surahDBmodels = new ArrayList<>();
        }
        else{
            this.surahDBmodels = surahsModelInternals;
        }
        this.context = context;
        this.surahListener=surahListener;


    }

    /*
     * Here is the on create view holder method to inflate the view
     */
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent,int viewType){
        LayoutInflater inflater=LayoutInflater.from(context);
        View view=inflater.inflate(R.layout.surah_item,parent,false);
        return new ViewHolder(view, surahListener);
    }


    /*
     * Here is the om bind view holder function to set the text with the content
     */
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder,int position){

        // Check whether it is null or not
        if (surahDBmodels != null) {
            this.surahDBmodels.get(position).setName(Normalizer.normalize(this.surahDBmodels.get(position).getName(),
                    Normalizer.Form.NFKD) .replaceAll("\\p{M}", ""));

            holder.english_Name.setText(surahDBmodels.get(position).getEnglishName());
            holder.arabic_Name.setText(surahDBmodels.get(position).getName());
            holder.number.setText(String.valueOf(surahDBmodels.get(position).getNumber()));
        }
        else {
            Toasty.warning(context, "Loading Surah list", Toasty.LENGTH_LONG, true).show();
        }
    }

    /*
     * As we deal with live data so we need to update the current list
     */
    @SuppressLint("NotifyDataSetChanged")
    public void setSurahs(List<SurahDBModel> surahModels){
        surahDBmodels = surahModels;
        notifyDataSetChanged();
    }

    /*
     * Get size of surah array
     */
    @Override
    public int getItemCount(){
        return surahDBmodels.size();
    }

    /*
     * Class view holder to bind with the text views and image buttons in view
     */
    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView english_Name;
        TextView arabic_Name;
        TextView number;

        public ViewHolder(@NonNull View itemView, SurahListener surahListener) {
            super(itemView);
            english_Name = itemView.findViewById(R.id.english_name);
            arabic_Name = itemView.findViewById(R.id.arabicName);
            number = itemView.findViewById(R.id.surah_number);

            // When a surah is clicked
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    surahListener.onSurahListener(Integer.parseInt(number.getText().toString())-1);
                }
            });
        }

    }
}




