package org.alee.component.preloader;

import android.util.Log;

import org.alee.component.preloader.template.ILogger;

/**********************************************************
 *
 * @author: MY.Liu
 * @date: 2020/12/15
 * @description: xxxx
 *
 *********************************************************/
final class DefaultLogger implements ILogger {
    @Override
    public void debug(String tag, String message) {
        Log.d(tag, message);
    }

    @Override
    public void info(String tag, String message) {
        Log.i(tag, message);
    }

    @Override
    public void error(String tag, String message) {
        Log.e(tag, message);
    }
}
