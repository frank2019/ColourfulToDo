package com.colour.time.todo.util;

import android.content.Context;
import android.support.v7.widget.Toolbar;

/**
 * Created by mx on 2018/7/16.
 */

public interface TomatoEvent {
    void onWorkTimeDone(Context context);

    void onRestTimeDone(Context context);
    void onWorkTimeStart(Context context);
    void onRestTimeStart(Context context);


    //void onTick(Context context, Toolbar toolbar);
}
