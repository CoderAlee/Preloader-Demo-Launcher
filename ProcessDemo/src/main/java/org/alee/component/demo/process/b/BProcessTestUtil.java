package org.alee.component.demo.process.b;

import org.alee.component.preloader.Process;
import org.alee.component.preloader.annotation.Preloading;

/**********************************************************
 *
 * @author: MY.Liu
 * @date: 2020/12/15
 * @description: xxxx
 *
 *********************************************************/
public class BProcessTestUtil {
    private boolean mIsInit;

    private BProcessTestUtil() {

    }

    /**
     * 获取单例对象
     *
     * @return {@link BProcessTestUtil}
     */
    public static BProcessTestUtil getInstance() {
        return BProcessTestUtilHolder.INSTANCE;
    }

    @Preloading(value = BProcessInitializer.class, ProcessType = Process.SPECIFY_PROCESS, ProcessName = "org.alee.component.demo.preloader:B")
    public String getContent() {
        return mIsInit ? "调用成功！" : "还未初始化";
    }

    public void init() {
        mIsInit = true;
    }

    /**
     * 静态内部类持有外部对象实现单利方式
     */
    private static class BProcessTestUtilHolder {
        private static BProcessTestUtil INSTANCE = new BProcessTestUtil();
    }

}
