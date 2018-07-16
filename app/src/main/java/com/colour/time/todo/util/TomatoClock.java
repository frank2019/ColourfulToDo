package com.colour.time.todo.util;


//TODO  番茄时钟的定义

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import java.util.Timer;
import java.util.TimerTask;

public class TomatoClock {

    private final String TAG = "TomatoClock";

   // public final static int STATE_INIT = 0;
    public final static int STATE_WORKTIME_START = 1;
    public final static int STATE_WORKTIME_DONE = 2;
    public final static int STATE_RESTTIME_START = 3;
    public final static int STATE_RESTTIME_DONE = 0;



    //一个番茄时钟  单位毫秒
    private  int mTOMATO_TIME = 25*60*1000;
    private int  mTOMATO_REST_TIME = 5*60*1000;

    TimerTask mTimerTask =  new TimerTask() {
        @Override
        public void run() {
            tick();
        }
    };

    long mStartTime = 0;
    int mState = STATE_RESTTIME_DONE;

    Context mContext;
    TomatoEvent mTomatoEvent;
    Toolbar mToolbar ;

    Timer mTimer = new Timer();






    private static class SingleHolder{
        public static TomatoClock instance = new TomatoClock();
    }
    public static TomatoClock getInstance(Context context){
        SingleHolder.instance.setContext(context);
        return SingleHolder.instance;
    }

    private  TomatoClock(){
        mTimer.schedule(mTimerTask,1000,1000);//延时1s执行
    }



    public Toolbar getToolbar() {
        return mToolbar;
    }

    public TomatoClock setToolbar(Toolbar mToolbar) {
        this.mToolbar = mToolbar;
        return  this;
    }


    public Context getContext() {
        return mContext;
    }

    public void setContext(Context mContext) {
        this.mContext = mContext;
    }


    public void setTomatoEvent(TomatoEvent tomatoEvent){
        mTomatoEvent = tomatoEvent;
    }

    /**
     *
     */

    /**
     * 按键的点击事件
     */
    public void click(){

        switch(mState){
            case  STATE_RESTTIME_DONE:
                //mTomatoEvent.onWorkTimeDone(mContext);
                mStartTime = System.currentTimeMillis();
                mState = STATE_WORKTIME_START;
                mTomatoEvent.onWorkTimeStart(mContext);
                break;
            case  STATE_WORKTIME_START:
                //已经开启番茄时钟，什么也不需要做
                //mTomatoEvent.onWorkTimeStart(mContext);
                break;
            case  STATE_WORKTIME_DONE:
                //提交任务
                mTomatoEvent.onWorkTimeDone(mContext);
                mStartTime = System.currentTimeMillis();
                mState = STATE_RESTTIME_START;
                break;
            case  STATE_RESTTIME_START:
                break;
        }
    }

    /**
     * 每一秒调用一次
     */
    public void tick(){

        Log.e(TAG,"tick");

        switch(mState){
            case  STATE_RESTTIME_DONE:
                //mTomatoEvent.onWorkTimeDone(mContext);
                break;
            case  STATE_WORKTIME_START:
                //mTomatoEvent.onWorkTimeStart(mContext);

                mTickEvent.onWorkingTimeTick(mContext,mToolbar,getCurrentClock());
                if(getCurrentClock() >= mTOMATO_TIME){
                    mState =  STATE_WORKTIME_DONE;
                    mTomatoEvent.onWorkTimeDone(mContext);
                }
                break;
            case  STATE_WORKTIME_DONE:

                break;
            case  STATE_RESTTIME_START:

                mTickEvent.onRestTimeTick(mContext,mToolbar,getCurrentClock());
                if(getCurrentClock() >= mTOMATO_REST_TIME){
                    mState =  STATE_RESTTIME_DONE;
                    mTomatoEvent.onRestTimeDone(mContext);
                }

                break;
        }
    }

    TickEvent mTickEvent ;

    public TickEvent getmTickEvent() {
        return mTickEvent;
    }

    public void setmTickEvent(TickEvent mTickEvent) {
        this.mTickEvent = mTickEvent;
    }



    /**
     * 删除时钟
     */
    public void delete(){

    }

    /**
     * 获取当前的番茄时钟 时刻
     * @return
     */
    public  long getCurrentClock(){
        long t =  System.currentTimeMillis() - mStartTime;
        if(t >= mTOMATO_TIME){
            return mTOMATO_TIME;
        }
        return  t;
    }

}
