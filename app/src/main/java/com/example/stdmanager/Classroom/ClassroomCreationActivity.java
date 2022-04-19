package com.example.stdmanager.Classroom;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.AppCompatButton;

import com.example.stdmanager.App;
import com.example.stdmanager.R;
import com.example.stdmanager.models.Session;
import com.example.stdmanager.models.Student;
import com.example.stdmanager.models.Teacher;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Pattern;

public class ClassroomCreationActivity extends AppCompatActivity {

    Session session;

    EditText familyName, firstName, birthday;
    RadioButton male, female;
    AppCompatButton buttonConfirm, buttonCancel;

    ImageButton buttonBirthday;

    private final Calendar cal = Calendar.getInstance();
    private final int year = cal.get(Calendar.YEAR);
    private final int month = cal.get(Calendar.MONTH) + 1;
    private final int day = cal.get(Calendar.DAY_OF_MONTH);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classroom_creation);
        session = new Session(ClassroomCreationActivity.this);
        setControl();
        setEvent();

        String today = day + "/" + month + "/" + year;
        birthday.setText("01/05/2000");
    }

    private void setControl()
    {
        buttonConfirm = findViewById(R.id.classroomCreationButtonConfirm);
        buttonCancel = findViewById(R.id.classroomCreationButtonGoBack);


        familyName = findViewById(R.id.classroomCreationFamilyName);
        firstName = findViewById(R.id.classroomCreationFirstName);

        male = findViewById(R.id.classroomCreationRadioButtonMale);
        female = findViewById(R.id.classroomCreationRadioButtonFemale);

        buttonBirthday = findViewById(R.id.classroomCreationButtonBirthday2);
        birthday = findViewById(R.id.classroomCreationBirthday);
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    public void openDatePicker(View view)
    {
        /*Step 1*/
        DatePickerDialog.OnDateSetListener dateSetListener = (datePicker, year, month, day) -> {
            month = month + 1;


            Date date = new Date(year-1900, month, day);
            SimpleDateFormat formatter =  new SimpleDateFormat("dd/MM/yyyy");

            String birthdayValue = formatter.format(date);

            birthday.setText(birthdayValue);
        };

        /*Step 2*/
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);


        int style;
        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
            style = AlertDialog.THEME_DEVICE_DEFAULT_DARK;
        } else {
            style = AlertDialog.THEME_DEVICE_DEFAULT_LIGHT;
        }

        /*Step 3*/
        DatePickerDialog datePicker = new DatePickerDialog(this, style, dateSetListener, year, month, day);
        datePicker.show();
    }


    private void setEvent()
    {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            birthday.setOnClickListener(this::openDatePicker);
        }
        buttonConfirm.setOnClickListener(view -> {

            int gender = male.isChecked() ? 0 : 1;
            int gradeId = Integer.parseInt( session.get("gradeId"));

            Student student = new Student();
            student.setFamilyName( familyName.getText().toString() );
            student.setFirstName( firstName.getText().toString() );
            student.setGender(gender);
            student.setGradeId(gradeId);
            student.setBirthday( birthday.getText().toString());

            boolean flag = validateStudentInformation(student);
            if( !flag )
                return;

            ClassroomActivity.getmInstanceActivity().createStudent(student);
            finish();
        });

        buttonCancel.setOnClickListener(view -> finish());
    }

    private boolean validateStudentInformation(Student student) {

        String VIETNAMESE_DIACRITIC_CHARACTERS
                = "ẮẰẲẴẶĂẤẦẨẪẬÂÁÀÃẢẠĐẾỀỂỄỆÊÉÈẺẼẸÍÌỈĨỊỐỒỔỖỘÔỚỜỞỠỢƠÓÒÕỎỌỨỪỬỮỰƯÚÙỦŨỤÝỲỶỸỴ" +
                "áảấẩắẳóỏốổớởíỉýỷéẻếểạậặọộợịỵẹệãẫẵõỗỡĩỹẽễàầằòồờìỳèềaâăoôơiyeêùừụựúứủửũữuư";

        Pattern pattern = Pattern.compile("(?:[" + VIETNAMESE_DIACRITIC_CHARACTERS + "]|[a-zA-Z])++");

        boolean flagFamilyName = pattern.matcher(student.getFamilyName()).matches();
        boolean flagFirstName = pattern.matcher(student.getFirstName()).matches();

        if (!flagFamilyName || !flagFirstName) {
            Toast.makeText(ClassroomCreationActivity.this, "Nội dung nhập vào không hợp lệ", Toast.LENGTH_LONG).show();
            return false;
        }

        int yearBirhday = Integer.parseInt( student.getBirthday().substring(6) );
        int flagAge = year - yearBirhday;
        if( flagAge < 18)
        {
            Toast.makeText(ClassroomCreationActivity.this, "Tuổi không nhỏ hơn 18", Toast.LENGTH_LONG).show();
            return false;
        }


        return true;
    }
}