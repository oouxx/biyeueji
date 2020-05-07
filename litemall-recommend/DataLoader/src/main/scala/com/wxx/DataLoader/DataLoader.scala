package com.wxx.DataLoader

import com.mongodb.casbah.commons.MongoDBObject
import com.mongodb.casbah.{MongoClient, MongoClientURI}
import com.wxx.offline.Config
import org.apache.spark.SparkConf
import org.apache.spark.sql.{DataFrame, Row, SparkSession}
import org.apache.spark.sql.functions.col

/**
  * Product数据集
  * 3982                            商品ID
  * Fuhlen 富勒 M8眩光舞者时尚节能    商品名称
  * 1057,439,736                    商品分类ID，不需要
  * B009EJN4T2                      亚马逊ID，不需要
  * https://images-cn-4.ssl-image   商品的图片URL
  * 外设产品|鼠标|电脑/办公           商品分类
  * 富勒|鼠标|电子产品|好用|外观漂亮   商品UGC标签
  */
case class Product( productId: Int, name: String, imageUrl: String, categories: String, tags: String )
case class Goods(productId: Int, name: String, imageUrl: String, category_id: Int, tags:String)
case class Category(category_id: Int, categories: String)
/**
  * Rating数据集
  * 4867        用户ID
  * 457976      商品ID
  * 5.0         评分
  * 1395676800  时间戳
  */
case class Rating( userId: Int, productId: Int, score: Double, timestamp: BigInt )

/**
  * MongoDB连接配置
  * @param uri    MongoDB的连接uri
  * @param db     要操作的db
  */
case class MongoConfig( uri: String, db: String )

object DataLoader {
  // 定义mongodb中存储的表名
  val MONGODB_PRODUCT_COLLECTION = "Product"
  val MONGODB_RATING_COLLECTION = "Rating"

  def main(args: Array[String]): Unit = {
    // 创建一个spark config
    val sparkConf = new SparkConf().setMaster("local[*]").setAppName("DataLoader")
    // 创建spark session
    val spark = SparkSession.builder().config(sparkConf).getOrCreate()

    import spark.implicits._

    // 加载数据
     val productFrame = spark.read
      .options(Config.mysqlConfig)
      .option("dbtable", "litemall_goods")
      .format("jdbc")
      .load()
      .select("id","category_id", "name", "pic_url", "keywords")
      .withColumnRenamed("id", "productId")
      .withColumnRenamed("pic_url", "imageUrl")
      .withColumnRenamed("keywords", "tags")
      .as[Goods]
      .cache()

    val categoryFrame = spark.read
      .options(Config.mysqlConfig)
      .option("dbtable", "litemall_category")
      .format("jdbc")
      .load()
      .select("id", "name")
      .withColumnRenamed("id", "category_id")
      .withColumnRenamed("name", "categories")
      .as[Category]
      .cache()
    val productDF = productFrame.join(categoryFrame, Seq("category_id")).drop("category_id")

    val ratingRDD = spark.read
      .options(Config.mysqlConfig)
      .option("dbtable", "litemall_comment")
      .format("jdbc")
      .load()
      .select("user_id", "value_id", "star", "add_time")
      .filter(col("type").equalTo(0))
      .withColumnRenamed("user_id", "userId")
      .withColumnRenamed("value_id", "productId")
      .withColumnRenamed("star", "score")
      .withColumnRenamed("add_time", "timestamp")
      .as[Rating]
      .rdd
      .map(
        rating => (rating.userId, rating.productId, rating.score, rating.timestamp)
      ).cache()
    val ratingDF = ratingRDD.toDF("userId", "productId", "score", "timestamp")


    implicit val mongoConfig = MongoConfig( Config.mongoConfig("uri"), Config.mongoConfig("db") )
    storeDataInMongoDB( productDF, ratingDF )
    spark.stop()
  }
  def storeDataInMongoDB( productDF: DataFrame, ratingDF: DataFrame )(implicit mongoConfig: MongoConfig): Unit ={
    // 新建一个mongodb的连接，客户端
    val mongoClient = MongoClient( MongoClientURI(mongoConfig.uri) )
    // 定义要操作的mongodb表，可以理解为 db.Product
    val productCollection = mongoClient( mongoConfig.db )( MONGODB_PRODUCT_COLLECTION )
    val ratingCollection = mongoClient( mongoConfig.db )( MONGODB_RATING_COLLECTION )

    // 如果表已经存在，则删掉
    productCollection.dropCollection()
    ratingCollection.dropCollection()

    // 将当前数据存入对应的表中
    productDF.write
      .option("uri", mongoConfig.uri)
      .option("collection", MONGODB_PRODUCT_COLLECTION)
      .mode("overwrite")
      .format("com.mongodb.spark.sql")
      .save()

    ratingDF.write
      .option("uri", mongoConfig.uri)
      .option("collection", MONGODB_RATING_COLLECTION)
      .mode("overwrite")
      .format("com.mongodb.spark.sql")
      .save()

    // 对表创建索引
    productCollection.createIndex( MongoDBObject( "productId" -> 1 ) )
    ratingCollection.createIndex( MongoDBObject( "productId" -> 1 ) )
    ratingCollection.createIndex( MongoDBObject( "userId" -> 1 ) )

    mongoClient.close()
  }
}
