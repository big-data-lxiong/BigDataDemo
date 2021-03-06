package io.github.streamingwithflink.chapter2

import org.apache.flink.streaming.connectors.redis.common.mapper.{RedisCommand, RedisCommandDescription, RedisMapper}

class MyRedisMapper extends RedisMapper[(String, Int)] {

  override def getCommandDescription: RedisCommandDescription = {
    new RedisCommandDescription(RedisCommand.SET, "flink")
  }

  override def getKeyFromData(t: (String, Int)): String = {
    "max"
  }

  override def getValueFromData(t: (String, Int)): String = {
    t._1.toString
  }

}