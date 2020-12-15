package org.alee.component.demo.process.main;

import org.alee.component.preloader.Process;
import org.alee.component.preloader.annotation.Preloading;

/**********************************************************
 *
 * @author: MY.Liu
 * @date: 2020/12/14
 * @description: xxxx
 *
 *********************************************************/
public class TestUtil {
    public static boolean sInit;

    @Preloading(value = TestUtilInitializer.class,ProcessType = Process.MAIN_PROCESS)
    public  static String getContent() {
        return sInit ? "调用成功！" : "还未初始化";
    }

    static void init() {
        sInit = true;
    }

}
