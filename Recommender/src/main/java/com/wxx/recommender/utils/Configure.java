package com.wxx.recommender.utils;

import com.mongodb.MongoClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.data.redis.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

@Configuration
public class Configure {

    private String mongoHost;
    private int mongoPort;
    private String redisHost;

    public Configure(){
        try{
            Properties properties = new Properties();
            Resource resource = new ClassPathResource("recommend.properties");
            properties.load(new FileInputStream(resource.getFile()));
            this.mongoHost = properties.getProperty("mongo.host");
            this.mongoPort = Integer.parseInt(properties.getProperty("mongo.port"));
            this.redisHost = properties.getProperty("redis.host");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.exit(0);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    @Bean(name = "mongoClient")
    public MongoClient getMongoClient(){
        MongoClient mongoClient = new MongoClient( mongoHost , mongoPort );
        return mongoClient;
    }

//    @Bean(name = "jedis")
//    public Jedis getRedisClient() {
//        Jedis jedis = new Jedis(redisHost);
//        return jedis;
//    }
}
