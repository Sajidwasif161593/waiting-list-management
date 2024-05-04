package com.example.sajid_waiting_list_management;

import android.os.Bundle;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class ViewStudentActivity extends AppCompatActivity {

    private ListView lvStudents;
    private StudentsListAdapter adapter;
    private DBHandler dbHandler;


    private static final int REQUEST_CODE_UPDATE_STUDENT = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_students);

        lvStudents = findViewById(R.id.lvStudents);
        dbHandler = new DBHandler(this);

        // Fetch the list of students from the database
        ArrayList<Student> studentList = dbHandler.getAllStudents();

        // Set up the adapter with the student list
        adapter = new StudentsListAdapter(this, R.layout.student_list_item, studentList);
        lvStudents.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();

        refreshStudentList();
    }

    private void refreshStudentList() {

        ArrayList<Student> studentList = dbHandler.getAllStudents();

        adapter = new StudentsListAdapter(this, R.layout.student_list_item, studentList);
        lvStudents.setAdapter(adapter);
    }
}
