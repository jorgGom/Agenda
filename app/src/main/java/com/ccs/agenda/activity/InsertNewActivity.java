package com.ccs.agenda.activity;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.View;

import com.ccs.agenda.R;
import com.ccs.agenda.fragments.DatePickerFragment;
import com.ccs.agenda.fragments.InsertNewFragment;
import com.ccs.agenda.fragments.TimePickerFragment;

public class InsertNewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        if (savedInstanceState == null) {
            InsertNewFragment fragment = new InsertNewFragment();
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(android.R.id.content, fragment, fragment.getClass().getSimpleName())
                    .commit();

        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.insert, menu);
        return true;

    }
    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    public void showTimePickerDialog(View v){
        DialogFragment newTimeFragment = new TimePickerFragment();
        newTimeFragment.show(getSupportFragmentManager(),"timePicker");
    }
}
