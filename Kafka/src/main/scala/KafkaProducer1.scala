import java.util.Collections

import org.apache.kafka.clients.consumer.KafkaConsumer
import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord, RecordMetadata}

class KafkaProducer1 {

  def send () = {
    val props = KafkaProducerConfig.getConfig()
    val producer = new KafkaProducer[String, String](props)

    while(true){
      for (i <- 0 to 10) {
        var recordMetadata = producer.send(new ProducerRecord(props.getProperty("topic"), "key-" + i, "msg-" + i)).get()
      }
      Thread.sleep(3000)
    }
    producer.close
  }

}
