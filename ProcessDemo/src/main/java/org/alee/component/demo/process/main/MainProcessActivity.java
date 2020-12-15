package org.alee.component.demo.process.main;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Bundle;
import android.os.Process;
import android.widget.TextView;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import org.alee.component.demo.process.R;

import java.util.List;

/**********************************************************
 *
 * @author: MY.Liu
 * @date: 2020/12/14
 * @description: xxxx
 *
 *********************************************************/
public class MainProcessActivity extends AppCompatActivity {
    protected TextView mContentView;
    protected StringBuilder mBuilder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        bindView();
        mBuilder = new StringBuilder();
        resetBuilder();
        showDemo();
    }

    protected @LayoutRes
    int getLayoutId() {
        return R.layout.process_activity_process_main;
    }

    protected void bindView() {
        mContentView = findViewById(R.id.tv_process_content);
    }

    protected void resetBuilder() {
        ActivityManager.RunningAppProcessInfo info = getCurrentProcessInfo(this);
        mBuilder.append("当前进程为：")
                .append(info.processName)
                .append("\r\n")
                .append("进程号：")
                .append(info.pid)
                .append("\r\n");
    }

    protected void showDemo() {
        mBuilder.append("调用结果:").append(TestUtil.getContent());
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
