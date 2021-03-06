package io.github.streamingwithflink.chapter2

import org.apache.flink.streaming.api.scala.{StreamExecutionEnvironment, _}
import org.apache.flink.streaming.connectors.redis.RedisSink
import org.apache.flink.streaming.connectors.redis.common.config.FlinkJedisPoolConfig
import org.apache.flink.streaming.connectors.redis.common.mapper.{RedisCommand, RedisCommandDescription, RedisMapper}


object RedisSinkTest {

  def main(args: Array[String]) {

    // set up the execution environment
    val env = StreamExecutionEnvironment.getExecutionEnvironment;

    /**
      * 通过netcat获取数据
      */
    var text = env.socketTextStream("10.0.24.59", 9999, '\n', 0).setParallelism(1);

    /**
      * 获取hdfs的数据
      */
//    var text = env.readTextFile("hdfs://10.0.24.124:8020/test1.txt");

    val conf = new FlinkJedisPoolConfig.Builder()
      .setHost("10.0.24.59")
      .setPort(16379)
      .setDatabase(3)
      .setPassword("sugon2019")
      .build()

    text.map(item => (item, 1)).addSink(new RedisSink[(String, Int)](conf, new MyRedisMapper()))

    // execute program
    env.execute("WordCount from SocketTextStream Example");
  }

}