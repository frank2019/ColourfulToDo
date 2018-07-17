/*
 * Copyright (C) 2015 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.colour.time.todo.tomatotasks;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.colour.time.todo.R;
import com.colour.time.todo.data.source.TasksRepository;
import com.colour.time.todo.data.source.local.TasksLocalDataSource;
import com.colour.time.todo.data.source.local.ToDoDatabase;
import com.colour.time.todo.data.source.remote.TasksRemoteAirDataSource;
import com.colour.time.todo.statistics.StatisticsActivity;
import com.colour.time.todo.tasks.TasksFilterType;
import com.colour.time.todo.tasks.TasksFragment;
import com.colour.time.todo.tasks.TasksPresenter;
import com.colour.time.todo.tomatoclockdetail.TomatoClockDetailActivity;
import com.colour.time.todo.util.ActivityUtils;
import com.colour.time.todo.util.AppExecutors;
import com.colour.time.todo.util.TickEvent;
import com.colour.time.todo.util.TomatoClock;
import com.colour.time.todo.util.TomatoEvent;

import static com.google.common.base.Preconditions.checkNotNull;

public class TomatoTasksActivity extends AppCompatActivity implements BottomNavigationBar.OnTabSelectedListener{
    final private String TAG = "TomatoTasksActivity";

    private static final String CURRENT_FILTERING_KEY = "CURRENT_FILTERING_KEY";

    private DrawerLayout mDrawerLayout;
    private TasksPresenter mTasksPresenter;

    private BottomNavigationBar bottomNavigationBar;
    int lastSelectedPosition = 0;

    private MyFragment mMyFragment;
    private StartFragment mStartFragment;
    //private HomeFragment mHomeFragment;

    private TasksFragment tasksFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tomoto_tasks_act);

        // Set up the toolbar.
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //toolbar.setTitle("00:00");
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        ab.setHomeAsUpIndicator(R.drawable.ic_menu);
        ab.setDisplayHomeAsUpEnabled(true);



        TomatoClock.getInstance(getApplicationContext()).setToolbar(toolbar);
        TomatoClock.getInstance(getApplicationContext()).setmTickEvent(new TickEvent() {
            @Override
            public void onWorkingTimeTick(Context context, final Toolbar toolbar, final long t) {

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        long min= t/1000/60;
                        long sec= t/1000%60;
                        //String str = String.format("%04d", youNumber);

                        toolbar.setTitle( String.format("%02d", min) + ":" + String.format("%02d", sec));
                    }
                });
            }

            @Override
            public void onRestTimeTick(Context context, final Toolbar toolbar, final long t) {


                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        toolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                        long min= t/1000/60;
                        long sec= t/1000%60;
                        //String str = String.format("%04d", youNumber);
                        toolbar.setTitle( String.format("%02d", min) + ":" + String.format("%02d", sec));
                    }
                });

            }
        });

        TomatoClock.getInstance(getApplicationContext()).setTomatoEvent(new TomatoEvent() {
            @Override
            public void onWorkTimeDone(Context context) {

            }

            @Override
            public void onRestTimeDone(Context context) {

            }

            @Override
            public void onWorkTimeStart(Context context) {
                Log.e(TAG,"onWorkTimeStart");
                Intent i =  new Intent();
                i.setClass(context,TomatoClockDetailActivity.class);
                context.startActivity(i);
            }


            @Override
            public void onRestTimeStart(Context context) {
                Log.e(TAG,"onRestTimeStart");
                Intent i =  new Intent();
                i.setClass(context,TomatoClockDetailActivity.class);
                context.startActivity(i);
            }
        });


        /**
         * bottomNavigation 设置
         */
        bottomNavigationBar = (BottomNavigationBar) findViewById(R.id.bottom_navigation_bar);

        /** 导航基础设置 包括按钮选中效果 导航栏背景色等 */
        bottomNavigationBar
                .setTabSelectedListener(this)
                .setMode(BottomNavigationBar.MODE_FIXED)
                /**
                 *  setMode() 内的参数有三种模式类型：
                 *  MODE_DEFAULT 自动模式：导航栏Item的个数<=3 用 MODE_FIXED 模式，否则用 MODE_SHIFTING 模式
                 *  MODE_FIXED 固定模式：未选中的Item显示文字，无切换动画效果。
                 *  MODE_SHIFTING 切换模式：未选中的Item不显示文字，选中的显示文字，有切换动画效果。
                 */

                .setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC)
                /**
                 *  setBackgroundStyle() 内的参数有三种样式
                 *  BACKGROUND_STYLE_DEFAULT: 默认样式 如果设置的Mode为MODE_FIXED，将使用BACKGROUND_STYLE_STATIC
                 *                                    如果Mode为MODE_SHIFTING将使用BACKGROUND_STYLE_RIPPLE。
                 *  BACKGROUND_STYLE_STATIC: 静态样式 点击无波纹效果
                 *  BACKGROUND_STYLE_RIPPLE: 波纹样式 点击有波纹效果
                 */

                .setActiveColor("#FF107FFD") //选中颜色
                .setInActiveColor("#e9e6e6") //未选中颜色
                .setBarBackgroundColor("#FFF5EE");//导航栏背景色

        BottomNavigationItem bottomNavigationItem =new BottomNavigationItem(R.drawable.ic_shortcuts_pomo_start, "START");

        /** 添加导航按钮 */
        bottomNavigationBar
                .addItem(new BottomNavigationItem(R.drawable.ic_home, "首页"))
                .addItem(bottomNavigationItem)
                .addItem(new BottomNavigationItem(R.drawable.ic_my, "个人设置"))
                .setFirstSelectedPosition(lastSelectedPosition )
                .initialise(); //initialise 一定要放在 所有设置的最后一项

        bottomNavigationItem.setActiveColor(R.color.cardview_shadow_end_color);



        //setDefaultFragment();//设置默认导航栏




        // Set up the navigation drawer.
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerLayout.setStatusBarBackground(R.color.colorPrimaryDark);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        if (navigationView != null) {
            setupDrawerContent(navigationView);
        }

        tasksFragment =
                (TasksFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);
        if (tasksFragment == null) {
            // Create the fragment
            tasksFragment = TasksFragment.newInstance();
            ActivityUtils.addFragmentToActivity(
                    getSupportFragmentManager(), tasksFragment, R.id.contentFrame);
        }

        /**
         * 测试代码
         */
        //TODO   测试代码
       // TestUseCase.getInstance().testTasksRepository(getApplicationContext());
        //TestUseCase.getInstance().testRemoteRepository(getApplicationContext());


        // Create the presenter
        /*mTasksPresenter = new TasksPresenter(
                Injection.provideTasksRepository(getApplicationContext()), tasksFragment);*/

        mTasksPresenter = new TasksPresenter(
                this.provideTasksRepository(getApplicationContext()), tasksFragment);

        // Load previously saved state, if available.
        if (savedInstanceState != null) {
            TasksFilterType currentFiltering =
                    (TasksFilterType) savedInstanceState.getSerializable(CURRENT_FILTERING_KEY);
            mTasksPresenter.setFiltering(currentFiltering);
        }
    }


    /**
     * 设置默认导航栏
     */
    private void setDefaultFragment() {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
       // mHomeFragment = HomeFragment.newInstance("首页");
        transaction.replace(R.id.tb, tasksFragment);
        transaction.commit();
    }


    /**
     * 设置导航选中的事件
     */
    @Override
    public void onTabSelected(int position) {
        Log.e(TAG, "onTabSelected() called with: " + "position = [" + position + "]");
        //FragmentManager fm = this.getFragmentManager();
        FragmentManager fm = getSupportFragmentManager();
        //开启事务
        FragmentTransaction transaction = fm.beginTransaction();
        switch (position) {
            case 0:
                if (tasksFragment == null) {
                    tasksFragment = TasksFragment.newInstance();
                }
                transaction.replace(R.id.contentFrame, tasksFragment);
                break;
            case 1:
                TomatoClock.getInstance(getApplicationContext()).click();
                /*if (mStartFragment == null) {
                    mStartFragment = StartFragment.newInstance("扫一扫");
                }
                transaction.replace(R.id.contentFrame, mStartFragment);*/
                break;
            case 2:
                if (mMyFragment == null) {
                    mMyFragment = MyFragment.newInstance("个人中心");
                }
                transaction.replace(R.id.contentFrame, mMyFragment);
                break;

            default:
                break;
        }

        transaction.commit();// 事务提交
    }

    /**
     * 设置未选中Fragment 事务
     */
    @Override
    public void onTabUnselected(int position) {

    }

    /**
     * 设置释放Fragment 事务
     */
    @Override
    public void onTabReselected(int position) {
        switch (position) {
            case 1:
                TomatoClock.getInstance(getApplicationContext()).click();
                break;
            default:
                break;
        }
    }



    public static TasksRepository provideTasksRepository(@NonNull Context context) {
        checkNotNull(context);
        ToDoDatabase database = ToDoDatabase.getInstance(context);
        return TasksRepository.getInstance(TasksRemoteAirDataSource.getInstance(),
                TasksLocalDataSource.getInstance(new AppExecutors(),
                        database.taskDao()));
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putSerializable(CURRENT_FILTERING_KEY, mTasksPresenter.getFiltering());
        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // Open the navigation drawer when the home icon is selected from the toolbar.
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        switch (menuItem.getItemId()) {
                            case R.id.list_navigation_menu_item:
                                // Do nothing, we're already on that screen
                                break;
                            case R.id.statistics_navigation_menu_item:
                                Intent intent =
                                        new Intent(TomatoTasksActivity.this, StatisticsActivity.class);
                                startActivity(intent);
                                break;
                            default:
                                break;
                        }
                        // Close the navigation drawer when an item is selected.
                        menuItem.setChecked(true);
                        mDrawerLayout.closeDrawers();
                        return true;
                    }
                });
    }

   /* @VisibleForTesting
    public IdlingResource getCountingIdlingResource() {
        return EspressoIdlingResource.getIdlingResource();
    }*/



}
