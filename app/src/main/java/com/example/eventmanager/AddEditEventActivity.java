package com.example.eventmanager;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddEditEventActivity extends AppCompatActivity {

    EditText eventNameEditText;
    Button saveEventButton;
    DatabaseHelper databaseHelper;
    int eventId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_event);

        eventNameEditText = findViewById(R.id.eventNameEditText);
        saveEventButton = findViewById(R.id.saveEventButton);
        databaseHelper = new DatabaseHelper(this);

        // Check if we're updating an event (if eventId is passed)
        if (getIntent().hasExtra("eventId")) {
            eventId = getIntent().getIntExtra("eventId", -1);
            String eventName = getIntent().getStringExtra("eventName");
            eventNameEditText.setText(eventName);
        }

        // When save button is clicked, save the event
        saveEventButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveEvent();
            }
        });
    }

    private void saveEvent() {
        String eventName = eventNameEditText.getText().toString().trim();

        if (eventName.isEmpty()) {
            Toast.makeText(this, "Please enter event name", Toast.LENGTH_SHORT).show();
            return;
        }

        // If eventId is -1, it's a new event, otherwise we're updating an existing one
        if (eventId == -1) {
            // Insert new event
            databaseHelper.insertEvent(eventName);
            Toast.makeText(this, "Event created", Toast.LENGTH_SHORT).show();
        } else {
            // Update existing event
            databaseHelper.updateEvent(eventId, eventName);
            Toast.makeText(this, "Event updated", Toast.LENGTH_SHORT).show();
        }

        // Go back to main screen after saving
        finish();
    }
}
