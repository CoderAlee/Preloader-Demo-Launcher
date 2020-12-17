package org.alee.component.preloader.compiler;

import java.util.Objects;

import javax.lang.model.type.TypeMirror;

/**********************************************************
 *
 * @author: MY.Liu
 * @date: 2020/12/7
 * @description: xxxx
 *
 *********************************************************/
class BeanMarkInfo implements Comparable<BeanMarkInfo> {
    /**
     * 组别
     */
    private String mGroup;
    /**
     * 唯一路径
     */
    private String mPath;
    /**
     * 类型
     */
    private TypeMirror mTypeMirror;

    @Override
    public int hashCode() {
        return Objects.hash(getGroup(), getPath(), getTypeMirror());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        BeanMarkInfo info = (BeanMarkInfo) o;
        return getGroup().equals(info.getGroup()) &&
                getPath().equals(info.getPath()) &&
                getTypeMirror().equals(info.getTypeMirror());
    }

    public String getGroup() {
        return null == mGroup ? "" : mGroup;
    }

    public void setGroup(String group) {
        mGroup = group;
    }

    public String getPath() {
        return null == mPath ? "" : mPath;
    }

    public void setPath(String path) {
        mPath = path;
    }

    public TypeMirror getTypeMirror() {
        return mTypeMirror;
    }

    public void setTypeMirror(TypeMirror typeMirror) {
        mTypeMirror = typeMirror;
    }

    @Override
    public int compareTo(BeanMarkInfo beanMarkInfo) {
        return this.getPath().hashCode() - beanMarkInfo.getPath().hashCode();
    }
}
