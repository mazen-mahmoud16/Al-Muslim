package com.example.elaislami.Adapter;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.elaislami.Model.AyahModel;
import com.example.elaislami.R;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class AyahListAdapter extends RecyclerView.Adapter<AyahListAdapter.ViewHolder>{

    private List<AyahModel> surahDetailModelInternal;

    public AyahListAdapter( List<AyahModel> list) {

        this.surahDetailModelInternal = list;
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

        String text = (surahDetailModelInternal.get(position).getAyaContent()).replace("\n", "");

        String ayaNumber= nf.format(surahDetailModelInternal.get(position).getAyaNo());

        holder.ayaContent.setText(text+"   "+"\uFD3F"+ayaNumber+"\uFD3E");


    }

    @Override
    public int getItemCount() {
        return surahDetailModelInternal.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView ayaContent;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            ayaContent = (TextView) itemView.findViewById(R.id.ayaContent);
        }

    }
}