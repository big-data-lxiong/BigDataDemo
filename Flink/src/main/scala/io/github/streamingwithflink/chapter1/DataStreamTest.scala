package io.github.streamingwithflink.chapter1

import org.apache.flink.streaming.api.datastream.DataStream
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment

object DataStreamTest {

  def main(args: Array[String]): Unit = {
    val env = StreamExecutionEnvironment.getExecutionEnvironment()
    val text = env.readTextFile("D:\\xiongliang\\code\\BigDataDemo\\Flink\\src\\main\\resources\\test.csv").map(_.split(""))

    text.print()

    // execute program
    env.execute("WordCount from SocketTextStream Example");
  }

}
