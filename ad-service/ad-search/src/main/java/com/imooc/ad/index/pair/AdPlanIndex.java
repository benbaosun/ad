package com.imooc.ad.index.pair;

import com.imooc.ad.index.IndexAware;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Seven
 * @date 2019/10/20 0:16
 * @description 推广计划的索引操作类
 */
@Slf4j
@Component
public class AdPlanIndex implements IndexAware<Long, AdPlanObject> {
    /**
     * 内存存储索引信息
     */
    private static Map<Long, AdPlanObject> objectMap;

    static {
        // 线程安全的map存储对象
        objectMap = new ConcurrentHashMap<>();
    }

    @Override
    public AdPlanObject get(Long key) {
        return objectMap.get(key);
    }

    @Override
    public void add(Long key, AdPlanObject value) {
        log.info("before add: {}", objectMap);
        objectMap.put(key, value);
        log.info("after add: {}", objectMap);
    }

    @Override
    public void update(Long key, AdPlanObject value) {
        log.info("before update: {}", objectMap);

        // 先查询在判断
        AdPlanObject oldValue = get(key);
        if (null == oldValue) {
            objectMap.put(key, value);
        } else {
            oldValue.update(value);
        }

        log.info("after update: {}", objectMap);
    }

    @Override
    public void delete(Long key, AdPlanObject value) {
        log.info("before delete: {}", objectMap);
        objectMap.remove(key);
        log.info("after delete: {}", objectMap);
    }

}
