package org.apache.dubbo.samples.cache;

import com.google.common.collect.Lists;
import org.apache.dubbo.samples.cache.api.CacheService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Arrays;
import java.util.List;

public class LRUConsumer {
    public static void main(String[] args) throws Exception {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[]{"spring/cache-consumer.xml"});
        context.start();
        CacheService cacheService = (CacheService) context.getBean("cacheService");


        // 缓存结果确是节省了dubbo的调用
        // 缓存的有效时间没有说明....
        // 需要看下缓存的实现方式
        List<Integer> integers = Arrays.asList(0, 1, 2, 3, 4, 2, 3, 4, 3, 2, 1, 0);
        for (Integer i : integers) {
            String res = cacheService.findCache(String.valueOf(i));
            System.out.println(res);
        }
    }
}
