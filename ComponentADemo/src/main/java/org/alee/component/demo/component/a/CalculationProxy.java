package org.alee.component.demo.component.a;

import com.alibaba.android.arouter.launcher.ARouter;

import org.alee.component.demo.capability.statement.ICalculation;
import org.alee.component.preloader.annotation.Preloading;

/**********************************************************
 *
 * @author: MY.Liu
 * @date: 2020/12/15
 * @description: xxxx
 *
 *********************************************************/
class CalculationProxy {
    @Preloading(value = ComponentAInitializer.class, ProcessType = org.alee.component.preloader.Process.MAIN_PROCESS)
    static String add() {
        try {
            ICalculation calculation = ARouter.getInstance().navigation(ICalculation.class);
            if (null == calculation) {
                return "调用失败!无法获取组件B实现类";
            }
            return "调用成功，计算5+3=" + calculation.add(5, 3);
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return "调用失败";
    }
}
