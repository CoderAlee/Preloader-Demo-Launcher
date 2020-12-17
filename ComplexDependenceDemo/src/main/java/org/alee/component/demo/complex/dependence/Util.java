package org.alee.component.demo.complex.dependence;

/**********************************************************
 *
 * @author: MY.Liu
 * @date: 2020/12/15
 * @description: xxxx
 *
 *********************************************************/
class Util {

    private static boolean sIsInit;

    static boolean isInit() {
        return sIsInit;
    }

    static void init() {
        sIsInit = true;
    }
}
