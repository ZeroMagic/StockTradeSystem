package cn.edu.zju.util;

import cn.edu.zju.bean.Position;
import cn.edu.zju.dao.PositionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Anthony.
 */


@Component
public class ScheduledTasks {

    @Autowired
    private PositionRepository positionRepository;

    //@Scheduled(fixedRate = 5000)
    @Scheduled(cron = "0 0 12 ? * WED") //每周三12：00运行
    public void updateToken(){
        System.out.println("开始更新Token");
        CacheUtil cacheUtil = CacheUtil.getInstance();
        HashMap<String, String> cache = cacheUtil.getCache();
        if(cache.size() <= 0){
            System.out.println("缓存为空");
            return;
        }
        cache.forEach((k,v) -> {
            cache.put(k, TokenUtil.getToken());
        });
        System.out.println("token更新完成");
    }

    //每日零点更新可用股票
    @Scheduled(cron = "0 0 0 ? * *") //每日零点运行
    public void updateAvailable(){
        System.out.println("开始更新Available");
        List<Position> all = positionRepository.findAll();
        for (Position each : all) {
            each.setAvailable(each.getTotal());
            positionRepository.saveAndFlush(each);
        }
        System.out.println("Available更新完成");
    }
}
