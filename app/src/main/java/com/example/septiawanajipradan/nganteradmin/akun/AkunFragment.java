package com.example.septiawanajipradan.nganteradmin.akun;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.septiawanajipradan.nganteradmin.R;
import com.example.septiawanajipradan.nganteradmin.SplashScreen;
import com.example.septiawanajipradan.nganteradmin.helper.SessionManager;

/**
 * Created by aji on 11/14/2017.
 */

public class AkunFragment extends Fragment {

    View view;
    private LinearLayout logout;
    private SessionManager sessionManager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.akun_fragment,container,false);
        logout = (LinearLayout) view.findViewById(R.id.ll_logout);
        sessionManager = new SessionManager(getContext());
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog dialog = new AlertDialog.Builder(getActivity())
                        .setTitle("Logout")
                        .setMessage("Yakin untuk logout dari akun admin ?")
                        .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                sessionManager.delete();
                                Intent intent = new Intent(getActivity(), SplashScreen.class);
                                startActivity(intent);
                                getActivity().finish();
                            }
                        })
                        .setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).create();
                dialog.show();
            }
        });
        return view;
    }
}
