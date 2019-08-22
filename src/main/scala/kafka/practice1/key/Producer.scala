package kafka.practice1.key

import java.util.Properties

import org.apache.kafka.clients.producer._
import org.apache.kafka.common.serialization.StringSerializer
import org.slf4j.{Logger, LoggerFactory}

object Producer {
  def main(args: Array[String]): Unit = {
    val bootstrapServer = "localhost:9092"
    val topic = "first"
    val logger: Logger = LoggerFactory.getLogger(Producer.getClass)

    // create producer properties
    val properties = new Properties()
    properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServer)
    properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, classOf[StringSerializer].getName)
    properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, classOf[StringSerializer].getName)

    // create producer
    val producer = new KafkaProducer[String, String](properties)

    for (i <- 1 to 10) {
      // create record
      val key = "id_" + i
      val message = "message_" + i
      val record = new ProducerRecord[String, String](topic, key, message)

      // send data
      producer.send(record, new Callback {
        override def onCompletion(metadata: RecordMetadata, exception: Exception): Unit = {
          if (exception == null) {
            logger.info("Received new metadata. \n" +
              "Topic: " + metadata.topic() + "\n" +
              "Offset: " + metadata.offset() + "\n" +
              "Partition: " + metadata.partition()
            )
          } else {
            logger.error("Error with producing", exception)
          }
        }
      }).get()
    }

    // flash data
    producer.flush()

    // close producer
    producer.close()
  }
}