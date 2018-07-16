package com.colour.time.todo.tomatoclockdetail;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.colour.time.todo.R;

import fr.castorflex.android.circularprogressbar.CircularProgressBar;

public class TomatoClockDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tomatoclock_detail);

        // Set up the toolbar.
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //toolbar.setTitle("00:00");
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        ab.setHomeAsUpIndicator(R.drawable.ic_menu);
        ab.setDisplayHomeAsUpEnabled(true);

        CircularProgressBar mCirprogressBar1 = (CircularProgressBar) findViewById(R.id.circularProgressBar);
       // mCirprogressBar1.setTooltipText("20:00");

    }
}
