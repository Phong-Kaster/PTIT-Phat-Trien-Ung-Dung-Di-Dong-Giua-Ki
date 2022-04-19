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
import com.example.stdmanager.Score.ScoreStudentEditActivity;
import com.example.stdmanager.models.ScoreInfo;

import java.util.ArrayList;

public class ScoreStudentAdapter extends ArrayAdapter<ScoreInfo> {

    Context context;
    int resource;
    ArrayList<ScoreInfo> data;

    public ScoreStudentAdapter(@NonNull Context context, int resource,
    @NonNull ArrayList<ScoreInfo> data ){
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

        TextView tvStudentID = convertView.findViewById(R.id.student_code);
        TextView tvStudentName = convertView.findViewById(R.id.student_full_name);
        TextView tvStudentScore = convertView.findViewById(R.id.student_score);

        ScoreInfo score = data.get(position);

        String studentID = String.valueOf(score.getStudentID());
        String studentFullName = score.getStudentFullName();
        String studentScore = String.valueOf(score.getScore());

        ImageView imageView = convertView.findViewById(R.id.button_edit_score_subject);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ScoreStudentEditActivity.class);
                intent.putExtra("score", score);
                context.startActivity(intent);
            }
        });
        tvStudentID.setText(studentID);
        tvStudentName.setText(studentFullName);
        tvStudentScore.setText(studentScore);

        return convertView;
    }

}
