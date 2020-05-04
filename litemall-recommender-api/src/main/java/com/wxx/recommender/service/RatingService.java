package com.wxx.recommender.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.wxx.recommender.domain.Rating;
import com.wxx.recommender.utils.Constant;
import org.bson.Document;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

@Service
public class RatingService {
    public void saveRating(Rating rating){
        // TODO 优化代码 把mongoClient 抽象出来
        MongoClient mongoClient = new MongoClient("47.93.97.16", Integer.parseInt("27017"));
        MongoCollection<Document> ratingCollection = mongoClient.getDatabase(Constant.MONGODB_DATABASE).getCollection(Constant.MONGODB_RATING_COLLECTION);
        Document document = new Document();
        document.put("userId", rating.getUserId());
        document.put("productId", rating.getProductId());
        document.put("score", rating.getScore());
        document.put("timestamp", rating.getTimestamp());
        ratingCollection.insertOne(document);

        updateRedis(rating);
    }

    private void updateRedis(Rating rating) {
        Jedis jedis = new Jedis("47.93.97.16");
        if (jedis.exists("userId:" + rating.getUserId()) && jedis.llen("userId:" + rating.getUserId()) >= Constant.REDIS_PRODUCT_RATING_QUEUE_SIZE) {
            jedis.rpop("userId:" + rating.getUserId());
        }
        jedis.lpush("userId:" + rating.getUserId(), rating.getProductId() + ":" + rating.getScore());
    }

}
