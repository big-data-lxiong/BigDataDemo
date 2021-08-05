
object KafkaTest {

  def main(args: Array[String]): Unit = {
    /**
     * kafka producer测试
     */
    val kafkaProducer1 = new KafkaProducer1()
    kafkaProducer1.send()

    /**
     * kafka consumer测试
     */
    val kafkaConsumer1 = new KafkaConsumer1()
    kafkaConsumer1.consume()
  }

}
