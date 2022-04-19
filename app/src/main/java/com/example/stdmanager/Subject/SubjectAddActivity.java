package com.example.stdmanager.Subject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.stdmanager.R;
import com.example.stdmanager.models.Subject;

public class SubjectAddActivity extends AppCompatActivity {


    EditText txt_tenMH,txt_heSo,txt_namHoc;
    CheckBox cb_HK1,cb_HK2;
    AppCompatButton btn_save,btn_cancel;
    boolean isError;
    ImageView errorName,errorNH,errorHS,errorHK;
    String regex;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject_add);
        setControl();
        Validate();
        setEvent();
    }

    public void setControl()
    {
        regex = "\\d{4}-\\d{4}";
        errorName = findViewById(R.id.errorName);
        errorNH = findViewById(R.id.errorNH);
        errorHS = findViewById(R.id.errorHS);
        errorHK = findViewById(R.id.errorHK);

        txt_tenMH = findViewById(R.id.txt_tenMH);
        txt_heSo = findViewById(R.id.txt_HeSo);
        txt_namHoc = findViewById(R.id.txt_namHoc);
        cb_HK1 = findViewById(R.id.cb_HK1);
        cb_HK2 = findViewById(R.id.cb_HK2);
        btn_save = findViewById(R.id.Btn_save);
        btn_cancel = findViewById(R.id.Btn_cancel);

    }

    public void setEvent()
    {


//        BUTTON
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(errorName.getVisibility()==View.VISIBLE||txt_tenMH.getText().toString().isEmpty())
                {
                    isError=true;
                    errorName.setVisibility(View.VISIBLE);
                    txt_tenMH.requestFocus();
                    Toast.makeText(getApplicationContext(),"Tên môn học không hợp lệ ",Toast.LENGTH_SHORT).show();
                    return;
                }
                else if(errorHK.getVisibility()==View.VISIBLE||cb_HK1.isChecked()==cb_HK2.isChecked())
                {
                    isError=true;
                    errorHK.setVisibility(View.VISIBLE);
                    cb_HK1.requestFocus();
                    Toast.makeText(getApplicationContext(),"Vui lòng chọn học kì",Toast.LENGTH_SHORT).show();
                    return;
                }
                else if(errorHS.getVisibility()==View.VISIBLE||txt_heSo.getText().toString().isEmpty())
                {
                    isError=true;
                    errorHS.setVisibility(View.VISIBLE);
                    txt_heSo.requestFocus();
                    Toast.makeText(getApplicationContext(),"Hệ số không đúng (1 hoặc 2)",Toast.LENGTH_SHORT).show();
                    return;
                }
                else if(errorNH.getVisibility()==View.VISIBLE||txt_namHoc.getText().toString().isEmpty())
                {
                    isError=true;
                    errorNH.setVisibility(View.VISIBLE);
                    txt_namHoc.requestFocus();
                    Toast.makeText(getApplicationContext(),"Năm học không hợp lệ (2021-2022)",Toast.LENGTH_SHORT).show();
                    return;
                }

                if(!isError)
                {
                    Subject item = new Subject();
                    item.setTenMH(txt_tenMH.getText().toString());
                    item.setNamHoc(txt_namHoc.getText().toString());
                    if(cb_HK1.isChecked()&&!cb_HK2.isChecked())
                        item.setHocKy(1);
                    else if(cb_HK2.isChecked()&&!cb_HK1.isChecked())
                        item.setHocKy(2);
                    item.setHeSo(Integer.parseInt(txt_heSo.getText().toString()));
                    SubjectActivity.getmInstanceActivity().addSubject(item);
                    finish();
                }
                else{
                    Toast.makeText(getApplicationContext(),"Xảy ra lỗi",Toast.LENGTH_SHORT).show();
                }

            }
        });

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    private void Validate()
    {
        //        VALIDATE NAME
        txt_tenMH.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String tenMH = txt_tenMH.getText().toString();
                if(tenMH.isEmpty()||tenMH.matches(".*\\d.*"))
                {
                    isError = true;
                    errorName.setVisibility(View.VISIBLE);
                }
                else
                {
                    isError = false;
                    errorName.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        //        VALIDATE HE SO

        txt_heSo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String heSo = txt_heSo.getText().toString();
                if(heSo.isEmpty()||Integer.parseInt(heSo)>2)
                {
                    isError = true;
                    errorHS.setVisibility(View.VISIBLE);
                }
                else
                {
                    isError = false;
                    errorHS.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        //        VALIDATE NAM HOC
        txt_namHoc.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String namHoc = txt_namHoc.getText().toString();
                if(namHoc.matches(regex)&&namHoc.split("-")[0]!=namHoc.split("-")[1])
                {
                    errorNH.setVisibility(View.INVISIBLE);
                    isError = false;
                }
                else
                {
                    errorNH.setVisibility(View.VISIBLE);
                    isError = true;
                }

            }
            @Override
            public void afterTextChanged(Editable editable) {


            }
        });

//      VALIDATE COMBOBOX Hoc Ki
        cb_HK1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(cb_HK2.isChecked()&&cb_HK1.isChecked())
                    cb_HK2.setChecked(false);
                if(!cb_HK1.isChecked()&&!cb_HK2.isChecked()){
                    errorHK.setVisibility(View.VISIBLE);
                    isError = true;
                }
                else{
                    errorHK.setVisibility(View.INVISIBLE);
                    isError = false;
                }
            }
        });
        cb_HK2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(cb_HK1.isChecked()&&cb_HK2.isChecked())
                    cb_HK1.setChecked(false);
                if(!cb_HK1.isChecked()&&!cb_HK2.isChecked()){
                    errorHK.setVisibility(View.VISIBLE);
                    isError = true;
                }
                else{
                    errorHK.setVisibility(View.INVISIBLE);
                    isError = false;
                }
            }
        });

    }
}