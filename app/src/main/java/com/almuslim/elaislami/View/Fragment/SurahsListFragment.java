package com.almuslim.elaislami.View.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.almuslim.elaislami.Adapter.SurahListAdapter;
import com.almuslim.elaislami.Listener.SurahListener;
import com.almuslim.elaislami.RoomDBManager.RoomDBModel.SurahDBModel;
import com.almuslim.elaislami.RoomDBManager.ViewModel.SurahViewModel;
import com.almuslim.elaislami.View.Activity.SurahDetailActivity;
import com.almuslim.elaislami.R;

import java.util.ArrayList;
import java.util.List;

/*
 * Here is surah list fragment class that is triggered when we open the fourth tab in the view pager
 */
public class SurahsListFragment extends Fragment implements SurahListener {

    // Recycler View
    private RecyclerView rvSurahs;

    // List of surah models
    private List<SurahDBModel> surahDBModels =new ArrayList<>();

    // View Model
    private SurahViewModel surahViewModel;

    // Search View
    private SearchView searchView;

    // Adapter
    private SurahListAdapter surahAdapter;

    /*
     * Here is on create view function
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_surahs_list, container, false);

        /*
         * Assign recycler view and search view
         */
        rvSurahs =view.findViewById(R.id.surahRV);
        searchView=view.findViewById(R.id.search_bar);

        // Initialize the adapter
        surahAdapter=new SurahListAdapter(surahDBModels,getContext(), this);

        // Set layout manager
        rvSurahs.setLayoutManager(new LinearLayoutManager(getContext()));

        // Set adapter
        rvSurahs.setAdapter(surahAdapter);

        // Initialize the view model
        surahViewModel = new ViewModelProvider(this).get(SurahViewModel.class);

        surahViewModel.getAllSurahs().observe(requireActivity(), surahModels -> {
            // Update the cached copy of the words in the adapter.
                surahAdapter.setSurahs(surahModels);
                surahDBModels =surahModels;
            });

        /*
         * Search view listener fired if any text added or removed
         */
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                if(s != null){
                    filter(s);
                }
                return false;
            }
        });

        return view;
    }

    /*
     * Here is the function to open the surah detail activity based on the surah that user clicked on.
     */
    @Override
    public void onSurahListener(int position) {
        Intent intent = new Intent(getActivity(), SurahDetailActivity.class);
        intent.putExtra("surah_englishName", surahDBModels.get(position).getEnglishName());
        intent.putExtra("surah_arabicName", surahDBModels.get(position).getName());
        intent.putExtra("surah_number", surahDBModels.get(position).getNumber());
        startActivity(intent);
    }

    /*
     * Here is the function to filter the list in the adapter based on the words entered by user in the search view
     */
    private void filter(String text) {

        // Initialize new list that will be filled  with the filtered results of search
        ArrayList<SurahDBModel> filteredList = new ArrayList<>();

            // Looping on the main adapter list
            for (SurahDBModel surah : surahDBModels) {
                if (surah.getEnglishName().toLowerCase().contains(text.toLowerCase())||surah.getName().toLowerCase().contains(text.toLowerCase())) {
                    filteredList.add(surah);
                }
            }

            /*
             * setting the adapter again with the resulted list
             */
            if (filteredList.isEmpty()) {
                surahAdapter.setSurahs(filteredList);
            } else {
                surahAdapter.setSurahs(filteredList);
            }
    }

}