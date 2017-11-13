package com.example.septiawanajipradan.nganteradmin.homepage;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.example.septiawanajipradan.nganteradmin.R;
import com.ss.bottomnavigation.BottomNavigation;
import com.ss.bottomnavigation.events.OnSelectedItemChangeListener;

/**
 * Created by aji on 11/13/2017.
 */

public class HomePageActivity extends AppCompatActivity {
    private BottomNavigation bottomNavigation;
    private android.support.v4.app.FragmentTransaction transaction;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_page_activity);
        bottomNavigation = (BottomNavigation)findViewById(R.id.bottom_navigation);
        bottomNavigation.setOnSelectedItemChangeListener(new OnSelectedItemChangeListener() {
            @Override
            public void onSelectedItemChanged(int i) {
                switch (i){
                    case R.id.tab_home:
                        transaction = getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.frame_fragment_containers,new TodayFragment());
                        transaction.commit();
                        break;
                    case R.id.tab_images:
                        transaction = getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.frame_fragment_containers,new FragmentA());
                        transaction.commit();
                        break;
                    case R.id.tab_camera:
                        Toast.makeText(HomePageActivity.this, "Camera", Toast.LENGTH_SHORT).show();
                        break;
                }

            }
        });
    }
}
