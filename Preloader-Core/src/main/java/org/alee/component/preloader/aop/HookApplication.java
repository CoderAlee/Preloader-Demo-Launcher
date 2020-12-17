package org.alee.component.preloader.aop;

import android.app.Application;

import org.alee.component.preloader.Preloader;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

/**********************************************************
 *
 * @author: MY.Liu
 * @date: 2020/12/7
 * @description: xxxx
 *
 *********************************************************/
@Aspect
public final class HookApplication {
    /**
     * 类名- Application
     */
    private static final String CLASS_NAME_APPLICATION = "android.app.Application";

    /**
     * 方法名
     */
    private static final String FUNCTION_ON_CREATE = "hookAppOnCreate()";

    @Pointcut("execution(* " + CLASS_NAME_APPLICATION + "+.onCreate())")
    public void hookAppOnCreate() {

    }

    @After(FUNCTION_ON_CREATE)
    public void attachBaseContextProcessor(JoinPoint joinPoint) throws Throwable {
        Object object = joinPoint.getTarget();
        if (!(object instanceof Application)) {
            return;
        }
        Application application = (Application) object;
        Preloader.getInstance().init(application);
    }
}
