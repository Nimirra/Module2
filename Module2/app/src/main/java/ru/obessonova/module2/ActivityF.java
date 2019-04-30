package ru.obessonova.module2;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;

public class ActivityF extends AppCompatActivity {
    
    private RecyclerView mList;
    private List<String> mStackInfo = new ArrayList<>();
    private RecyclerView.Adapter mListAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_f);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        
        for (int i = 0; i < 100; i++) {
            mStackInfo.add(String.valueOf(i));
        }
        mListAdapter = new ListAdapter(this, mStackInfo);
        mLayoutManager = new LinearLayoutManager(this);
        mList = findViewById(R.id.list);
        mList.setLayoutManager(mLayoutManager);
        mList.setAdapter(mListAdapter);
//        getStackInfo();
    }

// Здесь тоже ничего не получилось - во всех способах вывода информации о текущем stack-е экранов приложения,
// которые удавалось нагуглить, устаревшие методы.


   /* ActivityManager m = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
    List<ActivityManager.AppTask> runningTaskInfoList = m.getAppTasks();

    public void getStackInfo() {
        String result = "";
        for (ActivityManager.AppTask task : runningTaskInfoList) {
            ActivityManager.RecentTaskInfo resultInfo = task.getTaskInfo();
            ...
            mStackInfo.add(result);
        }
    }*/
    
}