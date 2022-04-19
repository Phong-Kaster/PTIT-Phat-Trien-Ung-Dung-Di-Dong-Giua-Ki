package com.example.stdmanager.Event;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.stdmanager.R;
import com.example.stdmanager.models.Event;


public class EventActivityCreation extends AppCompatActivity {
    Button saveEvent;
    Button troVeBT;
    EditText eventName, startEV,endEV,Place, Date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_creation);


        setControl();
        setEvent();
    }
    private void setEvent () {
        saveEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String EVname=eventName.getText().toString();
                String EVstart=startEV.getText().toString();
                String EVend=endEV.getText().toString();
                String EVdate=Date.getText().toString();
                String EVPlace=Place.getText().toString();

                Event event=new Event();
                event.setNameEvent(EVname);
                event.setStartTime(EVstart);
                event.setEndTime(EVend);
                event.setDay(EVdate);
                event.setPlace(EVPlace);

                EventActivity.weakReference.get().AddEvent(event);

                Intent intent=new Intent();
                setResult(RESULT_OK,intent);
                finish();
            }
        });
        troVeBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                Toast.makeText(EventActivityCreation.this,"Da Thoat",Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void setControl () {
        saveEvent=findViewById(R.id.eventCreationButtonConfirm);
        troVeBT=findViewById(R.id.eventCreationButtonGoBack);
        eventName=findViewById(R.id.eventNameAdd);
        startEV=findViewById(R.id.StartEV);
        endEV=findViewById(R.id.endEV);
        Date=findViewById(R.id.dateEV);
        Place=findViewById(R.id.eventPlaceAdd);

    }
}