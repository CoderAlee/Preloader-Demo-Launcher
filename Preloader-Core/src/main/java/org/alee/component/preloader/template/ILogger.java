package org.alee.component.preloader.template;

/**********************************************************
 *
 * @author: MY.Liu
 * @date: 2020/12/15
 * @description: xxxx
 *
 *********************************************************/
public interface ILogger {
    void debug(String tag, String message);

    void info(String tag, String message);

    void error(String tag, String message);
}
