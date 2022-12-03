package com.example.elaislami.Adapter;
import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import com.example.elaislami.Model.PrayerModel;
import com.example.elaislami.R;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class PrayerAdapter extends RecyclerView.Adapter<PrayerAdapter.ViewHolder>{

    ArrayList<PrayerModel> prayerModelInternal;
    Context context;
    Date current_date;

    public PrayerAdapter(ArrayList<PrayerModel> prayerModelInternal, Context context) {
        this.prayerModelInternal = prayerModelInternal;
        this.context = context;


    }
    @NonNull
    @Override
    public PrayerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        LayoutInflater inflater=LayoutInflater.from(context);
        View view=inflater.inflate(R.layout.prayer_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PrayerAdapter.ViewHolder holder, int position){
        current_date = new Date();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat formatTime = new SimpleDateFormat("hh:mm aa");
        String result_time = formatTime.format(current_date);
        char first = result_time.charAt(0);
        
        if(String.valueOf(first).equals("0")){
            result_time = result_time.substring(1);
        }

        if(prayerModelInternal.get(position).getSalatTime().equals(result_time)){
            holder.constraintLayoutMain.setBackgroundResource(R.drawable.rounded_btn);
            holder.constraintLayoutSecondary.setBackgroundResource(R.drawable.rounded_btn);
        }
        holder.salatName.setText(prayerModelInternal.get(position).getSalatName());
        holder.salatTime.setText(prayerModelInternal.get(position).getSalatTime());
    }

    @Override
    public int getItemCount(){
        return prayerModelInternal.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        ConstraintLayout constraintLayoutMain;
        ConstraintLayout constraintLayoutSecondary;
        TextView salatName;
        TextView salatTime;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            salatName = itemView.findViewById(R.id.salat_name);
            salatTime = itemView.findViewById(R.id.salat_time);
            constraintLayoutMain=itemView.findViewById(R.id.salat_container);
            constraintLayoutSecondary=itemView.findViewById(R.id.salat_info);

        }

    }
}
