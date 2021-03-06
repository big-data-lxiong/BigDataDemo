package io.github.streamingwithflink.chapter2

import org.apache.flink.streaming.api.functions.AssignerWithPeriodicWatermarks
import org.apache.flink.streaming.api.watermark.Watermark

class MyWaterMarks extends AssignerWithPeriodicWatermarks[Tuple3[String, Long, Integer]] {

  private var currentMaxTimestamp = 0l
  private val maxOutOfOrderness = 10000l //这个控制失序已经延迟的度量


  //获取EventTime
  override def extractTimestamp(element: Tuple3[String, Long, Integer], previousElementTimestamp: Long): Long = {
    val timestamp = element._2
    currentMaxTimestamp = Math.max(timestamp, currentMaxTimestamp)
    System.out.println("get timestamp is " + timestamp + " currentMaxTimestamp " + currentMaxTimestamp)
    timestamp
  } //获取Watermark

  override def getCurrentWatermark: Watermark = {
    System.out.println("wall clock is " + System.currentTimeMillis + " new watermark " + (currentMaxTimestamp - maxOutOfOrderness))
    new Watermark(currentMaxTimestamp - maxOutOfOrderness)
  }

}
