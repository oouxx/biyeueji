package com.wxx.streaming

import org.apache.kafka.common.serialization.StringDeserializer

object Config {
  val sparkConfig = Map(
    "spark.cores" -> "local[*]"
  )
  val kafkaConfig = Map(
    "topic" -> "recommender",
    "bootstrap.servers" -> "47.93.97.16:9092",
    "key.deserializer" -> classOf[StringDeserializer],
    "value.deserializer" -> classOf[StringDeserializer],
    "group.id" -> "recommender",
    "auto.offset.reset" -> "latest"
  )
  val redisConfig = Map(
   "host" -> "47.93.97.16"
  )

  val mongoConfig = Map(
  "uri" -> "mongodb://47.93.97.16:27017/recommender",
  )

}
