package com.colour.time.todo.util;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * 网络事件缓存容器
 *
 */
public class EventsHolder {
    private static class SingleHolder{
        public static EventsHolder instance = new EventsHolder();
    }
    public static EventsHolder getInstance(){
        return SingleHolder.instance;
    }
    LinkedBlockingQueue linkedBlockingQueue =  new LinkedBlockingQueue();

 /*
    class Event(){
        String cmd;
        Object o;
    }

    public void put(String cmd,Object o){
        linkedBlockingQueue.
    }
*/


}
