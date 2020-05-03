package com.wxx.recommender.service;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.wxx.recommender.domain.Rating;
import com.wxx.recommender.utils.Constant;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

@Service
public class RatingService {
    @Autowired
    private MongoTemplate mongoTemplate;
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
    }
}
