package com.example.sajid_waiting_list_management;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.Cursor;
import java.util.ArrayList;
import java.util.List;

public class DBHandler extends SQLiteOpenHelper {

    // Database Info
    private static final String DB_NAME = "coursedb";
    private static final int DB_VERSION = 1;

    // Course Table Info
    private static final String TABLE_COURSES = "mycourses";
    private static final String ID_COL = "id";
    private static final String NAME_COL = "name";
    private static final String DURATION_COL = "duration";
    private static final String DESCRIPTION_COL = "description";
    private static final String TRACKS_COL = "tracks";

    // Student Table Info
    private static final String TABLE_STUDENTS = "students";
    private static final String STUDENT_ID = "id";
    private static final String STUDENT_NAME = "name";
    private static final String COURSE_NAME = "course_name";
    private static final String PRIORITY_LEVEL = "priority_level";

    public DBHandler(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // SQL query to create the courses table
        String createCoursesTable = "CREATE TABLE " + TABLE_COURSES + " (" +
                ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                NAME_COL + " TEXT," +
                DURATION_COL + " TEXT," +
                DESCRIPTION_COL + " TEXT," +
                TRACKS_COL + " TEXT)";

        // SQL query to create the students table
        String createStudentsTable = "CREATE TABLE " + TABLE_STUDENTS + " (" +
                STUDENT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                STUDENT_NAME + " TEXT," +
                COURSE_NAME + " TEXT," +
                PRIORITY_LEVEL + " INTEGER)";

        // Executing SQL queries to create tables
        db.execSQL(createCoursesTable);
        db.execSQL(createStudentsTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older tables if they exist
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_COURSES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_STUDENTS);
        // Create tables again
        onCreate(db);
    }

    // Method to add a new course
    public void addNewCourse(String courseName, String courseDuration, String courseDescription, String courseTracks) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(NAME_COL, courseName);
        values.put(DURATION_COL, courseDuration);
        values.put(DESCRIPTION_COL, courseDescription);
        values.put(TRACKS_COL, courseTracks);
        db.insert(TABLE_COURSES, null, values);
        db.close();
    }

    // Method to fetch all course names
    public List<String> getAllCourseNames() {
        List<String> courseNames = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String[] columns = {NAME_COL};

        Cursor cursor = db.query(TABLE_COURSES, columns, null, null, null, null, null);

        // Get the index of the NAME_COL column
        int nameColIndex = cursor.getColumnIndex(NAME_COL);

        // Check if the column index is valid
        if (nameColIndex != -1) {
            // Iterate over the cursor
            if (cursor.moveToFirst()) {
                do {
                    // Use the column index to retrieve the course name
                    String courseName = cursor.getString(nameColIndex);
                    courseNames.add(courseName);
                } while (cursor.moveToNext());
            }
        }

        // Close the cursor and database
        cursor.close();
        db.close();

        return courseNames;
    }

    // Method to add a new student
    public boolean addNewStudent(String studentName, String course, String priority) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(STUDENT_NAME, studentName);
        values.put(COURSE_NAME, course);
        values.put(PRIORITY_LEVEL, priority);
        long newRowId = db.insert(TABLE_STUDENTS, null, values);
        db.close();
        return newRowId != -1;
    }

    public ArrayList<Student> getAllStudents() {
        ArrayList<Student> studentList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String[] projection = {
                STUDENT_ID,
                STUDENT_NAME,
                COURSE_NAME,
                PRIORITY_LEVEL
        };

        Cursor cursor = db.query(
                TABLE_STUDENTS,
                projection,
                null,
                null,
                null,
                null,
                null
        );

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(STUDENT_ID));
                String name = cursor.getString(cursor.getColumnIndexOrThrow(STUDENT_NAME));
                String course = cursor.getString(cursor.getColumnIndexOrThrow(COURSE_NAME));
                String priority = cursor.getString(cursor.getColumnIndexOrThrow(PRIORITY_LEVEL));

                studentList.add(new Student(id, name, course, priority)); // Ensure your Student class has this constructor
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return studentList;
    }

    public Student getStudent(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Student student = null;

        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                STUDENT_ID,
                STUDENT_NAME,
                COURSE_NAME,
                PRIORITY_LEVEL
        };

        // Filter results WHERE "id" = 'id'
        String selection = STUDENT_ID + " = ?";
        String[] selectionArgs = { String.valueOf(id) };

        Cursor cursor = db.query(
                TABLE_STUDENTS,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        if (cursor.moveToFirst()) {
            // Extract the student details
            int studentId = cursor.getInt(cursor.getColumnIndexOrThrow(STUDENT_ID));
            String name = cursor.getString(cursor.getColumnIndexOrThrow(STUDENT_NAME));
            String course = cursor.getString(cursor.getColumnIndexOrThrow(COURSE_NAME));
            String priority = cursor.getString(cursor.getColumnIndexOrThrow(PRIORITY_LEVEL));

            // Create a new Student object using the data from the cursor
            student = new Student(studentId, name, course, priority);
        }

        // Close the cursor and database to release resources
        cursor.close();
        db.close();

        return student;
    }


    public boolean updateStudent(Student student) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        // Put the new values for the student record
        values.put(STUDENT_NAME, student.getName());
        values.put(COURSE_NAME, student.getCourse());
        values.put(PRIORITY_LEVEL, student.getPriority());

        // Updating row
        int rowsAffected = db.update(TABLE_STUDENTS, values, STUDENT_ID + " = ?",
                new String[]{String.valueOf(student.getId())});

        db.close(); // Closing database connection

        // Check if any row was updated
        return rowsAffected > 0;
    }

}
