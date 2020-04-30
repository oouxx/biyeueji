package com.wxx.recommender.service;

import com.wxx.recommender.domain.Recommendation;
import com.wxx.recommender.dto.*;
import com.wxx.recommender.utils.Constant;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Sorts;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RecommenderService {
    @Autowired
    private MongoClient mongoClient;
    // 获取热门电影的条目
    public List<Recommendation> getHotRecommendations(HotRecommendationDTO request) {
        MongoCollection<Document> rateMoreMoviesRecentlyCollection = mongoClient.getDatabase(Constant.MONGODB_DATABASE).getCollection(Constant.MONGODB_RATE_MORE_PRODUCTS_RECENTLY_COLLECTION);
        FindIterable<Document> documents = rateMoreMoviesRecentlyCollection.find().sort(Sorts.descending("yearmonth")).limit(request.getSum());

        List<Recommendation> recommendations = new ArrayList<>();
        for (Document document : documents) {
            recommendations.add(new Recommendation(document.getInteger("productId"), 0D));
        }
        System.out.println(recommendations);
        return recommendations;
    }
    // 获取高分推荐
    public List<Recommendation> getRateMoreRecommendations(RateMoreRecommendationDTO request) {
        MongoCollection<Document> rateMoreProductsCollection = mongoClient.getDatabase(Constant.MONGODB_DATABASE).getCollection(Constant.MONGODB_RATE_MORE_PRODUCTS_COLLECTION);
        FindIterable<Document> documents = rateMoreProductsCollection.find().sort(Sorts.descending("count")).limit(request.getSum());

        List<Recommendation> recommendations = new ArrayList<>();
        for (Document document : documents) {
            recommendations.add(new Recommendation(document.getInteger("productId"), 0D));
        }
        return recommendations;
    }
    public List<Recommendation> getItemCFRecommendations(ItemCFRecommendationDTO request) {
        MongoCollection<Document> itemCFProductsCollection = mongoClient.getDatabase(Constant.MONGODB_DATABASE).getCollection(Constant.MONGODB_ITEMCF_COLLECTION);
        Document document = itemCFProductsCollection.find(new Document("productId", request.getId())).first();

        System.out.println(document.get("recs"));
        return getRecommendations(document);
    }
    public List<Recommendation> getContentBasedRecommendations(ContentBasedRecommendationDTO request) {
        MongoCollection<Document> contentBasedProductsCollection = mongoClient.getDatabase(Constant.MONGODB_DATABASE).getCollection(Constant.MONGODB_CONTENTBASED_COLLECTION);
        Document document = contentBasedProductsCollection.find(new Document("productId", request.getId())).first();

        System.out.println(document.get("recs"));
        return getRecommendations(document);
    }
    // user recs
    public List<Recommendation> getCollaborativeFilteringRecommendations(UserRecommendationDTO request) {
        MongoCollection<Document> userRecsCollection = mongoClient.getDatabase(Constant.MONGODB_DATABASE).getCollection(Constant.MONGODB_USER_RECS_COLLECTION);
        return getRecommendations(request, userRecsCollection);
    }

    private List<Recommendation> getRecommendations(UserRecommendationDTO request, MongoCollection<Document> userRecsCollection) {
        Document document = userRecsCollection.find(new Document("userId", request.getUserId())).first();
        return getRecommendations(document);
    }

    private List<Recommendation> getRecommendations(Document document) {
        List<Recommendation> recommendations = new ArrayList<>();
        ArrayList<Document> recs = document.get("recs", ArrayList.class);
        for (Document recDoc : recs) {
            recommendations.add(new Recommendation(recDoc.getInteger("productId"), recDoc.getDouble("score")));
        }
        return recommendations;
    }

    // user stream recs
    public List<Recommendation> getStreamRecommendations(UserRecommendationDTO request) {
        MongoCollection<Document> userRecsCollection = mongoClient.getDatabase(Constant.MONGODB_DATABASE).getCollection(Constant.MONGODB_STREAM_RECS_COLLECTION);
        return getRecommendations((UserRecommendationDTO) request, userRecsCollection);
    }
}