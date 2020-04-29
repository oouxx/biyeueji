package com.wxx.offline

import breeze.numerics.sqrt
import org.apache.spark.SparkConf
import org.apache.spark.mllib.recommendation.{ALS, MatrixFactorizationModel, Rating}
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions.col
import com.wxx.offline.OfflineRecommender.sparkConf

object ALSTrainer {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder().config(sparkConf).getOrCreate()
    import spark.implicits._
    // 加载数据
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
        .as[ProductRating]
        .rdd
        .map(
          rating => Rating(rating.userId, rating.productId, rating.score)
        ).cache()
    // 数据集切分成训练集和测试集
    val splits = ratingRDD.randomSplit(Array(0.8, 0.2))
    val trainingRDD = splits(0)
    val testingRDD = splits(1)
    // 核心实现：输出最优参数
    adjustALSParams( trainingRDD, testingRDD )
    spark.stop()
  }

  def adjustALSParams(trainData: RDD[Rating], testData: RDD[Rating]): Unit ={
    // 遍历数组中定义的参数取值
    val result = for( rank <- Array(5, 10, 20, 50); lambda <- Array(1, 0.1, 0.01) )
      yield {
        val model = ALS.train(trainData, rank, 10, lambda)
        val rmse = getRMSE( model, testData )
        ( rank, lambda, rmse )
      }
    // 按照rmse排序并输出最优参数
    println(result.minBy(_._3))
  }

  def getRMSE(model: MatrixFactorizationModel, data: RDD[Rating]): Double = {
    // 构建userProducts，得到预测评分矩阵
    val userProducts = data.map( item=> (item.user, item.product) )
    val predictRating = model.predict(userProducts)
    // 按照公式计算rmse，首先把预测评分和实际评分表按照(userId, productId)做一个连接
    val observed = data.map( item=> ( (item.user, item.product),  item.rating ) )
    val predict = predictRating.map( item=> ( (item.user, item.product),  item.rating ) )
    sqrt(
      observed.join(predict).map{
      case ( (userId, productId), (actual, pre) ) =>
        val err = actual - pre
        err * err
    }.mean()
    )
  }
}
