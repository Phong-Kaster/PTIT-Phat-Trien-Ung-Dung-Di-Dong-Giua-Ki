package com.example.stdmanager.Classroom;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.example.stdmanager.App;
import com.example.stdmanager.DB.GradeOpenHelper;
import com.example.stdmanager.DB.StudentOpenHelper;
import com.example.stdmanager.R;
import com.example.stdmanager.listViewModels.ClassroomListViewModel;
import com.example.stdmanager.models.Grade;
import com.example.stdmanager.models.Session;
import com.example.stdmanager.models.Student;
import com.example.stdmanager.models.Teacher;

import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Locale;

public class ClassroomActivity extends AppCompatActivity {

    public static WeakReference<ClassroomActivity> weakActivity;

    Session session;

    ListView listView;
    ArrayList<Student> objects = new ArrayList<>();
    ClassroomListViewModel listViewModel;

    ArrayList<Grade> gradeObjects;
    GradeOpenHelper gradeOpenHelper = new GradeOpenHelper(this);
    StudentOpenHelper studentOpenHelper = new StudentOpenHelper(this);


    AppCompatButton buttonCreation, buttonExport;
    SearchView searchView;


    /**
     * @author Phong Kaster
     * Step 1: declare local variable
     * Step 2: set default records up
     * Step 3: set Control to components
     * Step 4: listening events which is triggered by components
     * */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        /*Step 1*/
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classroom);

        weakActivity = new WeakReference<>(ClassroomActivity.this);
        session = new Session(ClassroomActivity.this);
        /*The command line belows that make sure that keyboard only pops up only if user clicks into EditText */
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        /*Step 2*/
        //gradeOpenHelper.deleteAndCreatTable();
        gradeObjects = gradeOpenHelper.retrieveAllGrades();

        //studentOpenHelper.deleteAndCreateTable();
        objects = studentOpenHelper.retrieveAllStudents();


        /*Step 3*/
        setControl();


        /*Step 4*/
        setEvent();
        searchByKeyword();

        /*Step 5*/
        String teacherId = session.get("teacherId");
        String value = gradeOpenHelper.retriveIdByTeachId( teacherId );
        session.set("gradeId", value );
    }

    public static ClassroomActivity getmInstanceActivity() {
        return weakActivity.get();
    }

    private void setControl()
    {
        listView = findViewById(R.id.classroomListView);
        buttonCreation = findViewById(R.id.classroomButtonCreation);
        buttonExport = findViewById(R.id.classroomButtonExport);
        searchView = findViewById(R.id.classroomSearchView);
    }


    /**
     * @author Phong-Kaster
     * set up events that these components are triggered
     *
     * Step 1: declare list view & list view model
     * Step 2: listen events which is triggered by component
     * */
    private void setEvent()
    {
        /*Step 1*/
        listViewModel = new ClassroomListViewModel(this, R.layout.activity_classroom_element, objects);
        listView.setAdapter(listViewModel);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                Student student = (Student) adapterView.getAdapter().getItem(position);

                /*Open the screen which shows student's detail*/
                Intent intent = new Intent(ClassroomActivity.this, ClassroomIndividualActivity.class);
                /*Pass student object to next activity - Student class must implements Serializable*/
                intent.putExtra("student", student );
                /*start next activity*/
                startActivity(intent);

            }
        });

        /*Step 2*/
        buttonCreation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ClassroomActivity.this, ClassroomCreationActivity.class);
                startActivity(intent);
            }
        });

        buttonExport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ClassroomActivity.this, ClassroomExportActivity.class);
                startActivity(intent);
            }
        });
    }


    /**
     * @author Phong-Kaster
     * Step 1: add the new student to objest - like AJAX to load static data
     * Step 2: add the new student to database
     * Step 3: notify data changed !
     * Step 4: toast to show notice on screen
     * */
    public void createStudent(Student student)
    {
        /* Temporary Solution so as to the Grade Name is null */
        student.setGradeName( objects.get(0).getGradeName() );
        /*Step 1*/ objects.add(student);

        /*Step 2*/ studentOpenHelper.create(student);

        /*Step 3*/ listViewModel.notifyDataSetChanged();

        /*Step 4*/ Toast.makeText(this, "Thêm thành công", Toast.LENGTH_LONG).show();
    }

    /**
     * @author Phong-Kaster
     * Step 1: objects.remove(student) make notifiyDataSetChanged doesn't work
     * Step 2: notifiyDataSetChanged immediately !
     * Step 3: delete student in database table
     * Step 4: Toast to show notice on screen
     * */
    public void deleteStudent(Student student)
    {
        if( student.getId() == 0 ) {
            Toast.makeText(this, "ID không hợp lệ", Toast.LENGTH_LONG).show();
            return;
        }
        /*Step 1*/
        for(int i = 0; i < objects.size(); i++)
        {
            if( objects.get(i).getId() == student.getId())
            {
                objects.remove( objects.get(i));
            }
        }
        /*Step 2*/ listViewModel.notifyDataSetChanged();

        /*Step 3*/ studentOpenHelper.delete(student);

        /*Step 4*/ Toast.makeText(this, "Xóa thành công", Toast.LENGTH_LONG).show();
    }

    public void updateStudent(Student student)
    {
        if( student.getId() == 0 ) {
            Toast.makeText(this, "ID không hợp lệ", Toast.LENGTH_LONG).show();
            return;
        }
        /*Step 1*/
        for (Student element: objects) {
            if(element.getId() == student.getId())
            {
                element.setFamilyName( student.getFamilyName() );
                element.setFirstName( student.getFirstName() );

                element.setGender( student.getGender() );
                element.setBirthday( student.getBirthday() );

                element.setGradeId( student.getGradeId() );
                element.setGradeName( student.getGradeName() );
            }
        }

        /*Step 2*/ listViewModel.notifyDataSetChanged();

        /*Step 3*/ studentOpenHelper.update(student);

        /*Step 4*/ Toast.makeText(this, "Cập nhật thành công", Toast.LENGTH_LONG).show();
    }

    private void searchByKeyword()
    {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                ArrayList<Student> array = new ArrayList<>();

                for (Student student: objects) {
                    String firstName = student.getFirstName().toLowerCase(Locale.ROOT);
                    String keyword = s.toLowerCase(Locale.ROOT);

                    if( firstName.contains(keyword) )
                    {
                        array.add(student);
                    }
                }
                setListView(array);

                return false;
            }
        });
    }

    private void setListView(ArrayList<Student> array)
    {
        listViewModel = new ClassroomListViewModel(this, R.layout.activity_classroom_element, array);
        listView.setAdapter(listViewModel);
        listViewModel.notifyDataSetChanged();
    }
}