package com.youthlin.blog.support;

import java.util.concurrent.Callable;

/**
 * 创建： lin
 * 时间： 2017-05-04 19:57
 */
public interface GlobalInfo<K, V> {
    void set(K key, V value);

    V get(K key);

    V get(K key, Callable<V> callable);
}
