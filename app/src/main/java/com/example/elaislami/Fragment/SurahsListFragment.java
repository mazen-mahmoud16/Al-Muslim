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
import com.example.elaislami.Activity.SurahDetailActivity;
import com.example.elaislami.Adapter.SurahListAdapter;
import com.example.elaislami.Listener.SurahListener;

import com.example.elaislami.R;
import com.example.elaislami.RoomDBModels.SurahDBModel;
import com.example.elaislami.ViewModel.SurahViewModel;
import java.util.List;

public class SurahsListFragment extends Fragment implements SurahListener {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    RecyclerView rv_surahs;
    List<SurahDBModel> list;
    private SurahViewModel surahViewModel;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_surahs_list, container, false);

        surahViewModel = new ViewModelProvider(this).get(SurahViewModel.class);

        rv_surahs=view.findViewById(R.id.surahRV);
        SurahListAdapter surahAdapter=new SurahListAdapter(list,getContext());
        rv_surahs.setLayoutManager(new LinearLayoutManager(getContext()));
        rv_surahs.setAdapter(surahAdapter);

        surahViewModel.getAllSurahs().observe(getActivity(), new Observer<List<SurahDBModel>>() {
            @Override
            public void onChanged(@Nullable final List<SurahDBModel> surahModels) {
                // Update the cached copy of the words in the adapter.
                surahAdapter.setSurahs(surahModels);
            }
        });

       /* Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.alquran.cloud/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        JsonPlaceHolderAPI jsonPlaceHolderAPI = retrofit.create(JsonPlaceHolderAPI.class);
        Call<SurahFirstResponse> call = jsonPlaceHolderAPI.getSurahs();

        call.enqueue(new Callback<SurahFirstResponse>() {
            @Override
            public void onResponse(Call<SurahFirstResponse> call, Response<SurahFirstResponse> response) {
                if (!response.isSuccessful()){
                    Log.d("MVVMX", "--- Not successful");
                } else {
                    SurahFirstResponse mAllPosts = response.body();
                    String s=mAllPosts.getData().get(0).getEnglishName();
                    Log.d("MVVMX", s);
                    rv_users=getActivity().findViewById(R.id.surahRV);
                    list=mAllPosts.getData();
                    // rv_users.setAdapter(new Adapter(mAllPosts.getSuhras(),MainActivity.this));


                }
            }

            @Override
            public void onFailure(Call<SurahFirstResponse> call, Throwable t) {
                Log.d("MVVMX", "--- FAILED " + t.getMessage());

            }


        });*/


        // rv_users=view.findViewById(R.id.surahRV);
      //  surahViewModel = new ViewModelProvider(this).get(SurahViewModel.class);


       // SurahListAdapter surahAdapter=new SurahListAdapter(list,getContext(),this::onSurahListener);
        //rv_users.setLayoutManager(new LinearLayoutManager(getContext()));
       // rv_users.setAdapter(surahAdapter);

        return view;
    }

    @Override
    public void onSurahListener(int position) {
        Intent intent = new Intent(getActivity(), SurahDetailActivity.class);
        // intent.putExtra("surah_englishName", surahsModels.get(position).getEnglish_Name());
        //intent.putExtra("surah_arabicName", surahsModels.get(position).getArabic_Name());
        startActivity(intent);
    }
}