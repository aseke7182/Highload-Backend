import java.util.concurrent.atomic.AtomicLong

import akka.Done
import akka.actor.{Actor, ActorLogging, ActorSystem, Props}
import akka.kafka._
import akka.kafka.scaladsl.Consumer.DrainingControl
import akka.kafka.scaladsl._
import akka.stream.scaladsl.{Keep, RestartSource, Sink}
import org.apache.kafka.clients.consumer.{ConsumerConfig, ConsumerRecord}
import org.apache.kafka.clients.producer.ProducerRecord
import org.apache.kafka.common.TopicPartition
import org.apache.kafka.common.serialization.{ByteArrayDeserializer, StringDeserializer, StringSerializer}

import scala.collection.immutable
import scala.concurrent.duration._
import scala.concurrent.{Await, Future, Promise}
import org.slf4j.{Logger, LoggerFactory}

import scala.util.{Failure, Success}

object Service3 {
  def number() : List[String] =  {
    implicit val system = ActorSystem("QuickStart")
    implicit val ec = system.dispatcher

    val server = system.settings.config.getString("akka.kafka.producer.kafka-clients.server")
    val consumerConfig = system.settings.config.getConfig("akka.kafka.consumer")
    val consumerSettings =
      ConsumerSettings(consumerConfig, new StringDeserializer, new StringDeserializer)
        .withBootstrapServers(server)
        .withGroupId("group1")
        .withProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest")
    val topic = system.settings.config.getString("akka.kafka.producer.kafka-clients.service1and2.topic")

    var numbers: List[String] = List.empty

    val control =
      Consumer
        .committableSource(consumerSettings, Subscriptions.topics(topic))
        .toMat(Sink.foreach(msg => numbers +:= msg.record.value))(DrainingControl.apply)
        .run()
    Thread.sleep(5000)
    val done = control.drainAndShutdown()

    done.onComplete {
      case Success(value) => {
        println(value)
        system.terminate()
      }
      case Failure(exception) => {
        println(exception)
      }
    }

    for (numb <- numbers) println(numb)
    numbers
  }
}
