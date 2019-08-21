package kafka.practice1.base

import java.util.Properties

import org.apache.kafka.clients.producer.{KafkaProducer, ProducerConfig, ProducerRecord}
import org.apache.kafka.common.serialization.StringSerializer

object Producer {
  def main(args: Array[String]): Unit = {
    // create producer properties
    val bootstrapServer = "localhost:9092"
    val topic = "first"

    val properties = new Properties()
    properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServer)
    properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, classOf[StringSerializer].getName)
    properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, classOf[StringSerializer].getName)

    // create producer
    val producer = new KafkaProducer[String, String](properties)

    // create record
    val record = new ProducerRecord[String, String](topic, "test1")

    // send data
    producer.send(record)

    // flash data
    producer.flush()

    // close producer
    producer.close()
  }
}