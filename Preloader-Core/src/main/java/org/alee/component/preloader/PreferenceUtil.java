package org.alee.component.preloader;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashSet;
import java.util.Set;

/**********************************************************
 *
 * @author: MY.Liu
 * @date: 2020/12/7
 * @description: xxxx
 *
 *********************************************************/
final class PreferenceUtil {

    /**
     * 配置存储
     */
    private static SharedPreferences sPreference;
    /**
     * 编辑器
     */
    private static SharedPreferences.Editor sEditor;

    private PreferenceUtil() {

    }

    /**
     * 获取单例对象
     *
     * @return {@link PreferenceUtil}
     */
    static PreferenceUtil getInstance() {
        return PreferenceUtilHolder.INSTANCE;
    }

    void init(Context context) {
        sPreference = context.getSharedPreferences(Constant.SP_KEY_CACHE, Context.MODE_PRIVATE);
        sEditor = sPreference.edit();
    }

    /**
     * 保存String类型
     *
     * @param key   键
     * @param value 键
     */
    void putString(String key, String value) {
        sEditor.putString(key, value);
        sEditor.commit();
    }

    /**
     * 获取String类型值
     *
     * @param key 键
     * @return 值
     */
    String getString(String key) {
        return sPreference.getString(key, "");
    }

    /**
     * 保存整数类型数据
     *
     * @param key   键
     * @param value 值
     */
    void putInt(String key, int value) {
        sEditor.putInt(key, value);
        sEditor.commit();
    }

    /**
     * 获取整数类型数据
     *
     * @param key 键
     * @return 值
     */
    int getInt(String key) {
        return sPreference.getInt(key, 0);
    }

    /**
     * 获取整数类型数据
     *
     * @param key 键
     * @return 值
     */
    int getInt(String key, int defValue) {
        return sPreference.getInt(key, defValue);
    }

    void putStringSet(String key, Set<String> set) {
        sEditor.putStringSet(key, set).commit();
    }

    Set<String> getStringSet(String key) {
        return sPreference.getStringSet(key, new HashSet<String>());
    }

    /**
     * 静态内部类持有外部对象实现单利方式
     */
    private static class PreferenceUtilHolder {
        private static PreferenceUtil INSTANCE = new PreferenceUtil();
    }
}
