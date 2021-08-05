import java.io.InputStreamReader
import java.util.Properties

object KafkaConsumerConfig {

  def getConfig(): Properties = {
    val props = load("application-consumer.properties")
    props
  }

  def load(propertieName: String): Properties = {
    val prop = new Properties();
    prop.load(new InputStreamReader(Thread.currentThread().getContextClassLoader.getResourceAsStream(propertieName), "UTF-8"))
    prop
  }

}
