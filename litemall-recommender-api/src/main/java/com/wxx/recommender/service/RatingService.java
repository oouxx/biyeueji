package com.wxx.recommender.service;

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
        MongoCollection<Document> ratingCollection = mongoTemplate.getCollection(Constant.MONGODB_RATING_COLLECTION);
        Document document = new Document();

        document.put("userId", rating.getUserId());
        document.put("productId", rating.getProductId());
        document.put("score", rating.getScore());
        document.put("timestamp", rating.getTimestamp());
        ratingCollection.insertOne(document);
    }
}
