package com.xl.core

import org.apache.spark.SparkConf
import org.apache.spark.sql.SparkSession

object SparkReader {

  def main(args: Array[String]): Unit = {

    val conf = new SparkConf()
    val spark = SparkSession.builder.appName("Test").master("local[1]").getOrCreate()

    testCsv(spark)

  }

  /**
   * 说明：测试csv文件
   *
   * @param spark SparkSession
   */
  def testCsv(spark: SparkSession) = {
    val df = spark.read.csv("D:\\xiongliang\\code\\BigDataDemo\\Spark\\src\\main\\resources\\test.csv")
    df.show();
  }

  /**
   * 说明：测试csv文件
   *
   * @param spark SparkSession
   */
  def testJson(spark: SparkSession): Unit = {

  }

  /**
   * 说明：测试csv文件
   *
   * @param spark SparkSession
   */
  def testParquet(spark: SparkSession): Unit = {

  }

  /**
   * 说明：测试csv文件
   *
   * @param spark SparkSession
   */
  def testHdfs(spark: SparkSession): Unit = {

  }

  /**
   * 说明：测试csv文件
   *
   * @param spark SparkSession
   */
  def testText(spark: SparkSession): Unit = {

  }

  /**
   * 说明：测试csv文件
   *
   * @param spark SparkSession
   */
  def testOrc(spark: SparkSession): Unit = {

  }

}
