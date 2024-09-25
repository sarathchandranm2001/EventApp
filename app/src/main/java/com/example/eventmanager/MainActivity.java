package com.example.eventmanager;

import androidx.appcompat.app.AppCompatActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView eventListView;
    ArrayList<Event> eventList;
    EventAdapter eventAdapter;
    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        eventListView = findViewById(R.id.eventListView);
        databaseHelper = new DatabaseHelper(this);

        loadEvents();

        // Handle item click to edit event
        eventListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Event event = eventList.get(position);
                Intent intent = new Intent(MainActivity.this, AddEditEventActivity.class);
                intent.putExtra("eventId", event.getId());
                intent.putExtra("eventName", event.getName());
                startActivity(intent);
            }
        });

        // Handle long click to delete event
        eventListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("Delete Event")
                        .setMessage("Are you sure you want to delete this event?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                deleteEvent(position);
                            }
                        })
                        .setNegativeButton(android.R.string.no, null)
                        .show();
                return true;
            }
        });

        // Button to add new event
        findViewById(R.id.addEventButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddEditEventActivity.class);
                startActivity(intent);
            }
        });
    }

    private void loadEvents() {
        eventList = databaseHelper.getAllEvents();
        eventAdapter = new EventAdapter(this, eventList);
        eventListView.setAdapter(eventAdapter);
    }

    private void deleteEvent(int position) {
        Event event = eventList.get(position);
        databaseHelper.deleteEvent(event.getId());
        Toast.makeText(this, "Event deleted", Toast.LENGTH_SHORT).show();
        loadEvents();  // Refresh the list
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadEvents();
    }
}
