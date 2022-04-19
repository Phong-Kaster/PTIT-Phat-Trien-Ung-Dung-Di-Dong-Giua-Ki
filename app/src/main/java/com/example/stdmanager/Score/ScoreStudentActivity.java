package com.example.stdmanager.Score;

import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.pdf.PdfDocument;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.SearchView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.stdmanager.DB.GradeOpenHelper;
import com.example.stdmanager.DB.ScoreDBHelper;
import com.example.stdmanager.DB.StudentOpenHelper;
import com.example.stdmanager.R;
import com.example.stdmanager.listViewModels.ScoreStudentAdapter;
import com.example.stdmanager.models.Score;
import com.example.stdmanager.models.ScoreInfo;
import com.example.stdmanager.models.Session;
import com.example.stdmanager.models.Student;
import com.example.stdmanager.models.Subject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
public class ScoreStudentActivity extends AppCompatActivity {

    Session session;

    int pageHeight = 1120;
    int pagewidth = 792;

    ListView listView;
    Subject subject;
    ArrayList<Score> objects = new ArrayList<>();
    ArrayList<ScoreInfo> scores = new ArrayList<>();
    ScoreStudentAdapter listViewModel;

    GradeOpenHelper gradeOpenHelper = new GradeOpenHelper(this);
    ScoreDBHelper scoreDBHelper = new ScoreDBHelper(this);
    StudentOpenHelper studentDB = new StudentOpenHelper(this);

    TextView tvSubject;
    SearchView searchView;
    String teacher;
    String grade;
    String gradeName;
    AppCompatButton buttonSave;
    Bitmap bmp, scaledbmp;

    private static final int PERMISSION_REQUEST_CODE = 200;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        bmp = BitmapFactory.decodeResource(getResources(), R.drawable.avatar);
        scaledbmp = Bitmap.createScaledBitmap(bmp, 10, 10, false);

        session = new Session(ScoreStudentActivity.this);
        teacher = session.get("teacherId");
        grade = gradeOpenHelper.retriveIdByTeachId(teacher);
        gradeName = gradeOpenHelper.retrieveNameById(Integer.parseInt(grade));
        setContentView(R.layout.activity_score_student);
        subject = (Subject) getIntent().getSerializableExtra("subject");
        if (checkPermission()) {
            Log.i("Gain permistion",  "Permission Granted");
        } else {
            requestPermission();
        }
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        objects = scoreDBHelper.getAll();
        setControl();
        setEvent();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        setControl();
        setEvent();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void addStudentScore()
    {
        ArrayList<Student> students = studentDB.getStudentInGrade(grade);
        for(Student student: students)
        {
            ArrayList<Score> sc = scoreDBHelper.getStudentAndSubject(String.valueOf(student.getId()), String.valueOf(subject.getMaMH()));
            if(sc.size() >0 ) continue;;
            Score score = new Score(student.getId(), subject.getMaMH(), 0);
            scoreDBHelper.add(score);
        }
    }
    private ArrayList<ScoreInfo> getData()
    {
        ArrayList<ScoreInfo> scores = new ArrayList<>();
        ArrayList<Student> students = studentDB.getStudentInGrade(grade);
        for(Student student: students)
        {
            ArrayList<Score> sc = scoreDBHelper.getStudentAndSubject(String.valueOf(student.getId()), String.valueOf(subject.getMaMH()));
            if(sc.size() <= 0)
            {
                return null;
            }
            Score i = sc.get(0);
            ScoreInfo sci = new ScoreInfo(i.getMaHS(), i.getMaMH(), i.getDiem(),
                    student.getFamilyName() + " " + student.getFirstName(),
                    subject.getTenMH());
            scores.add(sci);
        }
        return scores;
    }

    @SuppressLint("SetTextI18n")
    private void setControl()
    {
        listView = findViewById(R.id.score_student_list_view);
        searchView = findViewById(R.id.score_student_search_bar);
        tvSubject = findViewById(R.id.score_student_subject_name);
        buttonSave = findViewById(R.id.button_score_student_save_pdf);
        tvSubject.setText("Môn học: " + subject.getTenMH());
    }

    private void setEvent()
    {
        addStudentScore();
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                generatePDF();
            }
        });
        try {
            scores = getData();
            scores.size();
            listViewModel = new ScoreStudentAdapter(this, R.layout.activity_score_student_element, scores);
            listView.setAdapter(listViewModel);
        }catch (NullPointerException ex)
        {
            finish();
            Log.e("ScoreStudentActivity", "lỗi lấy danh sách sinh viên bị rỗng");
        }
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                ScoreStudentActivity.this.listViewModel.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                ArrayList<ScoreInfo> filtered = new ArrayList<>();
                for (ScoreInfo score: scores) {
                    if(score.getStudentFullName()
                            .toLowerCase()
                            .trim()
                            .contains(newText.toLowerCase().trim())
                            ||String.valueOf(score.getStudentID())
                            .toLowerCase()
                            .trim()
                            .contains(newText.toLowerCase().trim()))
                    {
                        filtered.add(score);
                    }
                }
                filteredScore(filtered);
                return false;
            }
        });
    }
    private void generatePDF() {
        PdfDocument pdfDocument = new PdfDocument();
        Paint title = new Paint();

        PdfDocument.PageInfo mypageInfo = new PdfDocument.PageInfo.Builder(pagewidth, pageHeight, 1).create();
        PdfDocument.Page myPage = pdfDocument.startPage(mypageInfo);
        Canvas canvas = myPage.getCanvas();
        title.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
        title.setTextSize(17);
        title.setColor(ContextCompat.getColor(this, R.color.black));
        canvas.drawText("Danh sách điểm sinh viên.", 100, 50, title);

        title.setTextSize(15);
        canvas.drawText("Môn:" , 100, 75, title);
        canvas.drawText("Lớp:", 100, 100, title);
        canvas.drawText(subject.getTenMH(), 200, 75, title);
        canvas.drawText(gradeName, 200, 100, title);

        title.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
        title.setColor(ContextCompat.getColor(this, R.color.black));
        title.setTextSize(15);
        title.setTextAlign(Paint.Align.CENTER);

        int y = 130;
        for(ScoreInfo score: scores)
        {
            if (y >= 1000)
            {
                y = 130;
            }
            y += 30;
            canvas.drawText(String.valueOf(score.getStudentID()), 100, y , title);
            canvas.drawText(String.valueOf(score.getStudentFullName()), 200, y , title);
            canvas.drawText(String.valueOf(score.getScore()), 700, y, title);
        }
        pdfDocument.finishPage(myPage);

        File file = new File(getApplicationContext().getFilesDir(), subject.getMaMH()
                + "-" + subject.getTenMH()
                + "-" + gradeName
                + ".pdf");
        try {
            pdfDocument.writeTo(new FileOutputStream(file));
            Toast.makeText(ScoreStudentActivity.this, "PDF file generated successfully."
                    + file.getAbsolutePath(), Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            Log.e("GeneratePDF", "PDF file generated successfully" + e.toString());
            Toast.makeText(ScoreStudentActivity.this, "PDF file generated Failed.",
                    Toast.LENGTH_SHORT).show();
        }
        pdfDocument.close();
    }
    private boolean checkPermission() {
        int permission1 = ContextCompat.checkSelfPermission(getApplicationContext(), WRITE_EXTERNAL_STORAGE);
        int permission2 = ContextCompat.checkSelfPermission(getApplicationContext(), READ_EXTERNAL_STORAGE);
        return permission1 == PackageManager.PERMISSION_GRANTED && permission2 == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(this, new String[]{WRITE_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0) {

                boolean writeStorage = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                boolean readStorage = grantResults[1] == PackageManager.PERMISSION_GRANTED;

                if (writeStorage && readStorage) {
                    Toast.makeText(this, "Permission Granted..", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Permission Denined.", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        }
    }
    private void filteredScore(ArrayList<ScoreInfo> filtered)
    {
        listViewModel = new ScoreStudentAdapter(this, R.layout.activity_score_student_element, filtered);
        listView.setAdapter(listViewModel);
    }

}
