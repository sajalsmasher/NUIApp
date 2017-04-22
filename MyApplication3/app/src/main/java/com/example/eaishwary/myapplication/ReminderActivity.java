package com.example.eaishwary.myapplication;

/**
 * Created by Eaishwary on 16-04-2017.
 */
import java.util.Calendar;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

public class ReminderActivity extends Activity {

    TimePicker myTimePicker;
    Button buttonstartSetDialog, back, h ;
    TextView textAlarmPrompt;

    TimePickerDialog timePickerDialog;

    final static int RQS_1 = 1;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reminder_main);

        textAlarmPrompt = (TextView)findViewById(R.id.alarmprompt);
        back = (Button)findViewById(R.id.button4);
        h = (Button)findViewById(R.id.home);
        buttonstartSetDialog = (Button)findViewById(R.id.startSetDialog);
        buttonstartSetDialog.setOnClickListener(new OnClickListener(){
           @Override
            public void onClick(View v) {
                textAlarmPrompt.setText("");
                openTimePickerDialog(false);

            }});

        back.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent k=new Intent(ReminderActivity.this,TaskActivity.class);
                startActivity(k);
            }
        });

        h.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent k=new Intent(ReminderActivity.this,Welcome.class);
                startActivity(k);
            }
        });
    }


    private void openTimePickerDialog(boolean is24r){
        Calendar calendar = Calendar.getInstance();

        timePickerDialog = new TimePickerDialog(
                ReminderActivity.this,
                onTimeSetListener,
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE),
                is24r);
        timePickerDialog.setTitle("Set Alarm Time");

        timePickerDialog.show();

    }

    OnTimeSetListener onTimeSetListener
            = new OnTimeSetListener(){

        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

            Calendar calNow = Calendar.getInstance();
            Calendar calSet = (Calendar) calNow.clone();

            calSet.set(Calendar.HOUR_OF_DAY, hourOfDay);
            calSet.set(Calendar.MINUTE, minute);
            calSet.set(Calendar.SECOND, 0);
            calSet.set(Calendar.MILLISECOND, 0);

            if(calSet.compareTo(calNow) <= 0){
                //Today Set time passed, count to tomorrow
                calSet.add(Calendar.DATE, 1);
            }

            setAlarm(calSet);
        }};

    private void setAlarm(Calendar targetCal){

        textAlarmPrompt.setText(
                "\n\n***\n"
                        + "Alarm is set@ " + targetCal.getTime() + "\n"
                        + "***\n");

        Intent intent = new Intent(getBaseContext(), Task.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getBaseContext(), RQS_1, intent, 0);
        AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, targetCal.getTimeInMillis(), pendingIntent);

    }

}