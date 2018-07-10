package com.colour.time.todo.test;

import android.content.Context;
import android.util.Log;

import com.colour.time.todo.NetRequest.RemoteApi;
import com.colour.time.todo.NetRequest.vo.BaseResponseVo;
import com.colour.time.todo.NetRequest.vo.QueryRequestVo;
import com.colour.time.todo.NetRequest.vo.TaskRemote;
import com.colour.time.todo.NetRequest.vo.TasksRequestVo;
import com.colour.time.todo.NetRequest.vo.TasksResponseVo;
import com.colour.time.todo.data.Task;
import com.colour.time.todo.data.source.TasksDataSource;
import com.colour.time.todo.data.source.TasksRepository;

import java.text.ParseException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.colour.time.todo.tasks.TasksActivity.provideTasksRepository;

/**
 * Created by mx on 2018/7/9.
 */

public class TestUseCase {

    private final String TAG="TestUseCase";

    private static class SingleHolder{
        public static TestUseCase instance = new TestUseCase();
    }
    public static TestUseCase getInstance(){
        return TestUseCase.SingleHolder.instance;
    }


    /**
     * 测试仓库的总接口
     * @param context
     */
    public void testTasksRepository(final Context context){

        new  Thread(new Runnable() {
            @Override
            public void run() {
                TasksRepository tasksRepository = provideTasksRepository(context);

                Log.e(TAG,"TestTasksRepository:  -->");
                Task task =  new Task("测试新建任务","bb这是一个新建的测试");

                Log.e(TAG,"add  task:" + task.toString());
                tasksRepository.saveTask(task);

                Log.e(TAG,"get task:\n");
                tasksRepository.getTasks(new TasksDataSource.LoadTasksCallback() {
                    @Override
                    public void onTasksLoaded(List<Task> tasks) {
                        Log.e(TAG,"onTasksLoaded " + tasks.size());
                        for(Task task : tasks){
                            Log.e(TAG,task.toString());
                        }
                    }
                    @Override
                    public void onDataNotAvailable() {
                        Log.e(TAG,"onDataNotAvailable ");
                    }
                });

                Log.e(TAG,"----->end\n");


                //tasksRepository.saveTask();
            }
        }).start();

    }

    /**
     * 测试远程仓库接口API
     * @param context
     */
    public void testRemoteRepository(final Context context){
        new Thread(new Runnable() {
            @Override
            public void run() {
                Long i =  System.currentTimeMillis();

                Long start =0L;
                //yyyy-MM-dd HH:mm:ss
                try {
                    start = RemoteApi.dateToStamp("2018-01-01 00:00:00");
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                QueryRequestVo queryRequestVo = new QueryRequestVo(QueryRequestVo.ACTIVE_TASKS,start,i,1,"", i);

                RemoteApi.getInstance().getTasks(queryRequestVo, new Callback<TasksResponseVo>() {
                    @Override
                    public void onResponse(Call<TasksResponseVo> call, Response<TasksResponseVo> response) {
                        Log.e("RemoteApi","" +  response.body().toString());
                    }

                    @Override
                    public void onFailure(Call<TasksResponseVo> call, Throwable throwable) {
                        Log.e("RemoteApi","Error " +  throwable.getMessage());
                    }
                });

                TasksRequestVo tasksRequestVo =  new TasksRequestVo();
                tasksRequestVo.setToken("token");
                tasksRequestVo.setTs(System.currentTimeMillis());

                TaskRemote[] taskRemotes =  new TaskRemote[1];
                TaskRemote taskRemote = new TaskRemote();
                taskRemote.setCompleted(false);
                taskRemote.setCreate_time(System.currentTimeMillis());
                taskRemote.setTitle("测试项目");
                taskRemote.setDescription("这是一个测试类的项目");
                taskRemote.setFather_id(0);
                taskRemote.setUser_id(1);
                taskRemote.setTicks_consume(0);
                taskRemote.setTicks_expect(5);
                taskRemote.setSelected(false);
                int[] sub_ids =  new  int[2];
                sub_ids[0]=1;
                sub_ids[1] =4;
                taskRemote.setSub_ids(sub_ids);
                taskRemotes[0] = taskRemote;

                tasksRequestVo.setTasks(taskRemotes);


                RemoteApi.getInstance().addTasks(tasksRequestVo, new Callback<BaseResponseVo>() {
                    @Override
                    public void onResponse(Call<BaseResponseVo> call, Response<BaseResponseVo> response) {
                        Log.e("RemoteApi","addTasks " +  response.body().toString());
                    }

                    @Override
                    public void onFailure(Call<BaseResponseVo> call, Throwable throwable) {
                        Log.e("RemoteApi","addTasks Error " +  throwable.getMessage());
                    }
                });


                //RemoteApi.getInstance().queryTasks(queryRequestVo);
            }
        });
    }


}
