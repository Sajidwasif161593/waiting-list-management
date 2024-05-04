package com.example.sajid_waiting_list_management;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class ViewCourseActivity extends AppCompatActivity {

    private ListView listView;
    private DBHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_courses);

        listView = findViewById(R.id.coursesListView);
        dbHandler = new DBHandler(ViewCourseActivity.this);

        List<String> courses = dbHandler.getAllCourseNames();

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, courses);
        listView.setAdapter(arrayAdapter);
    }
}
