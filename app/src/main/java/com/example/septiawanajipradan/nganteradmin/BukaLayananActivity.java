package com.example.septiawanajipradan.nganteradmin;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RelativeLayout;

/**
 * Created by Septiawan Aji Pradan on 7/14/2017.
 */

public class BukaLayananActivity extends AppCompatActivity {
    private RelativeLayout aktif;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.buka_layanan_activity);
        aktif = (RelativeLayout)findViewById(R.id.rl_buka_layanan);
        aktif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),OrderActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
