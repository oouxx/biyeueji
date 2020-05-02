package com.wxx.recommender.dto;

public class ProductRecommendationDTO {
    private int productId;

    private int sum;

    public ProductRecommendationDTO(int productId, int sum) {
        this.productId = productId;
        this.sum = sum;
    }

    public int getSum() {
        return sum;
    }

    public void setSum(int sum) {
        this.sum = sum;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }
}
