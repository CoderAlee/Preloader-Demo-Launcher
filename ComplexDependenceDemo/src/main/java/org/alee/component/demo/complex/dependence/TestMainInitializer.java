package org.alee.component.demo.complex.dependence;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

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
class TestMainInitializer implements IInitializer {
    @Override
    public void initOnMainProcess(@NonNull Application context) throws Throwable {
        Log.i("Preload::", "[" + this.getClass().getName() + "] is init On Main Process!");
    }

    @Override
    public void initOnOtherProcess(@NonNull Application context, @NonNull int pid, @NonNull String processName) throws Throwable {
        Log.i("Preload::", this.getClass().getName() + " is init On [ " + processName + " ] Process!");
    }

    @Nullable
    @Override
    public List<Class<? extends IInitializer>> getDependencies(@NonNull int pid, @NonNull String processName) {
        List<Class<? extends IInitializer>> dependencies = new ArrayList<>();
        dependencies.add(TestAInitializer.class);
        dependencies.add(TestDInitializer.class);
        dependencies.add(TestJInitializer.class);
        return dependencies;
    }

    @Nullable
    @Override
    public List<String> getDependencies(@NonNull String processName, @NonNull int pid) {
        return null;
    }
}
