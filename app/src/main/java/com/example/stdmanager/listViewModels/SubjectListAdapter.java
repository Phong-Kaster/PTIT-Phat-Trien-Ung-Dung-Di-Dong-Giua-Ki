package com.example.stdmanager.listViewModels;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


import com.example.stdmanager.R;

import com.example.stdmanager.Statistic.MarkStatsActivity;
import com.example.stdmanager.Subject.SubjectActivity;
import com.example.stdmanager.Subject.SubjectEditActivity;
import com.example.stdmanager.helpers.Alert;
import com.example.stdmanager.models.Statistic;
import com.example.stdmanager.models.Subject;

import java.util.ArrayList;

public class SubjectListAdapter extends ArrayAdapter<Subject> {
    /*
     * Declare global variable
     * */
    ImageView btn_Edit;
    Subject subject;
    Statistic statistic;
    Context context;
    int resource;
    ArrayList<Subject> data;

    public SubjectListAdapter(@NonNull Context context, int resource,
                          @NonNull ArrayList<Subject> data, Statistic statistic ){
        super(context, resource,data);
        this.context = context;
        this.resource = resource;
        this.data = data;
        this.statistic = statistic;
    }



    @Override
    public int getCount() {
        return data.size();
    }

    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(resource,null);

//      1
//        ImageView avatar = convertView.findViewById(R.id.subjectIcon);
        btn_Edit = convertView.findViewById(R.id.btn_edit);

        TextView name = convertView.findViewById(R.id.subjectName);
        TextView NKHK = convertView.findViewById(R.id.subjectNKHK);
        TextView heSo = convertView.findViewById(R.id.subjectHS);
//      2
        subject = data.get(position);
        String subject_name = subject.getTenMH();
        String subject_NKHK = "Học kỳ: " +  subject.getHocKy() +" Năm học: "+subject.getNamHoc();
        String subject_hs = "Hệ số: " +subject.getHeSo();

        name.setText(subject_name);
        NKHK.setText(subject_NKHK);
        heSo.setText(subject_hs);

        btn_Edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Subject subject1 = data.get(position);
                Intent intent = new Intent(context, MarkStatsActivity.class);
                intent.putExtra("Subject", subject1);
                intent.putExtra("Statistic", statistic);
                context.startActivity(intent);
            }
        });


        return convertView;
    }
}
