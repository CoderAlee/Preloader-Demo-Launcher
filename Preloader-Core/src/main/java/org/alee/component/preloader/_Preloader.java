package org.alee.component.preloader;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.os.Process;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.alee.component.preloader.exception.PreloadException;
import org.alee.component.preloader.template.IInitializer;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

/**********************************************************
 *
 * @author: MY.Liu
 * @date: 2020/12/7
 * @description: xxxx
 *
 *********************************************************/
final class _Preloader {

    private volatile List<IInitializer> mWaitStartUp;

    private volatile List<Class<? extends IInitializer>> mActivated;

    _Preloader() {
        mWaitStartUp = new ArrayList<>();
        mActivated = new ArrayList<>();
    }

    void printActivated() {
        StringBuilder builder = new StringBuilder("初始化顺序为：\r\n");
        for (Class cla : mActivated) {
            builder.append("------[" + cla.getName() + "]\r\n");
        }
        Launcher.getInstance().getLogger().info(Constant.TAG, builder.toString());
    }


    synchronized void startUpOnMainProcess(Application application, Class<? extends IInitializer> cla) {
        ActivityManager.RunningAppProcessInfo info = getCurrentProcessInfo(application.getApplicationContext());
        if (null == info) {
            throw new PreloadException("No current process found!");
        }
        if (TextUtils.equals(info.processName, getAppPackageName(application.getApplicationContext()))) {
            startUpOnCurrentProcess(application, cla, getCurrentProcessId(), info.processName);
        }
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

    private String getAppPackageName(Context context) {
        return context.getPackageName();
    }

    synchronized void startUpOnCurrentProcess(Application application, Class<? extends IInitializer> cla, int processId, String processName) {
        if (mActivated.contains(cla)) {
            return;
        }
        long start = System.currentTimeMillis();
        try {
            if (TextUtils.isEmpty(processName)) {
                processName = getCurrentProcessInfo(application.getApplicationContext()).processName;
            }

            IInitializer initializer = getInstance(cla);
            mWaitStartUp.add(initializer);
            startUpDependencies(application, initializer, processId, processName);
            try {
                if (TextUtils.equals(getAppPackageName(application.getApplicationContext()), processName)) {
                    initializer.initOnMainProcess(application);
                } else {
                    initializer.initOnOtherProcess(application, processId, processName);
                }
                mActivated.add(initializer.getClass());
            } catch (Throwable e) {
                e.printStackTrace();
            } finally {
                mWaitStartUp.remove(initializer);
            }
        } catch (Throwable ignore) {
            throw new PreloadException(ignore);
        } finally {
            if (Launcher.getInstance().isRunInDebug()) {
                Launcher.getInstance().getLogger().info(Constant.TAG, "[" + cla.getName() + "] 初始化耗时 " + (System.currentTimeMillis() - start) + " 毫秒");
            }
        }
    }

    private int getCurrentProcessId() {
        return Process.myPid();
    }

    /**
     * 启动依赖关系 此处目前才用的深度优先递归 后期计划修改为广度优先递归
     *
     * @param initializer
     * @param processId
     * @param processName
     */
    private void startUpDependencies(Application application, @Nullable IInitializer initializer, int processId, String processName) {
        List<Class<? extends IInitializer>> classList = initializer.getDependencies(processId, processName);
        if (null != classList && 0 < classList.size()) {
            for (Class<? extends IInitializer> cla : classList) {
                startUpOnCurrentProcess(application, cla, processId, processName);
            }
            return;
        }

        List<String> stringList = initializer.getDependencies(processName, processId);
        if (null == stringList || 0 >= stringList.size()) {
            return;
        }
        classList = new ArrayList<>();
        for (String path : stringList) {
            Class<? extends IInitializer> cla = Launcher.getInstance().searchByPath(path);
            if (null == cla) {
                continue;
            }
            classList.add(cla);
        }
        for (Class<? extends IInitializer> cla : classList) {
            startUpOnCurrentProcess(application, cla, processId, processName);
        }
    }

    synchronized void startUpOnSpecifyProcess(Application application, Class<? extends IInitializer> cla, String... processNames) {
        if (null == processNames || 0 >= processNames.length) {
            throw new PreloadException("When Process.Type is SPECIFY_PROCESS, the process name must be specified");
        }
        ActivityManager.RunningAppProcessInfo info = getCurrentProcessInfo(application.getApplicationContext());
        if (null == info) {
            throw new PreloadException("No current process found!");
        }
        for (String name : processNames) {
            if (TextUtils.isEmpty(name)) {
                continue;
            }
            if (TextUtils.equals(info.processName, name)) {
                startUpOnCurrentProcess(application, cla, getCurrentProcessId(), info.processName);
                return;
            }
        }
    }

    private @NonNull
    IInitializer getInstance(@NonNull Class<? extends IInitializer> cla) throws Throwable {
        Constructor<? extends IInitializer> constructor = cla.getDeclaredConstructor();
        boolean originAccessible = constructor.isAccessible();
        if (!originAccessible) {
            constructor.setAccessible(true);
        }
        IInitializer instance = constructor.newInstance();
        if (!originAccessible) {
            constructor.setAccessible(false);
        }
        return instance;
    }

}
