package org.alee.component.preloader.exception;

/**********************************************************
 *
 * @author: MY.Liu
 * @date: 2020/12/7
 * @description: xxxx
 *
 *********************************************************/
public final class ApplicationException extends RuntimeException {

    public ApplicationException() {
        super("Please pass in a valid Application!");
    }
}
