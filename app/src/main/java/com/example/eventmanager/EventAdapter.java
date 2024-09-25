package com.example.eventmanager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;

public class EventAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Event> eventList;
    private DatabaseHelper databaseHelper;

    public EventAdapter(Context context, ArrayList<Event> eventList) {
        this.context = context;
        this.eventList = eventList;
        this.databaseHelper = new DatabaseHelper(context);
    }

    @Override
    public int getCount() {
        return eventList.size();
    }

    @Override
    public Object getItem(int position) {
        return eventList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return eventList.get(position).getId();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.event_item, parent, false);
        }

        TextView eventNameTextView = convertView.findViewById(R.id.eventNameTextView);
        Button deleteButton = convertView.findViewById(R.id.deleteEventButton);

        final Event event = eventList.get(position);
        eventNameTextView.setText(event.getName());

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseHelper.deleteEvent(event.getId());
                eventList.remove(position);  // Remove item from list
                notifyDataSetChanged();  // Notify adapter to refresh view
                Toast.makeText(context, "Event deleted", Toast.LENGTH_SHORT).show();
            }
        });

        return convertView;
    }
}
