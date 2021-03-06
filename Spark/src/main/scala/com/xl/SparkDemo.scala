package com.xl

import org.apache.spark.SparkConf
import org.apache.spark.sql.SparkSession

object SparkDemo {

  def main(args: Array[String]): Unit = {

    val conf = new SparkConf()
    val spark = SparkSession.builder.appName("Test").master("local[1]").getOrCreate()
    val df = spark.read.csv("D:\\xiongliang\\code\\BigDataDemo\\Spark\\src\\main\\resources\\test.csv")
    df.show();
  }

}
