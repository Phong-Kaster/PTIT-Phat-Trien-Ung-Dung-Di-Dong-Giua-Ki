package com.example.stdmanager.Event;

import com.example.stdmanager.models.Event;

public interface OnEvent {
    void onEditEvent(Event event,int position);
    void onEventDelete(Event event,int position);
}

