package io.github.streamingwithflink.chapter1

import org.apache.flink.api.scala._

object WordCount {
  def main(args: Array[String]) {

    val env: ExecutionEnvironment = ExecutionEnvironment.getExecutionEnvironment
    val text: DataSet[String] = env.fromElements(
      "Who's there?",
      "I think I hear them. Stand, ho! Who's there?")

    val counts = text.flatMap { _.toLowerCase.split("\\W+") filter(_.nonEmpty)}
      .map { (_, 1) }
      .groupBy(0)
      .reduce((a,b) => (a._1, a._2 + b._2))

//      .sum(1)

    counts.print()
  }
}
