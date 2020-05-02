package com.wxx.recommender.service;

import com.wxx.recommender.domain.Rating;
import com.wxx.recommender.utils.Constant;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;


// 埋点日志服务
@Service
public class RecommendLogService {
    private static Logger logger = Logger.getLogger(RecommendLogService.class.getName());
    public void ratingLogger(Rating rating){
        logger.info(Constant.PRODUCT_RATING_PREFIX + ":" + rating.getUserId() + "|" + rating.getProductId() + "|" + rating.getScore() + "|" + rating.getTimestamp());
    }
}
