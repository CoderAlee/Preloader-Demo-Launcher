package org.alee.component.preloader;

import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**********************************************************
 *
 * @author: MY.Liu
 * @date: 2020/12/7
 * @description: xxxx
 *
 *********************************************************/
public final class Process {
    /**
     * 所有进程
     */
    public static final int ALL_PROCESS = 0xff >> 1;
    /**
     * 主进程
     */
    public static final int MAIN_PROCESS = ALL_PROCESS >> 2;
    /**
     * 指定进程
     */
    public static final int SPECIFY_PROCESS = MAIN_PROCESS >> 2;

    @IntDef({ALL_PROCESS, MAIN_PROCESS, SPECIFY_PROCESS})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Type {
    }

}
