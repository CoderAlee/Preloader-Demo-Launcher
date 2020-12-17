package org.alee.component.preloader.aop;

import org.alee.component.preloader.Preloader;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

/**********************************************************
 *
 * @author: MY.Liu
 * @date: 2020/12/7
 * @description: 切点定义类
 *
 *********************************************************/
@Aspect
public final class HookPreloading {

    /**
     * 类名- {@link org.alee.component.preloader.annotation.Preloading}
     */
    private static final String CLASS_NAME_PRELOADING = "org.alee.component.preloader.annotation.Preloading";

    /**
     * 方法名
     */
    private static final String FUNCTION_NAME_HOOK_PRELOADING = "hookPreloading()";


    @Pointcut(value = "@annotation(" + CLASS_NAME_PRELOADING + ")")
    public void hookPreloading() {

    }

    @Before(FUNCTION_NAME_HOOK_PRELOADING)
    public void onCreateProcessor(JoinPoint joinPoint) throws Throwable {
        Class target = null == joinPoint.getTarget() ? joinPoint.getSignature().getDeclaringType() : joinPoint.getTarget().getClass();
        Preloader.getInstance().prelpading(target, joinPoint.getSignature().getName());
    }


}
