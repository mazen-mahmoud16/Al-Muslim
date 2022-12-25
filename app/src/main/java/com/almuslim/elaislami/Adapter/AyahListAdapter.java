package com.almuslim.elaislami.Adapter;
import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.almuslim.elaislami.R;
import com.almuslim.elaislami.RoomDBManager.RoomDBModel.AyahDBModel;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/*
 * This is the ayah list adapter for the recycler view
 */
public class AyahListAdapter extends RecyclerView.Adapter<AyahListAdapter.ViewHolder>{

    /*
     * attributes for adapter
     */
    private List<AyahDBModel> ayahDBModelList;
    private final int surahNumber;

    // Here is the constructor
    public AyahListAdapter( List<AyahDBModel> list,int surahNumber) {
        this.ayahDBModelList =list;


        // Check whether it is null or not
        if(ayahDBModelList == null) {
            this.ayahDBModelList = new ArrayList<>();
        }

        this.surahNumber = surahNumber;
    }

    /*
     * Here is the on create view holder method to inflate the view
     */
    @NonNull
    @Override
    public AyahListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context=parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.surah_detail_item,parent,false);
        return new ViewHolder(view);
    }

    /*
     * Here is the om bind view holder function to set the text with the content
     */
    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {


        // Format ayah number
        NumberFormat nf = NumberFormat.getInstance(Locale.forLanguageTag("AR"));
        String ayaNumber= nf.format(ayahDBModelList.get(position).getNumberInSurah());

        // Replace all empty lines
        String text = (ayahDBModelList.get(position).getText()).replace("\n", "");

        // Remove the following text from all surah's beginning except suret Fatiiha
        if(position ==1 && surahNumber !=1){
            text = text.replace("بِسۡمِ ٱللَّهِ ٱلرَّحۡمَـٰنِ ٱلرَّحِیمِ", "");
            holder.ayaContent.setText(text+"   "+"\uFD3F"+ayaNumber+"\uFD3E");
        }

        // If we are in the first ayah except suret Fatiiha
        else if(position==0&&surahNumber!=1)
        {
            holder.ayaContent.setText(text);
            ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) holder.ayaContent.getLayoutParams();
            params.startToStart = holder.constraintLayout.getId();
            params.endToEnd = holder.constraintLayout.getId();
            holder.ayaContent.requestLayout();
        }

        // If nothing above (default)
        else {

            holder.ayaContent.setText(text+"   "+"\uFD3F"+ayaNumber+"\uFD3E");
            ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) holder.ayaContent.getLayoutParams();
            params.startToStart = ConstraintLayout.LayoutParams.UNSET;
            params.endToEnd = holder.constraintLayout.getId();
            holder.ayaContent.requestLayout();
        }

    }

    /*
     * As we deal with live data so we need to update the current list
     */
    @SuppressLint("NotifyDataSetChanged")
    public void setAyahs(List<AyahDBModel> ayahModels){
        this.ayahDBModelList = ayahModels;
        notifyDataSetChanged();
    }

    /*
     * Get size of ayah array
     */
    @Override
    public int getItemCount() {
        return ayahDBModelList.size();
    }

    /*
     * Class view holder to bind with the text views and constraint layout in view
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView ayaContent;
        private final ConstraintLayout constraintLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            Log.d("debuggingggg","a");

            ayaContent = itemView.findViewById(R.id.ayaContent);
            constraintLayout= itemView.findViewById(R.id.ayah_container);
        }
    }
}