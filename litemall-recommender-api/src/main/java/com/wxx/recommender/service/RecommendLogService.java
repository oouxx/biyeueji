package com.wxx.recommender.service;

import com.wxx.recommender.domain.Rating;
import com.wxx.recommender.utils.Constant;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


// 埋点日志服务
@Service
public class RecommendLogService {
    @Autowired
    private RecommenderKafkaService recommenderKafkaService;
    private static Logger logger = Logger.getLogger(RecommendLogService.class.getName());
    public void ratingLogger(Rating rating){
        String line = Constant.PRODUCT_RATING_PREFIX + "|" + rating.getUserId() + "|" + rating.getProductId() + "|" + rating.getScore() + "|" + rating.getTimestamp();
        logger.info(line);
        try{
            recommenderKafkaService.main(line);
        }catch (Exception e){
            System.out.println(e);
        }
    }
}
