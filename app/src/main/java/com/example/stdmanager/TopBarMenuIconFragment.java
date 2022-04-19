package com.example.stdmanager;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.view.menu.MenuPopupHelper;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.stdmanager.Classroom.ClassroomActivity;
import com.example.stdmanager.DB.TeacherDBHelper;
import com.example.stdmanager.Event.EventActivity;
import com.example.stdmanager.Score.ScoreSubjectActivity;
import com.example.stdmanager.Settings.SettingsActivity;
import com.example.stdmanager.Statistic.StatisticActivity;
import com.example.stdmanager.Subject.SubjectActivity;
import com.example.stdmanager.models.Teacher;

import java.lang.ref.WeakReference;

public class TopBarMenuIconFragment extends Fragment {
    private TextView txtNameGV, txtIDGV;
    private ImageButton btnMenu;

    private App appState;
    Context context;

    public static WeakReference<TopBarMenuIconFragment> weakActivity;

    public static TopBarMenuIconFragment getmInstanceActivity() {
        return weakActivity.get();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_top_bar_menu_icon, container, false);
        weakActivity = new WeakReference<>(TopBarMenuIconFragment.this);

        txtNameGV = view.findViewById(R.id.txtNameGV);
        txtIDGV = view.findViewById(R.id.txtIDGV);
        btnMenu = view.findViewById(R.id.btnMenu);



        setEvent();

        getData();

        return view;
    }

    @SuppressLint("RestrictedApi")
    private void setEvent() {
        MenuBuilder menuBuilder = new MenuBuilder(context);
        MenuInflater inflater = new MenuInflater(context);

        inflater.inflate(R.menu.menu_home_sidebar, menuBuilder);

        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MenuPopupHelper menuElement = new MenuPopupHelper(context, menuBuilder, view);
                menuElement.setForceShowIcon(true);

                menuBuilder.setCallback(new MenuBuilder.Callback() {
                    @SuppressLint("NonConstantResourceId")
                    @Override
                    public boolean onMenuItemSelected(@NonNull MenuBuilder menu, @NonNull MenuItem item) {
                        Intent intent;
                        switch (item.getItemId()) {
                            case R.id.classroom:
                                intent = new Intent(context, ClassroomActivity.class);
                                startActivity(intent);
                                return true;
                            case R.id.subject:
                                intent = new Intent(context, SubjectActivity.class);
                                startActivity(intent);
                                return true;
                            case R.id.event:
                                intent = new Intent(context, EventActivity.class);
                                startActivity(intent);
                                return true;
                            case R.id.mark:
                                intent = new Intent(context, ScoreSubjectActivity.class);
                                startActivity(intent);
                                return true;
                            case R.id.statistics:
                                intent = new Intent(context, StatisticActivity.class);
                                startActivity(intent);
                                return true;
                            case R.id.settings:
                                intent = new Intent(context, SettingsActivity.class);
                                startActivity(intent);
                                return true;
                            case R.id.signout:
                                if( appState.getTeacher() != null)
                                {
                                    appState.setTeacher(null);
                                }
                                intent = new Intent(context, LoginActivity.class);
                                startActivity(intent);
                                Toast.makeText(context, "Đăng xuất thành công !", Toast.LENGTH_LONG).show();
                                return true;
                            default:
                                throw new IllegalStateException("Unexpected value: " + item.getItemId());
                        }
                    }

                    @Override
                    public void onMenuModeChange(@NonNull MenuBuilder menu) {

                    }
                });
                menuElement.show();
            }
        });
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        appState = (App) getActivity().getApplication();
    }

    @SuppressLint("SetTextI18n")
    private void getData(){
        Teacher gv = appState.getTeacher();
        txtNameGV.setText(gv.getName());
        txtIDGV.setText("Mã GV: " + gv.getId());
    }

    public void setData(Teacher teacher)
    {
        appState.setTeacher(teacher);
        getData();
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }
}