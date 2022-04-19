package com.example.stdmanager.Statistic;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.ListView;

import com.example.stdmanager.R;
import com.example.stdmanager.listViewModels.StatisticListViewModel;
import com.example.stdmanager.models.Statistic;

import java.util.ArrayList;

public class StatisticActivity extends AppCompatActivity {

    ListView listView;
    ArrayList<Statistic> data = new ArrayList<>();
    StatisticListViewModel listViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistic);

        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.READ_EXTERNAL_STORAGE}, PackageManager.PERMISSION_GRANTED);
        setControl();

        setEvent();

    }


    private void setControl(){
        listView = findViewById(R.id.lvListStatistic);
    }

    private void setEvent(){

        data.add(new Statistic(1,"Xếp loại","Xem thống kê một lớp ở học kỳ nhất định có bao nhiêu học sinh giỏi, khá,... theo bảng điểm đã có "));
        data.add(new Statistic(2,"Phổ điểm tổng kết","Xem phổ điểm của lớp học nào đó trong học kỳ nhất định"));
        data.add(new Statistic(3,"Giới tính","Thống kê giới tính của lớp theo từng học kỳ "));

        listViewModel = new StatisticListViewModel(this, R.layout.activity_statistic_row, data);
        listView.setAdapter(listViewModel);
    }
}