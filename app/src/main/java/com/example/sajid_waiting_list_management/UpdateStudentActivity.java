package com.example.sajid_waiting_list_management;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class UpdateStudentActivity extends AppCompatActivity {

    private EditText editTextStudentName, editTextStudentCourse, editTextStudentPriority;
    private Button buttonSaveStudent;
    private DBHandler dbHandler;
    private int studentId; // Assuming the student ID is passed through the intent

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_student);

        editTextStudentName = findViewById(R.id.editTextStudentName);
        editTextStudentCourse = findViewById(R.id.editTextStudentCourse);
        editTextStudentPriority = findViewById(R.id.editTextStudentPriority);
        buttonSaveStudent = findViewById(R.id.buttonSaveStudent);

        dbHandler = new DBHandler(this);

        // Get the student ID from the intent
        studentId = getIntent().getIntExtra("id", -1); // Default to -1 if ID not found
        loadStudentData(studentId);

        buttonSaveStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateStudent();
            }
        });
    }

    private void loadStudentData(int id) {
        if (id != -1) {
            Student student = dbHandler.getStudent(id); // Implement getStudent(int id) in DBHandler to fetch a student by ID
            if (student != null) {
                editTextStudentName.setText(student.getName());
                editTextStudentCourse.setText(student.getCourse());
                editTextStudentPriority.setText(student.getPriority());
            }
        }
    }

    private void updateStudent() {
        String name = editTextStudentName.getText().toString().trim();
        String course = editTextStudentCourse.getText().toString().trim();
        String priority = editTextStudentPriority.getText().toString().trim();

        if (!name.isEmpty() && !course.isEmpty() && !priority.isEmpty()) {
            boolean updateSuccessful = dbHandler.updateStudent(new Student(studentId, name, course, priority));

            if (updateSuccessful) {
                Toast.makeText(UpdateStudentActivity.this, "Student updated successfully", Toast.LENGTH_SHORT).show();
                setResult(RESULT_OK); // Indicate success
                finish(); // Finish activity and return to the calling activity
            } else {
                Toast.makeText(UpdateStudentActivity.this, "Failed to update student", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(UpdateStudentActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
        }
    }
}
