package org.alee.component.demo.process.a;

import org.alee.component.preloader.Process;
import org.alee.component.preloader.annotation.Preloading;

/**********************************************************
 *
 * @author: MY.Liu
 * @date: 2020/12/15
 * @description: xxxx
 *
 *********************************************************/
public class AProcessTestUtil {
    public static boolean sInit;

    @Preloading(value = AProcessInitializer.class)
    public  static String getContent() {
        return sInit ? "调用成功！" : "还未初始化";
    }

    static void init() {
        sInit = true;
    }

}
