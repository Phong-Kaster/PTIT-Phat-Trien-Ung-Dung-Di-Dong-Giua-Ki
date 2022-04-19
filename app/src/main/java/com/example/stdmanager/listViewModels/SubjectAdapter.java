package com.example.stdmanager.listViewModels;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


import com.example.stdmanager.MainActivity;
import com.example.stdmanager.R;

import com.example.stdmanager.Subject.SubjectActivity;
import com.example.stdmanager.Subject.SubjectEditActivity;
import com.example.stdmanager.helpers.Alert;
import com.example.stdmanager.models.Subject;

import java.io.Serializable;
import java.util.ArrayList;

public class SubjectAdapter extends ArrayAdapter<Subject> {
    /*
     * Declare global variable
     * */
    ImageView btn_Edit;
    ImageView btn_Delete;
    Subject subject;
    Context context;
    int resource;
    ArrayList<Subject> data;
    ActivityResultLauncher<Intent> activityResultLauncher;

    public SubjectAdapter(@NonNull Context context, int resource,
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

//      1
//        ImageView avatar = convertView.findViewById(R.id.subjectIcon);
        btn_Edit = convertView.findViewById(R.id.btn_edit);
        btn_Delete = convertView.findViewById(R.id.btn_delete);
        TextView name = convertView.findViewById(R.id.subjectName);
        TextView NKHK = convertView.findViewById(R.id.subjectNKHK);
        TextView heSo = convertView.findViewById(R.id.subjectHS);
//      2
        subject = data.get(position);
        String subject_name = subject.getTenMH();
        String subject_NKHK = "Học kỳ: " +  subject.getHocKy() +" Năm học: "+subject.getNamHoc();
        String subject_hs = "Hệ số: " +subject.getHeSo();

        setEvent();


        btn_Edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Subject subject1 = data.get(position);
                Intent intent = new Intent(context, SubjectEditActivity.class);
                intent.putExtra("Subject",subject1);
                context.startActivity(intent);
            }
        });


        btn_Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Subject subject1 = data.get(position);
                Alert alert = new Alert(context);
                alert.confirm();
                alert.showAlert("Bạn có muốn xóa môn:\n"+subject1.getTenMH(), R.drawable.info_icon);
                alert.btnOK.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        SubjectActivity.getmInstanceActivity().delSubject(subject1);
                        alert.dismiss();
                    }
                });

                alert.btnCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        alert.dismiss();
                    }
                });

            }
        });
        name.setText(subject_name);
        NKHK.setText(subject_NKHK);
        heSo.setText(subject_hs);

        return convertView;
    }

    public void setEvent()
    {



    }

}
