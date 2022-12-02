package com.example.elaislami.Adapter;
import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.elaislami.Model.SurahDetailModel;
import com.example.elaislami.R;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class SurahDetailAdapter extends RecyclerView.Adapter<SurahDetailAdapter.ViewHolder>{
    ArrayList<SurahDetailModel> SurahDetailModelInternal;

    public SurahDetailAdapter(ArrayList<SurahDetailModel> SurahDetailModelInternal) {
        this.SurahDetailModelInternal = SurahDetailModelInternal;
    }
    @NonNull
    @Override
    public SurahDetailAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        Context context=parent.getContext();
        LayoutInflater inflater=LayoutInflater.from(context);
        View view=inflater.inflate(R.layout.surah_detail_item,parent,false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull SurahDetailAdapter.ViewHolder holder, int position){
        NumberFormat nf = NumberFormat.getInstance(Locale.forLanguageTag("AR"));
        long reversedNum = 0;
        long input_long = SurahDetailModelInternal.get(position).getAyaNumber();

        while (input_long != 0) {
            reversedNum = reversedNum * 10 + input_long % 10;
            input_long = input_long / 10;
        }

        if (reversedNum > Integer.MAX_VALUE || reversedNum < Integer.MIN_VALUE) {
            throw new IllegalArgumentException();
        }

        String ayaNumber=String.valueOf(nf.format(reversedNum));

        holder.ayaContent.setText(SurahDetailModelInternal.get(position).getAyaContent()+ayaNumber+"\u06DD");

    }

    @Override
    public int getItemCount(){
        return SurahDetailModelInternal.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView ayaContent;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ayaContent = itemView.findViewById(R.id.ayaContent);


        }

    }
}