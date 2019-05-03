package kafka.practice1

object ThreadTest {
  def main(args: Array[String]): Unit = {

    for (i <- 1 to 100) {
      val thread = new Thread {
        override def run: Unit = {
          println(i)
        }
      }

      thread.start()
      Thread.sleep(50)
    }
  }
}
