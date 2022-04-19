package com.example.stdmanager.Event;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.stdmanager.DB.EventDBHelper;
import com.example.stdmanager.R;
import com.example.stdmanager.listViewModels.EventListViewModel;
import com.example.stdmanager.models.Event;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class EventActivity extends AppCompatActivity implements OnEvent {
    public  static WeakReference<EventActivity> weakReference;
    ListView listViewEvent;
    ArrayList <Event> eventArrayList;
    EventListViewModel listViewModel;
    SearchView EventSearch;
    Button btn_addEvent;
    Button btn_exportEvent;
    int PositionUpdate=-1;

    EventDBHelper db = new EventDBHelper(EventActivity.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_event);


        weakReference = new WeakReference<>(EventActivity.this);

        btn_addEvent=(Button) findViewById(R.id.eventButtonCreation);
        btn_exportEvent=(Button) findViewById(R.id.eventButtonExport);

        db.deletedAndCreateTable();
        // get Data
        eventArrayList = db.getAllEvents();

        setControl();
        setEvent();
        inItSearchWidgets();

    }

    private void setEvent() {

        /**
         * 1. Lấy data từ db
         * 2. Gán data vào adapter.
         * 3. đưa adapter vào listView
         *
         * Trong adpter có 1 layout -> tượng trung kiểu cho 1 dòng dữ liệu
         */

        listViewModel = new EventListViewModel(this, R.layout.activity_event_element, eventArrayList,this);
        listViewEvent.setAdapter(listViewModel);
        btn_addEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EventActivity.this, EventActivityCreation.class);
                startActivity(intent);
            }
        });
    }

    private void setControl() {
        listViewEvent = findViewById(R.id.eventListView);
        EventSearch = findViewById(R.id.eventSearchView);
    }

    private void inItSearchWidgets(){

        EventSearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                ArrayList<Event> filteredEvent = new ArrayList<>();

                for (Event evt: eventArrayList) {
                    if(evt.getNameEvent().toLowerCase().trim().contains(s.toLowerCase().trim()))
                    {
                        filteredEvent.add(evt);
                    }

                }
                setFilteredEvent(filteredEvent);
                return false;
            }
        });
    }

    private void setFilteredEvent(ArrayList<Event> filtered)
    {
        EventListViewModel eventmodel = new EventListViewModel(this, R.layout.activity_event_element, filtered,this);
        listViewEvent.setAdapter(eventmodel);
    }


    public void AddEvent(Event event){
        if(db.AddEvent(event)){
            eventArrayList.add(event);
            listViewModel.notifyDataSetChanged();
            Toast.makeText(this, "Them thanh cong", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "Them that bai", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onEditEvent(Event event,int position) {
        PositionUpdate=position;
        Intent intent=new Intent(this,EventActivityUpdate.class);
        intent.putExtra("EV",event);
        startActivityForResult(intent,100);
    }

    @Override
    public void onEventDelete(Event event, int position) {
        db.DeleteEvent(event);
        listViewModel.DeleteItem(event,position);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==100){
            if(resultCode==RESULT_OK){
                Event event= (Event) data.getSerializableExtra("Evupdate");
                listViewModel.UpdateItem(event,PositionUpdate);
            }
        }
    }
}