package ru.otus.core.cache;

import java.time.Duration;

import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class HWCacheDemo {
    private static final Logger logger = LoggerFactory.getLogger(HWCacheDemo.class);

    public static void main(String[] args) {
        new HWCacheDemo().demo();
    }

    record Entity(Long id, String name) {}

    @SneakyThrows
    private void demo() {
        HwCacheImpl<String, Entity> cache = new HwCacheImpl<>();

        // пример, когда Idea предлагает упростить код, при этом может появиться "спец"-эффект
        var listener = new HwListener<String, Entity>() {
            @Override
            public void notify(String key, Entity value, String action) {
                logger.info("key:{}, value:{}, action: {}", key, value, action);
            }
        };

        cache.addListener(listener);
        for (int i = 0; i < 4000; i++) {
            var entity = new Entity((long) i, "entity" + i);
            cache.put(String.valueOf(entity.id()), entity);
        }

        logger.info("stored before gc(): {}", cache.size());
        System.gc();
        Thread.sleep(Duration.ofSeconds(3));
        logger.info("stored after gc(): {}", cache.size());
    }
}
