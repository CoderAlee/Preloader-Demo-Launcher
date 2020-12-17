package org.alee.component.compiler.util;

import java.util.Collection;

/**********************************************************
 *
 * @author: MY.Liu
 * @date: 2020/12/7
 * @description: xxxx
 *
 *********************************************************/
public final class CollectionUtils {

    public static boolean isNotEmpty(Collection<?> coll) {
        return !isEmpty(coll);
    }

    public static boolean isEmpty(Collection<?> coll) {
        return coll == null || coll.isEmpty();
    }
}
