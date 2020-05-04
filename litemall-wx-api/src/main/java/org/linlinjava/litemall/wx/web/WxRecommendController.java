package org.linlinjava.litemall.wx.web;

import com.wxx.recommender.domain.Recommendation;
import com.wxx.recommender.dto.UserRecommendationDTO;
import com.wxx.recommender.service.RecommenderService;
import io.swagger.models.auth.In;
import org.linlinjava.litemall.core.util.ResponseUtil;
import org.linlinjava.litemall.db.domain.LitemallGoods;
import org.linlinjava.litemall.db.service.LitemallGoodsService;
import org.linlinjava.litemall.wx.annotation.LoginUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/wx/recs")
public class WxRecommendController {
    @Autowired
    private RecommenderService recommenderService;
    @Autowired
    private LitemallGoodsService litemallGoodsService;
    @GetMapping("offline")
    public Object offlineRecommend(@LoginUser Integer userId, @NotNull Integer num){

        List<LitemallGoods> recommendGoodList = new ArrayList<>();
        List<Recommendation> recommendList = recommenderService.getCollaborativeFilteringRecommendations(new UserRecommendationDTO(userId, num));
        for(Recommendation recommendation: recommendList){
            Integer productId = recommendation.getProductId();
            LitemallGoods litemallGoods = litemallGoodsService.findById(productId);
            recommendGoodList.add(litemallGoods);
        }
        return ResponseUtil.okList(recommendGoodList);
    }
}
