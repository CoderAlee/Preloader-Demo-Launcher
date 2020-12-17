package org.alee.component.preloader.template;

import java.util.Map;

/**********************************************************
 *
 * @author: MY.Liu
 * @date: 2020/12/7
 * @description: xxxx
 *
 *********************************************************/
public interface IInitializerGroup {

    void loadInto(Map<String, Class<? extends IInitializer>> classMap);
}
