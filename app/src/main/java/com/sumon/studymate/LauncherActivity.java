package com.sumon.studymate;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

public class LauncherActivity extends AppCompatActivity {

    private CardView semesterDashboardCardView, courseDashboardCardView, assignmentDashboardCardView,
            classTestDashboardCardView, teacherDashboardCardView, routineDashboardCardView;
    private TextView semesterCountTV, courseCountTV, classTestCountTV, assignmentCountTV, teacherCountTV, routineCountTV;

    //    all managers
    private SemesterManager semesterManager;
    private ArrayList<SemesterModel> allSemester;
    private CourseManager courseManager;
    private ArrayList<CourseModel> allCourses;
    private TeacherManager teacherManager;
    private ArrayList<TeacherModel> teacherList;
    private AssignmentManager assignmentManager;
    private ArrayList<AssignmentModel> assignmentList;
    private ClassTestManager classTestManager;
    private ArrayList<ClassTestModel> classTestList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);
        setTitle("Home");
        AsyncTask task = new ProgressTask(this).execute();
    }


    private void init() {
        semesterDashboardCardView = (CardView) findViewById(R.id.semesterDashboardCardView);
        courseDashboardCardView = (CardView) findViewById(R.id.courseDashboardCardView);
        assignmentDashboardCardView = (CardView) findViewById(R.id.assignmentDashboardCardView);
        classTestDashboardCardView = (CardView) findViewById(R.id.classTestDashboardCardView);
        teacherDashboardCardView = (CardView) findViewById(R.id.teacherDashboardCardView);
        routineDashboardCardView = (CardView) findViewById(R.id.routineDashboardCardView);
        semesterCountTV = (TextView) findViewById(R.id.semesterCountTV);
        courseCountTV = (TextView) findViewById(R.id.courseCountTV);
        classTestCountTV = (TextView) findViewById(R.id.classTestCountTV);
        assignmentCountTV = (TextView) findViewById(R.id.assignmentCountTV);
        teacherCountTV = (TextView) findViewById(R.id.teacherCountTV);
        routineCountTV = (TextView) findViewById(R.id.routineCountTV);
        initManagers();

        semesterDashboardCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToSemesterList();
            }
        });
        courseDashboardCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToCoursesList();
            }
        });

        assignmentDashboardCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToAssignmentList();
            }
        });
        classTestDashboardCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToClassTestList();
            }
        });

        teacherDashboardCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToTeacherList();
            }
        });

        routineDashboardCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToRoutine();
            }
        });
    }

    private void initManagers() {

        semesterManager = new SemesterManager(this);
        courseManager = new CourseManager(this);
        teacherManager = new TeacherManager(this);
        assignmentManager = new AssignmentManager(this);
        classTestManager = new ClassTestManager(this);

        //fill arraylist
        allSemester = new ArrayList<>();
        allSemester = semesterManager.getAllSemester();

        allCourses = new ArrayList<>();
        allCourses = courseManager.getAllCourses();

        teacherList = new ArrayList<>();
        teacherList = teacherManager.getAllTeacher();

        assignmentList = new ArrayList<>();
        assignmentList = assignmentManager.getAllAssignment();

        classTestList = new ArrayList<>();
        classTestList = classTestManager.getAllClassTest();


        semesterCountTV.setText(String.valueOf(allSemester.size()));
        courseCountTV.setText(String.valueOf(allCourses.size()));
        teacherCountTV.setText(String.valueOf(teacherList.size()));
        assignmentCountTV.setText(String.valueOf(assignmentList.size()));
        classTestCountTV.setText(String.valueOf(classTestList.size()));
        if (new RoutineManager(this).getAllRoutine().size() > 0) {
            routineCountTV.setText("Available");
            routineCountTV.setTextColor(Color.BLACK);
        } else {
            routineCountTV.setText("Not Available");
            routineCountTV.setTextColor(Color.RED);

        }
    }

    private void goToRoutine() {
        startActivity(new Intent(LauncherActivity.this, RoutineWizerdActivity.class));
    }

    private void goToTeacherList() {
        startActivity(new Intent(LauncherActivity.this, TeacherListActivity.class));

    }

    private void goToClassTestList() {
        startActivity(new Intent(LauncherActivity.this, ClassTestListActivity.class));


    }

    private void goToAssignmentList() {
        startActivity(new Intent(LauncherActivity.this, AssignmentListActivity.class));

    }

    private void goToCoursesList() {
        startActivity(new Intent(LauncherActivity.this, CourseListActivity.class));

    }

    private void goToSemesterList() {
        startActivity(new Intent(LauncherActivity.this, SemesterListActivity.class));
    }

    public class ProgressTask extends AsyncTask<String, Void, Boolean> {

        public ProgressTask(LauncherActivity activity) {
            this.activity = activity;
            dialog = new ProgressDialog(LauncherActivity.this);
        }

        /**
         * progress dialog to show user that the backup is processing.
         */
        private ProgressDialog dialog;
        /**
         * application context.
         */
        private LauncherActivity activity;

        protected void onPreExecute() {
            this.dialog.setMessage("Please Wait...");
            this.dialog.show();
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            if (dialog.isShowing()) {
                dialog.dismiss();
            }

        }

        protected Boolean doInBackground(final String... args) {
            init();
            return false;
        }
    }


}
