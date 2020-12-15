package org.alee.component.preloader.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**********************************************************
 *
 * @author: MY.Liu
 * @date: 2020/12/7
 * @description: 用于标记 {@link org.alee.component.preloader.template.IInitializer} 的实现类
 *
 *********************************************************/
@Retention(RetentionPolicy.CLASS)
@Target(ElementType.TYPE)
public @interface InitialMark {
    /**
     * 唯一标识 用于作为每个 {@link org.alee.component.preloader.template.IInitializer}的标记
     *
     * @return
     */
    String Path();

    /**
     * 唯一标识 用于标记每个 {@link org.alee.component.preloader.template.IInitializer} 所属组别
     *
     * @return
     */
    String Group() default "";
}
