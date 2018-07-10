package com.colour.time.todo.test;

import android.content.Context;
import android.util.Log;

import com.colour.time.todo.NetRequest.RemoteApi;
import com.colour.time.todo.NetRequest.vo.BaseResponseVo;
import com.colour.time.todo.NetRequest.vo.DelRequestVo;
import com.colour.time.todo.NetRequest.vo.QueryRequestVo;
import com.colour.time.todo.NetRequest.vo.TaskRemote;
import com.colour.time.todo.NetRequest.vo.TasksRequestVo;
import com.colour.time.todo.NetRequest.vo.TasksResponseVo;
import com.colour.time.todo.data.Task;
import com.colour.time.todo.data.source.TasksDataSource;
import com.colour.time.todo.data.source.TasksRepository;

import java.text.ParseException;
import java.util.List;
import java.util.UUID;

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
     * 测试远程仓库接口API   网络接口API
     * @param context
     */
    public void testRemoteRepository(final Context context){
        new Thread(new Runnable() {
            @Override
            public void run() {
                Long current =  System.currentTimeMillis();

                Long start =0L;
                //yyyy-MM-dd HH:mm:ss
                try {
                    start = RemoteApi.dateToStamp("2018-01-01 00:00:00");
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                Integer user_id = 1;
                String  token = "token";
/*
                Log.e(TAG,"测试API: 查询");
                QueryRequestVo queryRequestVo = new QueryRequestVo(QueryRequestVo.ACTIVE_TASKS,start,current,user_id,token, current);

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

                Log.e(TAG,"测试API: 增加");

                TasksRequestVo tasksRequestVo =  new TasksRequestVo();
                tasksRequestVo.setToken("token");
                tasksRequestVo.setTs(System.currentTimeMillis());

                Task[]  tasks = new  Task[1];
                tasks[0] = new Task(UUID.randomUUID().toString(),"测试Title","这是一个测试项目",false,10,0,0,false,"0,1",System.currentTimeMillis(),1,Task.TAG_Important_Urgent);


                tasksRequestVo.setTasks(tasks);

                RemoteApi.getInstance().addTasks(tasksRequestVo, new Callback<BaseResponseVo>() {
                    @Override
                    public void onResponse(Call<BaseResponseVo> call, Response<BaseResponseVo> response) {
                        Log.e("RemoteApi","addTasks " +  response.body().toString());
                    }

                    @Override
                    public void onFailure(Call<BaseResponseVo> call, Throwable throwable) {
                        Log.e("RemoteApi","addTasks Error " +  throwable.getMessage());
                    }
                });*/

                ////////
               /* Log.e(TAG,"测试API: 更新");

                TasksRequestVo tasksRequestVo2 =  new TasksRequestVo();
                tasksRequestVo2.setToken("token");
                tasksRequestVo2.setTs(System.currentTimeMillis());

                Task[]  tasks2 = new  Task[1];
                tasks2[0] = new Task("7a04c831-95a6-49ed-b1cb-92b1c800df67","new Title","new 这是一个测试项目",false,10,0,0,false,"0,1",System.currentTimeMillis(),1,Task.TAG_Important_Urgent);


                tasksRequestVo2.setTasks(tasks2);

                RemoteApi.getInstance().updateTasks(tasksRequestVo2, new Callback<BaseResponseVo>() {
                    @Override
                    public void onResponse(Call<BaseResponseVo> call, Response<BaseResponseVo> response) {
                        Log.e("RemoteApi","updateTasks " +  response.body().toString());
                    }

                    @Override
                    public void onFailure(Call<BaseResponseVo> call, Throwable throwable) {
                        Log.e("RemoteApi","updateTasks Error " +  throwable.getMessage());
                    }
                });
*/

                Log.e(TAG,"测试API: 删除");

                DelRequestVo delRequestVo = new DelRequestVo();
                delRequestVo.setToken("token");
                delRequestVo.setTs(System.currentTimeMillis());

                String [] ids =  new  String[2];
                ids[0] =  "3";
                ids[1] = "2";
                delRequestVo.setTsaks_id(ids);


                RemoteApi.getInstance().delTasks(delRequestVo, new Callback<BaseResponseVo>() {
                    @Override
                    public void onResponse(Call<BaseResponseVo> call, Response<BaseResponseVo> response) {
                        Log.e("RemoteApi","DelRequestVo " +  response.body().toString());
                    }

                    @Override
                    public void onFailure(Call<BaseResponseVo> call, Throwable throwable) {
                        Log.e("RemoteApi","DelRequestVo Error " +  throwable.getMessage());
                    }
                });

            }
        }).start();
    }


}
