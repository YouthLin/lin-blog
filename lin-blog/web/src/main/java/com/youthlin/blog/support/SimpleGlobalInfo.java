package com.youthlin.blog.support;

import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.Callable;

/**
 * 创建： lin
 * 时间： 2017-05-05 12:19
 */
@Component
public class SimpleGlobalInfo<K, V> implements GlobalInfo<K, V> {
    private static final Logger LOGGER = LoggerFactory.getLogger(SimpleGlobalInfo.class);
    private final Map<K, V> map = Maps.newConcurrentMap();

    @Override
    public void set(K key, V value) {
        Preconditions.checkNotNull(key);
        if (value == null) {
            map.remove(key);
        } else {
            map.put(key, value);
        }
    }

    @Override
    public V get(K key) {
        Preconditions.checkNotNull(key);
        return map.get(key);
    }

    @Override
    public V get(K key, Callable<V> valueLoader) {
        V v = get(key);
        if (v == null) {
            try {
                v = valueLoader.call();
            } catch (Exception e) {
                LOGGER.warn("Can not load value.", e);
            }
        }
        return v;
    }
}
