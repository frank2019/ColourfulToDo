package com.colour.time.todo.util;

import android.content.Context;
import android.support.v7.widget.Toolbar;

/**
 * Created by mx on 2018/7/16.
 */

public interface TickEvent {
    void onWorkingTimeTick(Context context, Toolbar toolbar,long t);
    void onRestTimeTick(Context context, Toolbar toolbar,long t);
}
