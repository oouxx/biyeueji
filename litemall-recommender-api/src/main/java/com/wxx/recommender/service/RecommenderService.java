package com.wxx.recommender.service;

import com.wxx.recommender.domain.Recommendation;
import com.wxx.recommender.dto.*;
import com.wxx.recommender.utils.Constant;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RecommenderService {
    @Autowired
    private MongoTemplate mongoTemplate;
    // user recs
    public List<Recommendation> getCollaborativeFilteringRecommendations(UserRecommendationDTO request) {
        MongoCollection<Document> userRecsCollection = mongoTemplate.getCollection(Constant.MONGODB_USER_RECS_COLLECTION);
        return getRecommendations(request, userRecsCollection);
    }
    // user steam recs
    public List<Recommendation> getStreamRecommendation(UserRecommendationDTO request){
        MongoCollection<Document> userStreamRecsCollection = mongoTemplate.getCollection(Constant.MONGODB_STREAM_RECS_COLLECTION);
        return getRecommendations(request, userStreamRecsCollection);
    }
    private List<Recommendation> getRecommendations(Document document) {
        List<Recommendation> recommendations = new ArrayList<>();
        ArrayList<Document> recs = document.get("recs", ArrayList.class);
        for (Document recDoc : recs) {
            recommendations.add(new Recommendation(recDoc.getInteger("productId"), recDoc.getDouble("score")));
        }
        return recommendations;
    }
    private List<Recommendation> getRecommendations(UserRecommendationDTO request, MongoCollection<Document> userRecsCollection) {
        List<Recommendation> recommendations = new ArrayList<>();
        Query query = new Query(Criteria.where("userId").is(request.getUserId()));
        Document userRecsDocument = userRecsCollection.find(new Document("userId", request.getUserId())).first();
        // TODO 防止空指针异常 （使用Option??）
        ArrayList<Document> recs = userRecsDocument.get("recs", ArrayList.class);
        for (Document recDoc : recs) {
            recommendations.add(new Recommendation(recDoc.getInteger("productId"), recDoc.getDouble("score")));
        }
        return recommendations;
    }
}