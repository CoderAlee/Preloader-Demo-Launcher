package org.alee.component.preloader;

import android.app.Application;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;

import org.alee.component.preloader.annotation.Preloading;
import org.alee.component.preloader.exception.ApplicationException;
import org.alee.component.preloader.exception.PreloadException;
import org.alee.component.preloader.template.IInitializer;
import org.alee.component.preloader.template.ILogger;

import java.lang.reflect.Method;

/**********************************************************
 *
 * @author: MY.Liu
 * @date: 2020/12/7
 * @description: xxxx
 *
 *********************************************************/
public final class Preloader {
    private _Preloader mHonestMan;


    private Preloader() {
        mHonestMan = new _Preloader();
    }

    /**
     * 获取单例对象
     *
     * @return {@link Preloader}
     */
    public static Preloader getInstance() {
        return PreloaderHolder.INSTANCE;
    }

    public static Config createConfig() {
        return new Config();
    }

    public void init(@NonNull Application context) {
        Launcher.getInstance().init(context);
    }

    @RestrictTo(RestrictTo.Scope.LIBRARY)
    public void prelpading(@NonNull Class<?> cla, @NonNull String methodName) {
        Preloading preloading = getAnnotation(cla, methodName);
        if (null == preloading) {
            throw new PreloadException("Unable to find the [ preloading ] annotation successfully!");
        }
        this.prelpading(Launcher.getInstance().getApplication(), preloading.value(), preloading.ProcessType(), preloading.ProcessName());
    }

    private Preloading getAnnotation(Class<?> cla, String methodName) {
        Preloading target = matchMethod(cla.getDeclaredMethods(), methodName);
        target = null == target ? matchMethod(cla.getMethods(), methodName) : target;
        return target;
    }

    public void prelpading(@NonNull Application application, @NonNull Class<? extends IInitializer> cla, @Process.Type int processType, @Nullable String[] processName) {
        if (null == application) {
            throw new ApplicationException();
        }
        if (null == cla) {
            throw new PreloadException("The original [ Initializer ] cannot be NULL!");
        }
        if (!Launcher.getInstance().isInit()) {
            throw new PreloadException("Please call [ Preloader.getInstance().init(Application application); ] first and pass in a valid Application object");
        }
        long start = System.currentTimeMillis();
        switch (processType) {
            case Process.SPECIFY_PROCESS:
                mHonestMan.startUpOnSpecifyProcess(application, cla, processName);
                break;
            case Process.MAIN_PROCESS:
                mHonestMan.startUpOnMainProcess(application, cla);
                break;
            case Process.ALL_PROCESS:
            default:
                mHonestMan.startUpOnCurrentProcess(application, cla, -1, null);
                break;
        }
        if (Launcher.getInstance().isRunInDebug()) {
            Launcher.getInstance().getLogger().info(Constant.TAG, "初始化共耗时 " + (System.currentTimeMillis() - start) + " 毫秒");
            mHonestMan.printActivated();
        }
    }

    private @Nullable
    Preloading matchMethod(Method[] methods, String methodName) {
        if (null == methods || 0 >= methods.length) {
            return null;
        }
        for (Method method : methods) {
            if (null == method) {
                continue;
            }
            Preloading preloading = method.getAnnotation(Preloading.class);
            if (null == preloading) {
                continue;
            }
            preloading = TextUtils.isEmpty(methodName) ? preloading : TextUtils.equals(methodName, method.getName()) ? preloading : null;
            if (null == preloading) {
                continue;
            }
            return preloading;
        }
        return null;
    }


    public void prelpading(@NonNull Application application, @NonNull Class<? extends IInitializer> cla) {
        this.prelpading(application, cla, Process.ALL_PROCESS, null);
    }


    public static class Config {

        public Config setRunMode(boolean isDebug) {
            Launcher.getInstance().setRunInDebug(isDebug);
            return this;
        }

        public Config setLogger(@NonNull ILogger logger) {
            Launcher.getInstance().setLogger(logger);
            return this;
        }

    }


    /**
     * 静态内部类持有外部对象实现单利方式
     */
    private static class PreloaderHolder {
        private static Preloader INSTANCE = new Preloader();
    }


}
