package com.example.septiawanajipradan.nganteradmin.akun;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.septiawanajipradan.nganteradmin.R;

/**
 * Created by aji on 11/14/2017.
 */

public class AkunFragment extends Fragment {

    View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.akun_fragment,container,false);
        return view;
    }
}
