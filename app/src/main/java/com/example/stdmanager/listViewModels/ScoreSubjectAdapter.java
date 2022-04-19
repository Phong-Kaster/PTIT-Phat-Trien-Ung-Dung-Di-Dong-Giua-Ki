package com.example.stdmanager.listViewModels;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.stdmanager.R;
import com.example.stdmanager.Score.ScoreStudentActivity;
import com.example.stdmanager.models.Subject;

import java.util.ArrayList;

public class ScoreSubjectAdapter extends ArrayAdapter<Subject> {

    Context context;
    int resource;
    ArrayList<Subject> data;

    public ScoreSubjectAdapter(@NonNull Context context, int resource,
                          @NonNull ArrayList<Subject> data ){
        super(context, resource,data);
        this.context = context;
        this.resource = resource;
        this.data = data;
    }
    @Override
    public int getCount() {
        return data.size();
    }

    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(resource,null);

        TextView name = convertView.findViewById(R.id.subjectName);
        TextView NKHK = convertView.findViewById(R.id.subjectNKHK);
        TextView heSo = convertView.findViewById(R.id.subjectHS);

        Subject subject = data.get(position);

        ImageView imageView = convertView.findViewById(R.id.button_edit_score_subject);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ScoreStudentActivity.class);
                intent.putExtra("subject", subject);
                context.startActivity(intent);
            }
        });
        String subject_name = subject.getTenMH();
        String subject_NKHK = "Học kỳ: " +  subject.getHocKy() +" Năm học: "+subject.getNamHoc();
        String subject_hs = "Hệ số: " +subject.getHeSo();

        name.setText(subject_name);
        NKHK.setText(subject_NKHK);
        heSo.setText(subject_hs);
        return convertView;
    }

}
