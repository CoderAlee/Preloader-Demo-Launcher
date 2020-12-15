package org.alee.component.demo.component.a;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.alibaba.android.arouter.launcher.ARouter;

import org.alee.component.preloader.template.IInitializer;

import java.util.ArrayList;
import java.util.List;

/**********************************************************
 *
 * @author: MY.Liu
 * @date: 2020/12/15
 * @description: xxxx
 *
 *********************************************************/
public class ComponentAInitializer implements IInitializer {

    @Override
    public void initOnMainProcess(@NonNull Application context) throws Throwable {
        Log.i("Preload::", "[" + this.getClass().getName() + "] is init On Main Process!");
        ARouter.init(context);
    }

    @Override
    public void initOnOtherProcess(@NonNull Application context, @NonNull int pid, @NonNull String processName) throws Throwable {
        Log.i("Preload::", this.getClass().getName() + " is init On [ " + processName + " ] Process!");
    }

    @Nullable
    @Override
    public List<Class<? extends IInitializer>> getDependencies(@NonNull int pid, @NonNull String processName) {
        return null;
    }

    @Nullable
    @Override
    public List<String> getDependencies(@NonNull String processName, @NonNull int pid) {
        // 用于模拟组件A 的初始化依赖于组件B
        List<String> dependencies = new ArrayList<>();
        dependencies.add("/component/b/test1");
        dependencies.add("/component/b/test2");
        return dependencies;
    }
}
