package com.example.stdmanager.Statistic;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ListView;
import android.widget.SearchView;

import com.example.stdmanager.DB.SubjectDBHelper;
import com.example.stdmanager.R;
import com.example.stdmanager.listViewModels.SubjectAdapter;
import com.example.stdmanager.listViewModels.SubjectListAdapter;
import com.example.stdmanager.models.Statistic;
import com.example.stdmanager.models.Subject;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class SubjectListActivity extends AppCompatActivity {

    public static WeakReference<SubjectListActivity> weakActivity;
    Statistic item;
    ListView listView;
    ArrayList<Subject> objects = new ArrayList<>();
    SubjectListAdapter listViewModel;
    SubjectDBHelper subjectDB = new SubjectDBHelper(this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject_list);
        weakActivity = new WeakReference<>(SubjectListActivity.this);
        /*The command line belows that make sure that keyboard only pops up only if user clicks into EditText */
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        item = (Statistic) getIntent().getSerializableExtra("detail");

        objects = subjectDB.getAllSubjects();

        /*Step 3*/
        setControl();


        /*Step 4*/
        setEvent();
        inItSearchWidgets();

    }


    private void inItSearchWidgets(){
        SearchView searchView = findViewById(R.id.searchSubject);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                ArrayList<Subject> filteredSubject = new ArrayList<Subject>();

                for (Subject subject: objects) {
                    if(subject.getTenMH().toLowerCase().trim().contains(s.toLowerCase().trim()))
                    {
                        filteredSubject.add(subject);
                    }

                }
                setFilteredSubject(filteredSubject);
                return false;
            }
        });
    }

    private void setControl()
    {
        listView = findViewById(R.id.subjectListView);
    }

    private void setFilteredSubject(ArrayList<Subject> filtered)
    {
        SubjectListAdapter subjectAdapter = new SubjectListAdapter(this, R.layout.statistic_subject_element, filtered, item);
        listView.setAdapter(subjectAdapter);
    }

    private void setEvent()
    {
        listViewModel = new SubjectListAdapter(this, R.layout.statistic_subject_element, objects, item);
        listView.setAdapter(listViewModel);
    }

    public static SubjectListActivity getmInstanceActivity() {
        return weakActivity.get();
    }
}