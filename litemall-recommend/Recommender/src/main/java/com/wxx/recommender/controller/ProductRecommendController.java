package com.wxx.recommender.controller;

import com.wxx.recommender.domain.Recommendation;
import com.wxx.recommender.dto.UserRecommendationDTO;
import com.wxx.recommender.service.ProductService;
import com.wxx.recommender.service.RatingService;
import com.wxx.recommender.service.RecommenderService;
import org.linlinjava.litemall.core.util.ResponseUtil;
import org.linlinjava.litemall.db.domain.LitemallUser;
import org.linlinjava.litemall.db.service.LitemallUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
public class ProductRecommendController {
    @Autowired
    private RecommenderService recommenderService;
    @Autowired
    private ProductService productService;
    @Autowired
    private RatingService ratingService;

    // 离线推荐
    @GetMapping(value = "/offline")
    public Object getOfflineProducts(@RequestParam("userId")Integer userId, @RequestParam("num")int num) {
//        LitemallUser litemallUser= litemallUserService.findById(userId);
        List<Recommendation> recommendations = recommenderService.getCollaborativeFilteringRecommendations(new UserRecommendationDTO(userId, num));
        return ResponseUtil.ok(recommendations);
    }

    // 实时推荐
//    @GetMapping(value = "/stream")
//    public  getStreamProducts(@RequestParam("username")String username,@RequestParam("num")int num, Model model) {
//        User user = userService.findByUsername(username);
//        List<Recommendation> recommendations = recommenderService.getStreamRecommendations(new UserRecommendationRequest(user.getUserId(), num));
//    }

}
