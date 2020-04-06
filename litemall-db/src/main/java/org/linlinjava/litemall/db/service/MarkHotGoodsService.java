package org.linlinjava.litemall.db.service;

import com.github.pagehelper.PageHelper;
import org.apache.ibatis.annotations.Select;
import org.linlinjava.litemall.db.dao.LitemallFootprintMapper;
import org.linlinjava.litemall.db.dao.LitemallGoodsMapper;
import org.linlinjava.litemall.db.dao.LitemallHotgoodsMapper;
import org.linlinjava.litemall.db.domain.LitemallFootprint;
import org.linlinjava.litemall.db.domain.LitemallFootprintExample;
import org.linlinjava.litemall.db.domain.LitemallFootprint.Column;
import org.linlinjava.litemall.db.domain.LitemallGoods;
import org.linlinjava.litemall.db.domain.LitemallHotgoods;
import org.linlinjava.litemall.db.service.LitemallFootprintService;
import org.linlinjava.litemall.db.service.LitemallGoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class MarkHotGoodsService {
    private final Integer period = 7;
    Column[] columns = new Column[]{Column.goodsId};
    @Resource
    private LitemallFootprintMapper litemallFootprintMapper;
    @Resource
    private LitemallGoodsMapper litemallGoodsMapper;

    public void markHotGoods(){
        Set<Integer> recentFootprintSet = getRecentFootprint(period);
        LitemallGoods record = new LitemallGoods();
        for(Integer goodId: recentFootprintSet){
            record.setId(goodId);
            record.setIsHot(true);
            record.setUpdateTime(LocalDateTime.now());
            litemallGoodsMapper.updateByPrimaryKeySelective(record);
        }
    }
    private Set<Integer> getRecentFootprint(Integer period){

        Set<Integer> recentFootprintSet = new HashSet<Integer>();
        LitemallFootprintExample example = new LitemallFootprintExample();
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime then = now.minusDays(period);
        example.or().andAddTimeGreaterThan(then);
        List<LitemallFootprint> litemallFootprints = litemallFootprintMapper.selectByExampleSelective(example, columns);
        for(LitemallFootprint litemallFootprint: litemallFootprints){
           recentFootprintSet.add(litemallFootprint.getGoodsId());
        }
        return recentFootprintSet;
    }

}
