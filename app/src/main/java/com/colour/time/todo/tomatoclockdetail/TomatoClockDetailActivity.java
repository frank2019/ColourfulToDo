package com.colour.time.todo.tomatoclockdetail;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.colour.time.todo.R;

import fr.castorflex.android.circularprogressbar.CircularProgressBar;


public class TomatoClockDetailActivity extends AppCompatActivity {

    private String[] data = { "今日 7月17日 星期二     2", "单例模式", "番茄时钟首页" };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tomatoclock_detail);

        // Set up the toolbar.
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //toolbar.setTitle("00:00");
        //toolbar.setNavigationIcon(R.drawable.white_back);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        ab.setHomeAsUpIndicator(R.drawable.ic_menu);
        ab.setDisplayHomeAsUpEnabled(true);



        TextView mTextView = (TextView) findViewById(R.id.mTextView);
        mTextView.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        CircularProgressBar mCirprogressBar1 = (CircularProgressBar) findViewById(R.id.circularProgressBar);

        if(savedInstanceState != null){
            String[] data2 =  savedInstanceState.getStringArray("TaskDone");
            if(data2 != null){
                data = data2;
            }
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                TomatoClockDetailActivity.this, android.R.layout.simple_list_item_1, data);

        ListView listView = (ListView) findViewById(R.id.mListView);
        listView.setAdapter(adapter);


    }
}
