package com.example.elaislami.Adapter;
import android.content.Context;
import android.graphics.Color;
import android.text.Layout;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import com.example.elaislami.Model.AyahModel;
import com.example.elaislami.R;
import com.example.elaislami.RoomDBModels.AyahDBModel;
import com.example.elaislami.RoomDBModels.SurahDBModel;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class AyahListAdapter extends RecyclerView.Adapter<AyahListAdapter.ViewHolder>{

    private List<AyahDBModel> surahDetailModelInternal;

    private int surahNumber;

    public AyahListAdapter(int surahNumber) {

        if(surahDetailModelInternal == null) {
            this.surahDetailModelInternal = new ArrayList<>();
        }
        this.surahNumber = surahNumber;

    }

    @NonNull
    @Override
    public AyahListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context=parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.surah_detail_item,parent,false);
        return new AyahListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        NumberFormat nf = NumberFormat.getInstance(Locale.forLanguageTag("AR"));

        String text = (surahDetailModelInternal.get(position).getText()).replace("\n", "");

        for (AyahDBModel ayahDBModel:surahDetailModelInternal) {
            Log.d("xxxx",String.valueOf(ayahDBModel.getSurahNumber()));
        }


        String ayaNumber= nf.format(surahDetailModelInternal.get(position).getNumberInSurah());

        if(position ==1 && surahNumber !=1){
            text = text.replace("بِسۡمِ ٱللَّهِ ٱلرَّحۡمَـٰنِ ٱلرَّحِیمِ", "");
            holder.ayaContent.setText(text+"   "+"\uFD3F"+ayaNumber+"\uFD3E");

        }

        else if(position==0&&surahNumber!=1){
            holder.ayaContent.setText(text);//holder.constraintLayout.setBackgroundColor(Color.TRANSPARENT);
            ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) holder.ayaContent.getLayoutParams();
            params.startToStart = holder.constraintLayout.getId();
            params.endToEnd = holder.constraintLayout.getId();
            holder.ayaContent.requestLayout();
        }

        else {
            holder.ayaContent.setText(text+"   "+"\uFD3F"+ayaNumber+"\uFD3E");

        }

    }

    public void setAyahs(List<AyahDBModel> ayahModels){
        this.surahDetailModelInternal = ayahModels;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return surahDetailModelInternal.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView ayaContent;
        private ConstraintLayout constraintLayout;
        private TextView juz;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            ayaContent = (TextView) itemView.findViewById(R.id.ayaContent);
            constraintLayout=(ConstraintLayout) itemView.findViewById(R.id.ayah_container);

        }

    }
}