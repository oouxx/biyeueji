package com.wxx.offline

object Config {
  val sparkConfig = Map(
    "spark.cores" -> "local[*]",
  )

  val mongoConfig = Map(
    "uri" -> "mongodb://47.93.97.16:27017/recommender",
    "db" -> "recommender",
    "user" -> "root",
    "password" -> "wxx1512",
  )

  val mysqlConfig = Map(
    "url" -> "jdbc:mysql://wangxinxing.top:3306/litemall",
    "user" -> "litemall",
    "password" -> "litemall123456",
  )

  val tablesConfig = Map(
    "Rating" -> "litemall_comment",
    "UserRecs" -> "UserRecs",
    "ProductRecs" -> "ProductRecs",
    "UserMaxRecommendation" -> 20
  )
}
