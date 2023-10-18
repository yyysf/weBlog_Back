package com.yang.hdyplm.scheduling;

import com.yang.hdyplm.mapper.PhotoMapper;
import com.yang.hdyplm.utils.RedisCache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Set;

@Slf4j
@Component
public class RedisViews {
    @Autowired
    private RedisCache redisCache;
    @Autowired
    private PhotoMapper photoMapper;

    @Scheduled(cron = "0 */30 * * * *")  //每半小时执行一次
    public void RedisViewsIncr(){
        Map<String, Object> views = redisCache.getCacheMap("views");
        Set<Map.Entry<String, Object>> entries = views.entrySet();
        for (Map.Entry<String, Object> entry : entries) {
            photoMapper.updateViews(entry.getKey(),Integer.valueOf(String.valueOf(entry.getValue())));
        }
        log.info("Views已更新到MySql数据库");

    }
}
