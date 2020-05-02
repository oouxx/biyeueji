package com.wxx.recommender.controller;

import com.wxx.recommender.domain.Recommendation;
import com.wxx.recommender.dto.UserRecommendationDTO;
import com.wxx.recommender.service.RecommenderService;
import org.linlinjava.litemall.core.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping(value = "/recs")
public class ProductRecommendController {
    @Autowired
    private RecommenderService recommenderService;

    // 离线推荐
    @GetMapping(value = "/offline")
    public Object getOfflineProducts(@RequestParam("userId")Integer userId, @RequestParam("num")int num) {
        List<Recommendation> recommendations = recommenderService.getCollaborativeFilteringRecommendations(new UserRecommendationDTO(userId, num));
        return ResponseUtil.ok(recommendations);
    }
    // 实时推荐
    @GetMapping(value = "/stream")
    public Object getStreamProducts(@RequestParam("userId")Integer userId, @RequestParam("num")int num) {
        List<Recommendation> recommendations = recommenderService.getStreamRecommendation(new UserRecommendationDTO(userId, num));
        return ResponseUtil.ok(recommendations);
    }



}
