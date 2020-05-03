package com.wxx.recommender.conf;

import com.mongodb.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class MongoConf {
//    public static final String COMMA = ";";
//    public static final String COLON = ":";
//
//    @Value("${spring.data.mongodb.uri}")
//    private String uri;
//
//    @Value("${spring.data.mongodb.username}")
//    private String username;
//
//    @Value("${spring.data.mongodb.database}")
//    private String dbname;
//
//    @Value("${spring.data.mongodb.password}")
//    private String password;
//
//    /**
//     * 获取mongodb的地址
//     *
//     * @return
//     */
//    private List<ServerAddress> getMongoDbAddress() {
//        List<ServerAddress> serverAddrList = new ArrayList<ServerAddress>();
//        //如果有多个服务器的话
//        if (this.uri.indexOf(COMMA) > 0) {
//            String[] addressArrays = uri.split(COMMA);
//            String[] hostPort;
//            for (String address : addressArrays) {
//                hostPort = address.split(COLON);
//                ServerAddress serverAdress = new ServerAddress(hostPort[0], Integer.parseInt(hostPort[1]));
//                serverAddrList.add(serverAdress);
//            }
//        } else {
//            String[] hostPort = uri.split(COLON);
//            ServerAddress serverAdress = new ServerAddress(hostPort[0], Integer.parseInt(hostPort[1]));
//            serverAddrList.add(serverAdress);
//        }
//        return serverAddrList;
//    }
//    /**
//     * 设置连接参数
//     */
//    private MongoClientOptions getMongoClientOptions() {
//        MongoClientOptions.Builder builder = MongoClientOptions.builder();
//        // todo 添加其他参数配置
//        //最大连接数
////        builder.connectionsPerHost(Integer.valueOf(connectionsPerHost));
//        MongoClientOptions options = builder.readPreference(ReadPreference.nearest()).build();
//        return options;
//    }
//    /**
//     *
//     * @return
//     */
//    @Bean
//    public MongoClient mongoClient() {
//        //使用数据库名、用户名密码登录
//        MongoCredential credential = MongoCredential.createCredential(username, password, password.toCharArray());
//        //创建Mongo客户端
//        List<ServerAddress> serverAddressList = new ArrayList<>();
//        serverAddressList.add(new ServerAddress("47.93.97.16", Integer.parseInt("27017")));
//        return new MongoClient(serverAddressList, credential, getMongoClientOptions());
//    }
//    /**
//     * 注册mongodb操作类
//     * @param mongoClient
//     * @return
//     */
//    @Bean
//    @ConditionalOnClass(MongoClient.class)
//    public MongoTemplate mongoTemplate(MongoClient mongoClient) {
//        MongoTemplate mongoTemplate = new MongoTemplate(new SimpleMongoDbFactory(mongoClient, dbname));
//        return mongoTemplate;
//    }
}
