package org.linlinjava.litemall.wx.task;

import org.linlinjava.litemall.core.task.Task;
import org.linlinjava.litemall.db.service.MarkHotGoodsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class MarkHotGoodsTask extends Task{
    private static final Logger LOGGER = LoggerFactory.getLogger(MarkHotGoodsTask.class);

    private static final long MINUTE = 60000;
    @Autowired
    private MarkHotGoodsService markHotGoodsService;

    public MarkHotGoodsTask(){
        super("MarkHotGoodsTask" , 60 * 1000);
    }
    @Scheduled(fixedRate = 3 * MINUTE)
    @Override
    public void run() {
        LOGGER.info("当前时间：{}\t\t任务：MarkHotGoodsTask，每3分钟执行一次", System.currentTimeMillis());
        markHotGoodsService.markHotGoods();
    }
}
