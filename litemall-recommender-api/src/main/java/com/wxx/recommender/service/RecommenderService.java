package com.wxx.recommender.service;

import com.mongodb.MongoClient;
import com.wxx.recommender.domain.Recommendation;
import com.wxx.recommender.dto.*;
import com.wxx.recommender.utils.Constant;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RecommenderService {
    private static MongoClient mongoClient = new MongoClient("47.93.97.16", Integer.parseInt("27017"));
    // user recs
    public List<Recommendation> getCollaborativeFilteringRecommendations(UserRecommendationDTO request) {
        MongoCollection<Document> userOfflineRecsCollection = mongoClient.getDatabase("recommender").getCollection((Constant.MONGODB_USER_RECS_COLLECTION));
        return getRecommendations(request, userOfflineRecsCollection);
    }
    // user steam recs
    public List<Recommendation> getStreamRecommendation(UserRecommendationDTO request){
        MongoCollection<Document> userStreamRecsCollection = mongoClient.getDatabase("recommender").getCollection((Constant.MONGODB_STREAM_RECS_COLLECTION));
        return getRecommendations(request, userStreamRecsCollection);
    }
    // itemcf recs
    public List<Recommendation> getItemCFRecommendation(Integer productId){
        List<Recommendation> recommendations = new ArrayList<>();
        MongoCollection<Document> userItemCFRecsCollection = mongoClient.getDatabase("recommender").getCollection((Constant.MONGODB_ITEMCF_COLLECTION));
        Query query = new Query(Criteria.where("userId").is(productId));
        Document userRecsDocument = userItemCFRecsCollection.find(new Document("productId", productId)).first();
        ArrayList<Document> recs = userRecsDocument.get("recs", ArrayList.class);
        for (Document recDoc : recs) {
            recommendations.add(new Recommendation(recDoc.getInteger("productId"), recDoc.getDouble("score")));
        }
        return recommendations;
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
        ArrayList<Document> recs = userRecsDocument.get("recs", ArrayList.class);
        for (Document recDoc : recs) {
            recommendations.add(new Recommendation(recDoc.getInteger("productId"), recDoc.getDouble("score")));
        }
        return recommendations;
    }
}