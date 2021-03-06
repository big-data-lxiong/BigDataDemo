package io.github.streamingwithflink.chapter2

import java.util.Properties

import org.apache.flink.api.common.serialization.SimpleStringSchema
import org.apache.flink.api.scala.createTypeInformation
import org.apache.flink.streaming.api.functions.ProcessFunction
import org.apache.flink.streaming.api.scala.{DataStream, OutputTag, StreamExecutionEnvironment}
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer
import org.apache.flink.util.Collector
import org.apache.kafka.clients.consumer.ConsumerConfig


object KafkaSourceTest {

  def main(args: Array[String]) {

    /**
     * 第一步：set up the execution environment
      */
    val env = StreamExecutionEnvironment.getExecutionEnvironment

    /**
     * 第二步：添加source源，kafka
     */
    val consumerProps = new Properties
    consumerProps.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "10.0.24.124:9092,10.0.24.127:9092,10.0.24.127:9092")
    consumerProps.setProperty(ConsumerConfig.GROUP_ID_CONFIG, "test")
    consumerProps.setProperty(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "true")
    val consumer = new FlinkKafkaConsumer[String]("tst", new SimpleStringSchema, consumerProps)
    consumer.setCommitOffsetsOnCheckpoints(true)
    consumer.setStartFromLatest

    var text = env.addSource(consumer)

    /**
     * 第三步：数据处理
     */
    lazy val xiong: OutputTag[String] = new OutputTag[String]("xiong")
    lazy val liang: OutputTag[String] = new OutputTag[String]("liang")
    // split up the lines in pairs (2-tuples) containing: (word,1)
    var stream: DataStream[String] = text.flatMap(item => (item.split(" ")))
    var streamSplit = stream.process(new SideOutput())
    var stream1 = streamSplit.getSideOutput(xiong)
    var stream2 = streamSplit.getSideOutput(liang)
    var streamKeyBy = stream.map(item => (item, 1)).keyBy(0)

    /**
     * 第四步：数据sink
     */
    streamKeyBy.print()

    /**
     * 第五
     */
    // execute program
    env.execute("WordCount from SocketTextStream Example");
  }

}

class SideOutput() extends ProcessFunction[String, String] {
  //定义一个侧输出流标签
  lazy val xiong: OutputTag[String] = new OutputTag[String]("xiong")

  override def processElement(value: String,
                              ctx: ProcessFunction[String, String]#Context,
                              out: Collector[String]): Unit = {
    if (value.equalsIgnoreCase("xiong")) {
      ctx.output(xiong, value)
    } else {
      out.collect(value)
    }
  }

}
