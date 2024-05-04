package com.example.sajid_waiting_list_management;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;


import java.util.List;

public class StudentsListAdapter extends ArrayAdapter<Student> {
    private final Context context;
    private final int resource;
    private final List<Student> students;

    public StudentsListAdapter(Context context, int resource, List<Student> students) {
        super(context, resource, students);
        this.context = context;
        this.resource = resource;
        this.students = students;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(resource, parent, false);
        }

        Student student = getItem(position);

        TextView tvStudentName = convertView.findViewById(R.id.tvStudentName);
        TextView tvStudentCourse = convertView.findViewById(R.id.tvStudentCourse);
        TextView tvStudentPriority = convertView.findViewById(R.id.tvStudentPriority);
        Button btnEditStudent = convertView.findViewById(R.id.btnEditStudent);
        Button btnDeleteStudent = convertView.findViewById(R.id.btnDeleteStudent);

        assert student != null;
        tvStudentName.setText(student.getName());
        tvStudentCourse.setText(student.getCourse());
        tvStudentPriority.setText(student.getPriority());

        btnEditStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, UpdateStudentActivity.class);
                intent.putExtra("id", student.getId()); // Pass the student ID to the UpdateStudentActivity
                context.startActivity(intent);
            }
        });
        btnDeleteStudent.setOnClickListener(v -> {

            students.remove(position);
            notifyDataSetChanged();

            String studentName;
            Toast.makeText(context, "Deleted record successfully!!", Toast.LENGTH_SHORT).show();
        });

        return convertView;
    }
}
