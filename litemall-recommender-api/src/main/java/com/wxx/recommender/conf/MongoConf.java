package com.wxx.recommender.conf;

import com.mongodb.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.aggregation.ArrayOperators;


@Configuration
public class MongoConf {

    @Value("${spring.data.mongodb.host}")
    private String mongoHost;

    @Value("${spring.data.mongodb.port}")
    private String mongoPort;

    @Value("${spring.data.mongodb.database}")
    private String database;

    @Value("${spring.data.mongodb.password}")
    private String password;

    @Value("${spring.data.mongodb.username}")
    private String username;

    /**
     *
     * @return
     */
//    @Bean(name = "mongoClient")
//    public MongoClient mongoClient() {
//        MongoClient mongoClient = new MongoClient( mongoHost , Integer.parseInt(mongoPort));
//        return mongoClient;
//    }
}
