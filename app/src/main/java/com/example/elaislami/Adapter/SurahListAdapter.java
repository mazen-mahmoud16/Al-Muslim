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
import com.example.elaislami.Model.SurahsModel;
import java.util.ArrayList;

public class SurahListAdapter extends RecyclerView.Adapter<SurahListAdapter.ViewHolder>{
    ArrayList<SurahsModel> SurahsModelInternal;
    Context context;
    SurahListener surahListener;

    public SurahListAdapter(ArrayList<SurahsModel> SurahsModelInternals, Context context, SurahListener surahListener) {
        this.SurahsModelInternal = SurahsModelInternals;
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

        holder.english_Name.setText(SurahsModelInternal.get(position).getEnglishName());
        holder.arabic_Name.setText(SurahsModelInternal.get(position).getName());
        holder.number.setText(String.valueOf(SurahsModelInternal.get(position).getNumber()));

    }

    @Override
    public int getItemCount(){
        return SurahsModelInternal.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView english_Name;
        TextView arabic_Name;
        TextView number;


        public ViewHolder(@NonNull View itemView,SurahListener surahListener) {
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




