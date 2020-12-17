package org.alee.component.preloader.annotation;

import org.alee.component.preloader.Process;
import org.alee.component.preloader.template.IInitializer;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**********************************************************
 *
 * @author: MY.Liu
 * @date: 2020/12/7
 * @description: xxxx
 *
 *********************************************************/
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface Preloading {
    /**
     * 进程类型
     *
     * @return xxx
     */
    @Process.Type
    int ProcessType() default Process.ALL_PROCESS;

    /**
     * 进程名 只有当使用{@link Process#SPECIFY_PROCESS} 时才需要指定
     *
     * @return xxx
     */
    String[] ProcessName() default {};

    /**
     * 传入的class 标识需要启动的源头
     *
     * @return {@link IInitializer}
     */
    Class<? extends IInitializer> value();

}
