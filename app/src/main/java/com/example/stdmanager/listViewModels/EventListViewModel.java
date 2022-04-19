package com.example.stdmanager.listViewModels;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.stdmanager.Event.OnEvent;
import com.example.stdmanager.R;
import com.example.stdmanager.helpers.Alert;
import com.example.stdmanager.models.Event;

import java.util.ArrayList;


public class EventListViewModel extends ArrayAdapter<Event> {

    Context context;
    int resource;
    ArrayList<Event> objects;
    OnEvent onEvent;

    public void UpdateItem(Event ev,int Position){
        objects.set(Position,ev);
        notifyDataSetChanged();
    }
    public void DeleteItem(Event ev,int Position){
        objects.remove(Position);
        notifyDataSetChanged();
    }

    public EventListViewModel(@NonNull Context context, int resource, @NonNull ArrayList<Event> objects, OnEvent onEvent) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.objects = objects;
        this.onEvent = onEvent;
    }

    public int count() {
        return objects.size();
    }

    @NonNull
    @Override
    public View getView(int position, @NonNull View convertView, @NonNull ViewGroup parent) {

        // array list => element    - position = index.    => lấy 1 thằng đổ dữ liệu ra 1 layout -> trả về listview
        convertView = LayoutInflater.from(context).inflate(resource, null);

        /*Step 1*/
        TextView name = convertView.findViewById(R.id.eventName);
        TextView datetime = convertView.findViewById(R.id.eventDateTime);
        TextView place = convertView.findViewById(R.id.eventPlace);
        ImageView btn_edit = convertView.findViewById(R.id.btn_edit);
        ImageView btn_delete = convertView.findViewById(R.id.btn_delete);

        btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        /*Step 2*/
        Event event = objects.get(position);
        String eventName = event.getNameEvent();
        String eventDateTime = event.getStartTime() + "-" + event.getEndTime() + " " + event.getDay();
        String eventPlace = event.getPlace();

        /*Step 3*/
        name.setText(eventName);
        datetime.setText(eventDateTime);
        place.setText(eventPlace);


        btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onEvent.onEditEvent(event,position);
            }
        });

// Thông báo xóa
        Alert alert = new Alert(context);
        alert.confirm();

        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alert.showAlert("Bạn có chắc chắn xoá không?", R.drawable.info_icon);
            }
        });

        alert.btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onEvent.onEventDelete(event,position);
                alert.dismiss();
            }
        });

        alert.btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alert.dismiss();
            }
        });
        return convertView;

    }


}
