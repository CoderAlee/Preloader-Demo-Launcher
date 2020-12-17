package org.alee.component.preloader.exception;

/**********************************************************
 *
 * @author: MY.Liu
 * @date: 2020/12/7
 * @description: xxxx
 *
 *********************************************************/
public final class PreloadException extends RuntimeException {

    public PreloadException(String msg) {
        super(msg);
    }

    public PreloadException(Throwable throwable) {
        super(throwable);
    }
}
