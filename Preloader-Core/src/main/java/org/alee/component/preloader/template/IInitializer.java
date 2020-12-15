package org.alee.component.preloader.template;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

/**********************************************************
 *
 * @author: MY.Liu
 * @date: 2020/12/7
 * @description: 初始化器接口
 *
 *********************************************************/
public interface IInitializer {
    /**
     * 初始化在主进程
     *
     * @param context {@link Context}
     * @throws Throwable 可能會抛出異常
     */
    void initOnMainProcess(@NonNull Application context) throws Throwable;

    /**
     * 初始化在其他进程
     *
     * @param context     {@link Context}
     * @param pid         进程id
     * @param processName 进程名
     * @throws Throwable 可能會抛出異常
     */
    void initOnOtherProcess(@NonNull Application context, @NonNull int pid, @NonNull String processName) throws Throwable;

    /**
     * 获取当前初始化器的依赖关系
     *
     * @param pid         进程id
     * @param processName 进程名
     * @return 依赖关系
     */
    @Nullable
    List<Class<? extends IInitializer>> getDependencies(@NonNull int pid, @NonNull String processName);

    /**
     * 获取当前初始化器的依赖关系
     *
     * @param processName 进程名
     * @param pid         进程id
     * @return 依赖关系的path
     */
    @Nullable
    List<String> getDependencies(@NonNull String processName, @NonNull int pid);
}
