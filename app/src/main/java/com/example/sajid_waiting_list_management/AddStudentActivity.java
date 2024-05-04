package com.example.sajid_waiting_list_management;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.List;

public class AddStudentActivity extends AppCompatActivity {

    private EditText studentNameInput;
    private Spinner courseSpinner, prioritySpinner;
    private DBHandler dbHandler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_student);

        // Initialize UI components
        studentNameInput = findViewById(R.id.studentNameInput);
        courseSpinner = findViewById(R.id.courseSpinner);
        prioritySpinner = findViewById(R.id.prioritySpinner);
        Button addButton = findViewById(R.id.addButton);
        Button viewButton = findViewById(R.id.viewButton);

        // Initialize DBHandler
        dbHandler = new DBHandler(this);


        setupPrioritySpinner();
        setupCourseSpinner();


        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addStudent();
            }
        });

        viewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddStudentActivity.this, ViewStudentActivity.class);
                startActivity(intent);
            }
        });
    }

    private void setupPrioritySpinner() {
        // ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.priority_levels, android.R.layout.simple_spinner_item);


        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        prioritySpinner.setAdapter(adapter);
    }

    private void setupCourseSpinner() {
        // Fetch course names from DBHandler (make sure this method is implemented in DBHandler)
        List<String> courseNames = dbHandler.getAllCourseNames();

        // ArrayAdapter for course names
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, courseNames);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        courseSpinner.setAdapter(adapter);
    }

    private void addStudent() {
        String studentName = studentNameInput.getText().toString().trim();
        String course = courseSpinner.getSelectedItem().toString();
        String priority = prioritySpinner.getSelectedItem().toString();

        if (studentName.isEmpty()) {
            Toast.makeText(AddStudentActivity.this, "Please enter the student's name", Toast.LENGTH_SHORT).show();
            return;
        }

        // Assuming addNewStudent method exists in DBHandler
        boolean isSuccess = dbHandler.addNewStudent(studentName, course, priority);

        if (isSuccess) {
            Toast.makeText(AddStudentActivity.this, "Student added successfully", Toast.LENGTH_SHORT).show();
            // Optionally clear the input fields
            studentNameInput.setText("");
            courseSpinner.setSelection(0);
            prioritySpinner.setSelection(0);
        } else {
            Toast.makeText(AddStudentActivity.this, "Failed to add student", Toast.LENGTH_SHORT).show();
        }
    }


}
