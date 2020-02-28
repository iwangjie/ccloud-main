package com.ccloud.main.pojo.system;

/**
 * 用户接口
 *
 * @param <T>
 */
public interface CloudUser<T> {

    /**
     * 获取当前用户对象
     *
     * @return
     */
    T getUser();
}
