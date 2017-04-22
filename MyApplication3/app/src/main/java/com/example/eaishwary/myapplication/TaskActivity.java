package com.example.eaishwary.myapplication;


import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.app.DatePickerDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.eaishwary.myapplication.DatabaseHelp;
import com.example.eaishwary.myapplication.NotificationActivity;
import com.example.eaishwary.myapplication.R;


import java.util.Calendar;
import java.util.TimeZone;

public class TaskActivity extends Activity {

    NotificationActivity nt;
    DatabaseHelp db;
    EditText t, d, dl, n;
    Button btn1, btn2, btn3;
    Spinner sp;
    static String[] type = {"Personal", "Work", "Others"};
    DatePickerDialog datePickerDialog;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    //private GoogleApiClient client;

    int mYear,mMonth,mDay;
    int hour, minute;
    long startTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.task_main);
        db = new DatabaseHelp(this);
        btn1 = (Button) findViewById(R.id.button1);
        // btn2=(Button)findViewById(R.id.button2);
        //btn3=(Button)findViewById(R.id.button3);
        t = (EditText) findViewById(R.id.editText1);


        n = (EditText) findViewById(R.id.editText5);
        sp = (Spinner) findViewById(R.id.spinner);


        btn3 = (Button) findViewById(R.id.button3);
        // initiate the date picker and a button
        d = (EditText) findViewById(R.id.editText6);

        // perform click event on edit text
        d.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // calender class's instance and get current date , month and year from calender
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR); // current year
                mMonth = c.get(Calendar.MONTH); // current month
                mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                // date picker dialog
                datePickerDialog = new DatePickerDialog(TaskActivity.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // set day of month , month and year value in the edit text
                                d.setText(dayOfMonth + "/"
                                        + (monthOfYear + 1) + "/" + year);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        dl = (EditText) findViewById(R.id.editText4);
        // perform click event listener on edit text
        dl.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(TaskActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        dl.setText(selectedHour + ":" + selectedMinute);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();

            }
        });

        Calendar calendar = Calendar.getInstance();
        calendar.set(mYear, mMonth, mDay,
                hour, minute, 0);
        startTime = calendar.getTimeInMillis();

        btn3.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent m=new Intent(TaskActivity.this, ReminderActivity.class);
                startActivity(m);
            }
        });

        ArrayAdapter<String> aa = new ArrayAdapter<String>(TaskActivity.this,
                android.R.layout.simple_list_item_1, type);
        sp.setAdapter(aa);
        //String text = sp.getSelectedItem().toString();
        AddData();

        // viewAll();
        //UpdateData();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        //client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    private void addReminderInCalendar(long stime, String name) {
        Calendar cal = Calendar.getInstance();
        Uri EVENTS_URI = Uri.parse(getCalendarUriBase(true) + "events");
        ContentResolver cr = getContentResolver();
        TimeZone timeZone = TimeZone.getDefault();

        /** Inserting an event in calendar. */
        ContentValues values = new ContentValues();
        values.put(CalendarContract.Events.CALENDAR_ID, 1);
        values.put(CalendarContract.Events.TITLE, name);
        //values.put(CalendarContract.Events.EVENT_LOCATION, location);
        //values.put(CalendarContract.Events.DESCRIPTION, desc);
        values.put(CalendarContract.Events.ALL_DAY, 0);
        // event starts at 11 minutes from now
        values.put(CalendarContract.Events.DTSTART, stime);
        // ends 60 minutes from now
        //values.put(CalendarContract.Events.DTEND, etime);
        values.put(CalendarContract.Events.EVENT_TIMEZONE, timeZone.getID());
        values.put(CalendarContract.Events.HAS_ALARM, 1);
        Uri event = cr.insert(EVENTS_URI, values);

        // Display event id.
        Toast.makeText(getApplicationContext(), "Event added " , Toast.LENGTH_LONG).show();

        /** Adding reminder for event added. */
        Uri REMINDERS_URI = Uri.parse(getCalendarUriBase(true) + "reminders");
        values = new ContentValues();
        values.put(CalendarContract.Reminders.EVENT_ID, Long.parseLong(event.getLastPathSegment()));
        values.put(CalendarContract.Reminders.METHOD, CalendarContract.Reminders.METHOD_ALERT);
        values.put(CalendarContract.Reminders.MINUTES, 60);
        cr.insert(REMINDERS_URI, values);
    }

    /** Returns Calendar Base URI, supports both new and old OS. */
    private String getCalendarUriBase(boolean eventUri) {
        Uri calendarURI = null;
        try {
            if (android.os.Build.VERSION.SDK_INT <= 7) {
                calendarURI = (eventUri) ? Uri.parse("content://calendar/") : Uri.parse("content://calendar/calendars");
            } else {
                calendarURI = (eventUri) ? Uri.parse("content://com.android.calendar/") : Uri
                        .parse("content://com.android.calendar/calendars");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return calendarURI.toString();
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void addNotification(final String str, final String str1) {
        final Notification.Builder builder = new Notification.Builder(this);
        builder.setStyle(new Notification.BigTextStyle(builder)
                .bigText(str)
                .setBigContentTitle("New Task Created")
                .setSummaryText(str1))

                .setSmallIcon(android.R.drawable.sym_def_app_icon);

        Intent notificationIntent = new Intent(this,
                NotificationActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(contentIntent);

        // Add as notification
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(0, builder.build());
    }

    public void AddData() {
        btn1.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                boolean isInserted = db.insertData(t.getText().toString(), d.getText().toString(), dl.getText().toString(), sp.getSelectedItem().toString(), n.getText().toString());

                if (isInserted == true) {


                    Toast.makeText(TaskActivity.this, "Data Inserted", Toast.LENGTH_LONG).show();
                    addNotification(n.getText().toString(), t.getText().toString());
                    d.setText("");
                    dl.setText("");
                    n.setText("");
                    t.setText("");
                    Intent i = new Intent(getApplicationContext(), Welcome.class);
                    startActivity(i);

                } else
                    Toast.makeText(TaskActivity.this, "Data Not Inserted", Toast.LENGTH_LONG).show();
            }
        });
    }

//    public void AddReminder() {
//        btn3.setOnClickListener(new OnClickListener() {
//
//            @Override
//            public void onClick(View arg0) {
//                // TODO Auto-generated method stub
//                addReminderInCalendar(startTime, t.getText().toString());
//            }
//        });
//    }

    public void viewAll() {

        btn2.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                Cursor r = db.getAllData();

                if (r.getCount() == 0) {
                    showMsg("Error !!", "Nothing found ...");
                }

                StringBuffer buff = new StringBuffer();
                while (r.moveToNext()) {
                    buff.append("TaskId: " + r.getString(0) + "\n");
                    buff.append("Task Name: " + r.getString(1) + "\n");
                    buff.append("Date: " + r.getString(2) + "\n");
                    buff.append("Time: " + r.getString(3) + "\n\n");
                    buff.append("Type: " + r.getString(4) + "\n\n");
                    buff.append("Notes: " + r.getString(5) + "\n\n");
                }

                showMsg("Data Entered !!", buff.toString());
            }
        });
    }

    public void showMsg(String title, String msg) {
        Builder build = new Builder(this);
        build.setCancelable(true);
        build.setTitle(title);
        build.setMessage(msg);
        build.show();

    }

}
