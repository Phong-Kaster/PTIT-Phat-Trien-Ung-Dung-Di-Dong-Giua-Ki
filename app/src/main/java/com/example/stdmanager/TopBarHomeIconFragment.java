package com.example.stdmanager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.stdmanager.models.Teacher;

public class TopBarHomeIconFragment extends Fragment {
    private TextView txtNameGV, txtIDGV;
    private ImageButton btnHome;

    private App appState;
    private AppCompatActivity activity;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_top_bar_home_icon, container, false);

        txtNameGV = view.findViewById(R.id.txtNameGV);
        txtIDGV = view.findViewById(R.id.txtIDGV);
        btnHome = view.findViewById(R.id.btnHome);

        setEvent();

        getData();

        return view;
    }

    private void setEvent() {
        btnHome.setOnClickListener(view -> {
            Intent mainActivity = new Intent(activity, HomeActivity.class);
            mainActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(mainActivity);
            activity.finish();
        });
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        appState = (App) getActivity().getApplication();
        activity = (AppCompatActivity) getActivity();
    }

    private void getData(){
        Teacher gv = appState.getTeacher();
        txtNameGV.setText(gv.getName());
        txtIDGV.setText("Mã GV: " + gv.getId());
    }
}