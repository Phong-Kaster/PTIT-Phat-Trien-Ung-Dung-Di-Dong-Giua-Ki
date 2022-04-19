package com.example.stdmanager.Score;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.example.stdmanager.DB.ScoreDBHelper;
import com.example.stdmanager.R;
import com.example.stdmanager.helpers.Alert;
import com.example.stdmanager.helpers.InputFilterMinMax;
import com.example.stdmanager.models.ScoreInfo;
import com.example.stdmanager.models.Session;

public class ScoreStudentEditActivity extends AppCompatActivity {

    Session session;
    ScoreInfo score;
    ScoreDBHelper scoreDBHelper = new ScoreDBHelper(this);
    TextView tvSubject;
    TextView tvStudentID;
    TextView tvStudentName;
    EditText editScore;

    AppCompatButton buttonSave;

    Alert alert;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        session = new Session(ScoreStudentEditActivity.this);
        setContentView(R.layout.activity_score_student_edit);
        score = (ScoreInfo) getIntent().getSerializableExtra("score");
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        alert = new Alert(ScoreStudentEditActivity.this);
        alert.normal();

        setControl();
        setEvent();

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }
    @SuppressLint("SetTextI18n")
    private void setControl()
    {
        tvSubject = findViewById(R.id.tv_score_student_edit_subject);
        tvStudentID = findViewById(R.id.tv_score_student_edit_student_id);
        tvStudentName = findViewById(R.id.tv_score_student_edit_student_name);
        editScore = findViewById(R.id.edit_text_score_student_edit_score);
        buttonSave = findViewById(R.id.button_scoreave_student_s);
        editScore.setFilters(new InputFilter[]{ new InputFilterMinMax("0", "10")});

        tvSubject.setText(score.getSubjectName());
        tvStudentID.setText(String.valueOf(score.getStudentID()));
        editScore.setText(String.valueOf(score.getScore()));
        tvStudentName.setText(score.getStudentFullName());
    }
    private void setEvent()
    {
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                double scoreValue = Double.parseDouble(editScore.getText().toString());

                if(scoreValue > 10 || scoreValue < 0)
                {
                    alert.showAlert("Điểm nhập vào không hợp lệ", R.drawable.info_icon);
                }
                else
                {
                    try {
                        score.setScore(scoreValue);
                        scoreDBHelper.update(score.getStudentID(), score.getSubjectID(), score.getScore());
                        alert.showAlert("Thay đổi điểm thành công", R.drawable.check_icon);
                    }catch(Exception ex)
                    {
                        alert.showAlert(ex.toString(), R.drawable.info_icon);
                    }
                }

            }
        });
        alert.btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alert.btnOK.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        alert.dismiss();
                    }
                });
            }
        });
    }
}
