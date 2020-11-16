import akka.Done

import scala.concurrent.Future
import akka.actor.ActorSystem
import akka.kafka.ProducerSettings
import akka.kafka.scaladsl.Producer
import akka.stream.scaladsl.Source
import org.apache.kafka.clients.producer.ProducerRecord
import org.apache.kafka.common.serialization.StringSerializer

import scala.util.{Failure, Random, Success}
import org.slf4j.{Logger, LoggerFactory}

import scala.concurrent.duration.DurationInt

object Service0 extends App {
  implicit val log: Logger = LoggerFactory.getLogger(getClass)
  implicit val system = ActorSystem("QuickStart")
  implicit val ec = system.dispatcher

  val config = system.settings.config.getConfig("akka.kafka.producer")
  val server = system.settings.config.getString("akka.kafka.producer.kafka-clients.server")
  val topic = system.settings.config.getString("akka.kafka.producer.kafka-clients.service0.topic")
  val producerSettings = ProducerSettings(config, new StringSerializer, new StringSerializer).withBootstrapServers(server)

  val done: Future[Done] = {
    Source(0 to scala.util.Random.nextInt(10))
      .map(_.toString)
      .map(value => new ProducerRecord[String, String](topic, value))
      .runWith(Producer.plainSink(producerSettings))
  }

  done.onComplete {
    case Success(value) => {
      println(value)
      system.terminate()
    }
    case Failure(exception) => {
      println(exception)
    }
  }


}
