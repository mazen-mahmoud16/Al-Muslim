package com.example.elaislami.Activity;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;
import android.os.Bundle;
import com.example.elaislami.R;
import com.example.elaislami.Adapter.TabsAdapter;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {

    int backState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        backState=getIntent().getIntExtra("back_state",-1);

        Toolbar toolbar = findViewById(R.id.tool_bar);

        setSupportActionBar(toolbar);

        TabLayout tabs = findViewById(R.id.tab_layout);

        tabs.addTab(tabs.newTab().setIcon(R.drawable.ic_baseline_home_24));
        tabs.addTab(tabs.newTab().setIcon(R.drawable.ic_prayer));
        tabs.addTab(tabs.newTab().setIcon(R.drawable.ic_qibla));
        tabs.addTab(tabs.newTab().setIcon(R.drawable.ic_quran));

        tabs.setTabGravity(TabLayout.GRAVITY_FILL);

        ViewPager viewPager = findViewById(R.id.view_pager);

        TabsAdapter tabsAdapter = new TabsAdapter(getSupportFragmentManager(), tabs.getTabCount());

        viewPager.setAdapter(tabsAdapter);

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabs));

        if(backState!=-1){
            viewPager.setCurrentItem(backState);
            backState=-1;
        }

        tabs.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {

            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }


}