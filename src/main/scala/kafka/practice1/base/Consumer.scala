package kafka.practice1.base

import java.time.Duration
import java.util.Properties

import org.apache.kafka.clients.consumer.{ConsumerConfig, ConsumerRecord, ConsumerRecords, KafkaConsumer}
import org.apache.kafka.common.serialization.StringDeserializer
import org.slf4j.{Logger, LoggerFactory}

import scala.collection.JavaConverters._

object Consumer {
  val logger: Logger = LoggerFactory.getLogger("Consumer")

  def main(args: Array[String]): Unit = {

    // create consumer property
    val property = new Properties()
    property.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092")
    property.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, classOf[StringDeserializer].getName)
    property.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, classOf[StringDeserializer].getName)
    property.setProperty(ConsumerConfig.GROUP_ID_CONFIG, "first_group")
    property.setProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest")

    // create consumer
    val consumer = new KafkaConsumer[String, String](property)

    // subscribe consumer to topic
    consumer.subscribe(List("first").asJava)

    while (true) {
      val records: ConsumerRecords[String, String] = consumer.poll(Duration.ofMillis(100))

      for (record <- records.asScala) {
        println(record.value())
      }
    }
  }
}
