package com.example.stdmanager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.example.stdmanager.Classroom.ClassroomActivity;
import com.example.stdmanager.Event.EventActivity;
import com.example.stdmanager.Score.ScoreSubjectActivity;
import com.example.stdmanager.Settings.SettingsAccountActivity;
import com.example.stdmanager.Statistic.StatisticActivity;
import com.example.stdmanager.Subject.SubjectActivity;


public class HomeActivity extends AppCompatActivity {

    ImageButton buttonHomeStatistic,
                buttonHomeClassroom,
                buttonHomeSubject,
                buttonHomeEvent,
                buttonHomeScore,
                buttonHomeAccount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        setControl();
        setEvent();
    }

    private void setControl()
    {
        buttonHomeStatistic = findViewById(R.id.buttonHomeStatistic);
        buttonHomeClassroom = findViewById(R.id.buttonHomeClassroom);

        buttonHomeSubject = findViewById(R.id.buttonHomeSubject);
        buttonHomeEvent = findViewById(R.id.buttonHomeEvent);

        buttonHomeScore = findViewById(R.id.buttonHomeScore);
        buttonHomeAccount = findViewById(R.id.buttonHomeAccount);
    }


    @SuppressLint("RestrictedApi")
    private void setEvent(){

        /*Step 1*/
        buttonHomeStatistic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, StatisticActivity.class);
                startActivity(intent);
            }
        });

        /*Step 2*/
        buttonHomeClassroom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, ClassroomActivity.class);
                startActivity(intent);
            }
        });

        buttonHomeSubject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, SubjectActivity.class);
                startActivity(intent);
            }
        });

        buttonHomeAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, SettingsAccountActivity.class);
                startActivity(intent);
            }
        });

        buttonHomeScore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, ScoreSubjectActivity.class);
                startActivity(intent);
            }
        });

        buttonHomeEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, EventActivity.class);
                startActivity(intent);
            }
        });
    }
}