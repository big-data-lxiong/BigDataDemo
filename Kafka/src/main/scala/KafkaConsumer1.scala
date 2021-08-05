import java.util.Collections
import scala.collection.JavaConversions._

import org.apache.kafka.clients.consumer.KafkaConsumer

class KafkaConsumer1 {

  def consume() = {
    val props = KafkaConsumerConfig.getConfig()
    val consumer = new KafkaConsumer[String, String](props)
    val topics = Collections.singletonList(props.getProperty("topic"))
    consumer.subscribe(topics)

    while (true){
      val records = consumer.poll(100)
      for (record <- records){
        println(record.offset() +"--" +record.key() +"--" +record.value())
      }
      consumer.commitSync()
//      consumer.commitAsync()
    }
    consumer.close()
  }

}
