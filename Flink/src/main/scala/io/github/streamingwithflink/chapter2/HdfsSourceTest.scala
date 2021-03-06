package io.github.streamingwithflink.chapter2

import java.util.Properties

import org.apache.flink.api.common.serialization.SimpleStringSchema
import org.apache.flink.streaming.api.scala.{StreamExecutionEnvironment, _}
import org.apache.flink.streaming.api.windowing.assigners.TumblingEventTimeWindows
import org.apache.flink.streaming.api.windowing.time.Time
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer
import org.apache.kafka.clients.consumer.ConsumerConfig


object HdfsSourceTest {

  def main(args: Array[String]) {

    // set up the execution environment
    val env = StreamExecutionEnvironment.getExecutionEnvironment;

    /**
      * 获取hdfs的数据
      */
    var hdfsFile = env.readTextFile("hdfs://10.0.24.124:8020/test1.txt");

    hdfsFile.print()

    // execute program
    env.execute("WordCount from SocketTextStream Example");
  }

}
