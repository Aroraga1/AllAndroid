package com.example.usingsqlitedatabase;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    EditText editTitle, editId;
    Button btnAdd, btnUpdate, btnDelete, btnView;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new DatabaseHelper(this);

        editTitle = findViewById(R.id.editTitle);
        editId = findViewById(R.id.editId);
        btnAdd = findViewById(R.id.btnAdd);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnDelete = findViewById(R.id.btnDelete);
        btnView = findViewById(R.id.btnView);

        btnAdd.setOnClickListener(view -> {
            String title = editTitle.getText().toString();
            if (db.insertNote(title)) {
                Toast.makeText(this, "Note Added", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Failed to Add Note", Toast.LENGTH_SHORT).show();
            }
        });

        btnUpdate.setOnClickListener(view -> {
            int id = Integer.parseInt(editId.getText().toString());
            String newTitle = editTitle.getText().toString();
            if (db.updateNote(id, newTitle)) {
                Toast.makeText(this, "Note Updated", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Failed to Update", Toast.LENGTH_SHORT).show();
            }
        });

        btnDelete.setOnClickListener(view -> {
            int id = Integer.parseInt(editId.getText().toString());
            if (db.deleteNote(id)) {
                Toast.makeText(this, "Note Deleted", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Failed to Delete", Toast.LENGTH_SHORT).show();
            }
        });

        btnView.setOnClickListener(view -> {
            Cursor cursor = db.getAllNotes();
            if (cursor.getCount() == 0) {
                showMessage("No Data", "Nothing found");
                return;
            }

            StringBuilder buffer = new StringBuilder();
            while (cursor.moveToNext()) {
                buffer.append("ID: ").append(cursor.getInt(0)).append("\n");
                buffer.append("Title: ").append(cursor.getString(1)).append("\n\n");
            }

            showMessage("Notes", buffer.toString());
        });
    }

    private void showMessage(String title, String message) {
        new android.app.AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(message)
                .setCancelable(true)
                .show();
    }
}
