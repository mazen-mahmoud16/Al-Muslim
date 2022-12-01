package com.example.elaislami.Fragment;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.elaislami.Adapter.SurahsListAdapter;
import com.example.elaislami.Model.SurahsModel;
import com.example.elaislami.R;

import java.util.ArrayList;

public class SurahsListFragment extends Fragment {

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

        SurahsListAdapter surahAdapter=new SurahsListAdapter(surahsModels,getContext());
        rv_users.setLayoutManager(new LinearLayoutManager(getContext()));
        rv_users.setAdapter(surahAdapter);

        return view;
    }
}