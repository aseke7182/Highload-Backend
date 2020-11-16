import java.util.concurrent.atomic.{AtomicLong, AtomicReference}

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

object Service1 extends App {
  implicit val log: Logger = LoggerFactory.getLogger(getClass)
  implicit val system = ActorSystem("QuickStart")
  implicit val ec = system.dispatcher

  val server = system.settings.config.getString("akka.kafka.producer.kafka-clients.server")

  val consumerConfig = system.settings.config.getConfig("akka.kafka.consumer")
  val topicNew = system.settings.config.getString("akka.kafka.producer.kafka-clients.service1and2.topic")
  val consumerSettings =
    ConsumerSettings(consumerConfig, new StringDeserializer, new StringDeserializer)
      .withBootstrapServers(server)
      .withGroupId("group1")
      .withProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest")

  val producerConfig = system.settings.config.getConfig("akka.kafka.producer")
  val topicOld = system.settings.config.getString("akka.kafka.producer.kafka-clients.service0.topic")
  val producerSettings =
    ProducerSettings(producerConfig, new StringSerializer, new StringSerializer)
      .withBootstrapServers(server)

  val committerConfig = system.settings.config.getConfig("akka.kafka.committer")
  val committerSettings = CommitterSettings(committerConfig)

  val control =
    Consumer
      .committableSource(consumerSettings, Subscriptions.topics(topicOld))
      .map { msg =>
        ProducerMessage.single(
          new ProducerRecord(topicNew, msg.record.key, msg.record.value + "0"),
          msg.committableOffset
        )
      }
      .toMat(Producer.committableSink(producerSettings, committerSettings))(DrainingControl.apply)
      .run()


  Thread.sleep(10000)

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


}
