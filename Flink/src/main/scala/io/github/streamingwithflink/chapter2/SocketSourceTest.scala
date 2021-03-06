package io.github.streamingwithflink.chapter2

import java.util.Properties

import org.apache.flink.api.common.serialization.SimpleStringSchema
import org.apache.flink.streaming.api.functions.AssignerWithPeriodicWatermarks
import org.apache.flink.streaming.api.scala.StreamExecutionEnvironment
import org.apache.flink.streaming.api.scala._
import org.apache.flink.streaming.api.watermark.Watermark
import org.apache.flink.streaming.api.windowing.assigners.TumblingEventTimeWindows
import org.apache.flink.streaming.api.windowing.time.Time
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer
import org.apache.kafka.clients.consumer.ConsumerConfig


object SocketSourceTest {

  def main(args: Array[String]) {

    // set up the execution environment
    val env = StreamExecutionEnvironment.getExecutionEnvironment;

    /**
      * 通过netcat获取数据
      */
//    var text =
//      env.socketTextStream("10.0.24.59", 9999, '\n', 0);

    /**
      * 获取hdfs的数据
      */
    var hdfsFile = env.readTextFile("hdfs://10.0.24.124:8020/test1.txt");

    val consumerProps = new Properties
    consumerProps.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "10.0.24.124:9092,10.0.24.127:9092,10.0.24.127:9092")
    consumerProps.setProperty(ConsumerConfig.GROUP_ID_CONFIG, "test")
    consumerProps.setProperty(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "true")
    val consumer = new FlinkKafkaConsumer[String]("tst", new SimpleStringSchema, consumerProps)
    consumer.setCommitOffsetsOnCheckpoints(true)
    consumer.setStartFromLatest
    /**
      *获取kafka中的数据
      */
    var text = env.addSource(consumer)

    hdfsFile.map(item => (item, 1)).keyBy(0).window(TumblingEventTimeWindows.of(Time.seconds(3)))

    var stream =
      // split up the lines in pairs (2-tuples) containing: (word,1)
      text.flatMap(item => item.split(" "));

//    var streamSplit = stream.split(
//      word => (
//        "xiong".equals(word) match {
//          case true => List("xiong")
//          case false => List("other")
//        }
//      )
//    )
//
//    var stream1 = streamSplit.select("xiong");
//    var stream2 = streamSplit.select("other");

    var streamKeyBy = stream.map(item => (item, 1)).keyBy(0)
    streamKeyBy.print()

    // execute program
    env.execute("WordCount from SocketTextStream Example");
  }

}
