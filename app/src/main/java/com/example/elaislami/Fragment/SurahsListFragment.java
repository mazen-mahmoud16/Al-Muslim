package com.example.elaislami.Fragment;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.elaislami.Activity.SurahDetailActivity;
import com.example.elaislami.Adapter.SurahListAdapter;
import com.example.elaislami.Listener.SurahListener;

import com.example.elaislami.R;
import com.example.elaislami.RoomDBModels.SurahDBModel;
import com.example.elaislami.ViewModel.SurahViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import es.dmoral.toasty.Toasty;

public class SurahsListFragment extends Fragment implements SurahListener {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    RecyclerView rv_surahs;
    List<SurahDBModel> list;
    private SurahViewModel surahViewModel;
    SearchView searchView;
    SurahListAdapter surahAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_surahs_list, container, false);

        surahViewModel = new ViewModelProvider(this).get(SurahViewModel.class);

        rv_surahs=view.findViewById(R.id.surahRV);
        surahAdapter=new SurahListAdapter(list,getContext(),this::onSurahListener);
        rv_surahs.setLayoutManager(new LinearLayoutManager(getContext()));
        rv_surahs.setAdapter(surahAdapter);

        surahViewModel.getAllSurahs().observe(getActivity(), new Observer<List<SurahDBModel>>() {
            @Override
            public void onChanged(@Nullable final List<SurahDBModel> surahModels) {
                // Update the cached copy of the words in the adapter.
                    surahAdapter.setSurahs(surahModels);
                    list=surahModels;
                }
        });

        searchView=view.findViewById(R.id.search_bar);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                filter(s);
                return false;
            }
        });

        return view;
    }

    @Override
    public void onSurahListener(int position) {
        Intent intent = new Intent(getActivity(), SurahDetailActivity.class);
        intent.putExtra("surah_englishName", list.get(position).getEnglishName());
        intent.putExtra("surah_arabicName", list.get(position).getName());
        intent.putExtra("surah_number", list.get(position).getNumber());
        startActivity(intent);
    }

    private void filter(String text) {
        ArrayList<SurahDBModel> filteredlist = new ArrayList<SurahDBModel>();

        for (SurahDBModel surah : list) {
            if (surah.getEnglishName().toLowerCase().contains(text.toLowerCase())||surah.getName().toLowerCase().contains(text.toLowerCase())) {
                filteredlist.add(surah);
            }
        }
        if (filteredlist.isEmpty()) {
            Toasty.info(getActivity(), "No Data Found..", Toast.LENGTH_SHORT).show();
            surahAdapter.setSurahs(filteredlist);
        } else {
            surahAdapter.setSurahs(filteredlist);
        }
    }

}