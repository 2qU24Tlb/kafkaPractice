package kafka.practice1

import java.time.Duration
import java.util.Properties
import java.util.concurrent.CountDownLatch

import org.apache.kafka.clients.consumer.{ConsumerConfig, KafkaConsumer}
import org.apache.kafka.common.serialization.StringDeserializer
import org.slf4j.LoggerFactory

import scala.collection.JavaConverters._

object Consumer1 {
  val logger = LoggerFactory.getLogger("Consumer1")

  def main(args: Array[String]): Unit = {
    print("consumer 1")

    val latch = new CountDownLatch(1)
    val consumerRunnable = new ConsumerRunnable("localhost:9092", "first", "first")
    val myThread = new Thread(consumerRunnable)

    myThread.start()

//    try {
//      latch.await()
//    } catch {
//      case e: InterruptedException => e.printStackTrace()
//    } finally {
//      logger.info("closing")
//    }
  }

  class ConsumerRunnable(val bootServer: String,
                         val topic: String,
                         val group: String) extends Runnable {

    // create consumer property
    val property = new Properties()
    property.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootServer)
    property.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, classOf[StringDeserializer].getName)
    property.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, classOf[StringDeserializer].getName)
    property.setProperty(ConsumerConfig.GROUP_ID_CONFIG, group)
    property.setProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest")

    // create consumer
    val consumer = new KafkaConsumer[String, String](property)

    // subscribe consumer to topic
    consumer.subscribe(List(topic).asJava)

    override def run(): Unit = {
      // poll for new data
      while (true) {
        val records = consumer.poll(Duration.ofMillis(100))

        for (record <- records.asScala) {
          logger.info("key" + record.key() + ", Value: " + record.value())
          logger.info("Partition: " + record.partition() + ", Offset:" + record.offset())
        }
      }
    }

    def shutdown(): Unit = {
      consumer.wakeup()
    }
  }

}
