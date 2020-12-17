package org.alee.component.demo.complex.dependence;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Bundle;
import android.os.Process;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import org.alee.component.preloader.Preloader;
import org.alee.component.preloader.annotation.Preloading;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    protected TextView mContentView;
    protected StringBuilder mBuilder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        Preloader.createConfig().setRunMode(true);
        bindView();
        mBuilder = new StringBuilder();
        resetBuilder();
    }

    protected @LayoutRes
    int getLayoutId() {
        return R.layout.complex_dependence_activity_main;
    }

    protected void bindView() {
        mContentView = findViewById(R.id.tv_dependence_content);
        findViewById(R.id.btn_dependence_main).setOnClickListener(new View.OnClickListener() {
            @Preloading(TestMainInitializer.class)
            @Override
            public void onClick(View v) {
                MainActivity.this.showDemo();
            }
        });
    }

    protected void resetBuilder() {
        ActivityManager.RunningAppProcessInfo info = getCurrentProcessInfo(this);
        mBuilder = new StringBuilder();
        mBuilder.append("当前进程为：")
                .append(info.processName)
                .append("\r\n")
                .append("进程号：")
                .append(info.pid)
                .append("\r\n");
    }

    protected void showDemo() {
        resetBuilder();
        mBuilder.append("初始化结果: ").append(Util.isInit());
        mContentView.setText(mBuilder);
    }

    private ActivityManager.RunningAppProcessInfo getCurrentProcessInfo(Context context) {
        android.app.ActivityManager am = (android.app.ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> processesList = am.getRunningAppProcesses();
        if (null == processesList || 0 >= processesList.size()) {
            return null;
        }
        int pid = getCurrentProcessId();
        for (ActivityManager.RunningAppProcessInfo info : processesList) {
            if (null == info) {
                continue;
            }
            if (pid != info.pid) {
                continue;
            }
            return info;
        }
        return null;
    }

    private int getCurrentProcessId() {
        return Process.myPid();
    }
}