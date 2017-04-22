package com.example.eaishwary.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

public class Help extends ActionBarActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.help);
        Button btn;
        String[] textString = {"Create a New Task", "View all Tasks", "List Task Details", "Delete a Task", "Undo"};
        int[] drawableIds = {R.drawable.a1, R.drawable.a2, R.drawable.a3, R.drawable.a4, R.drawable.a5};

        ListView listView1;
        CustomAdapter adapter = new CustomAdapter(this,  textString, drawableIds);


        listView1 = (ListView)findViewById(R.id.list1);
        listView1.setAdapter(adapter);
        btn=(Button)findViewById(R.id.button2);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getApplicationContext(),Welcome.class);
                startActivity(in);

            }
        });
    }
}
