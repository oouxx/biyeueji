package com.wxx.recommender.dto;

public class UserRecommendationDTO {
    private int userId;


    public UserRecommendationDTO(int userId ){
        this.userId = userId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
