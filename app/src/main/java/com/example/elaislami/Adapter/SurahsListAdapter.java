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

public class SurahsListAdapter extends RecyclerView.Adapter<SurahsListAdapter.ViewHolder>{
    ArrayList<SurahsModel> SurahsModelInternal;
    Context context;
    SurahListener surahListener;

    public SurahsListAdapter(ArrayList<SurahsModel> SurahsModelInternals,Context context,SurahListener surahListener) {
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

        holder.english_Name.setText(SurahsModelInternal.get(position).getEnglish_Name());
        holder.arabic_Name.setText(SurahsModelInternal.get(position).getArabic_Name());

    }

    @Override
    public int getItemCount(){
        return SurahsModelInternal.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView english_Name;
        TextView arabic_Name;

        public ViewHolder(@NonNull View itemView,SurahListener surahListener) {
            super(itemView);
            english_Name = itemView.findViewById(R.id.english_name);
            arabic_Name = itemView.findViewById(R.id.arabicName);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    surahListener.onSurahListener(getAdapterPosition());
                }
            });
        }

    }
}




