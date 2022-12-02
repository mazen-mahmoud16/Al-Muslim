package com.example.elaislami.Fragment;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.elaislami.Activity.MainActivity;
import com.example.elaislami.Activity.SurahDetailActivity;
import com.example.elaislami.Adapter.SurahsListAdapter;
import com.example.elaislami.Listener.SurahListener;
import com.example.elaislami.Model.SurahsModel;
import com.example.elaislami.R;

import java.util.ArrayList;

public class SurahsListFragment extends Fragment implements SurahListener {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    RecyclerView rv_users;
    ArrayList<SurahsModel> surahsModels=new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_surahs_list, container, false);
        rv_users=view.findViewById(R.id.surahRV);
        surahsModels.add(new SurahsModel("Al-Fatiha","سورة الفاتحه"));
        surahsModels.add(new SurahsModel("Al-Fatiha","سورة الفاتحه"));
        surahsModels.add(new SurahsModel("Al-Fatiha","سورة الفاتحه"));
        surahsModels.add(new SurahsModel("Al-Fatiha","سورة الفاتحه"));
        surahsModels.add(new SurahsModel("Al-Fatiha","سورة الفاتحه"));
        surahsModels.add(new SurahsModel("Al-Fatiha","سورة الفاتحه"));
        surahsModels.add(new SurahsModel("Al-Fatiha","سورة الفاتحه"));
        surahsModels.add(new SurahsModel("Al-Fatiha","سورة الفاتحه"));

        SurahsListAdapter surahAdapter=new SurahsListAdapter(surahsModels,getContext(),this::onSurahListener);
        rv_users.setLayoutManager(new LinearLayoutManager(getContext()));
        rv_users.setAdapter(surahAdapter);

        return view;
    }
    @Override
    public void onSurahListener(int position) {
        Intent intent = new Intent(getActivity(), SurahDetailActivity.class);
        intent.putExtra("surah_englishName", surahsModels.get(position).getEnglish_Name());
        intent.putExtra("surah_arabicName", surahsModels.get(position).getArabic_Name());
        startActivity(intent);
    }
}