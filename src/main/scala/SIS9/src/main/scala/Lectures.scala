import akka.NotUsed
import akka.actor.ActorSystem
import akka.stream.ClosedShape
import akka.stream.scaladsl.GraphDSL.Implicits.{SourceArrow, port2flow}
import akka.stream.scaladsl.{Broadcast, Flow, GraphDSL, Keep, RunnableGraph, Sink, Source}

import scala.concurrent.Future
import scala.util.{Failure, Success}

object Lectures {

  implicit val system = ActorSystem("QuickStart")
  implicit val ec = system.dispatcher

  def f1() = {

    val source = Source(1 to 10)
    val sink = Sink.fold[Int, Int](0)(_ + _)

    // connect the Source to the Sink, obtaining a RunnableGraph
    val runnable: RunnableGraph[Future[Int]] = source.toMat(sink)(Keep.right)

    // materialize the flow and get the value of the FoldSink
    val sum: Future[Int] = runnable.run()


    sum.onComplete {
      case Success(value) => {
        println(value)
        system.terminate()
      }
      case Failure(exception) => {
        println(exception)
      }
    }
  }

  def f2() = {

    val source = Source(1 to 10)
    val sink = Sink.fold[Int, Int](0)(_ + _)

    // materialize the flow, getting the Sinks materialized value
    val sum: Future[Int] = source.runWith(sink)

    sum.onComplete {
      case Success(value) => {
        println(value)
        system.terminate()
      }
      case Failure(exception) => {
        println(exception)
      }
    }
  }

  def f3() = {
    // connect the Source to the Sink, obtaining a RunnableGraph
    val sink = Sink.fold[Int, Int](0)(_ + _)
    val runnable: RunnableGraph[Future[Int]] =
      Source(1 to 10).toMat(sink)(Keep.right)

    // get the materialized value of the FoldSink
    val sum1: Future[Int] = runnable.run()
    val sum2: Future[Int] = runnable.run()

    sum1.onComplete {
      case Success(value) => {
        println(value)
        system.terminate()
      }
      case Failure(exception) => {
        println(exception)
      }
    }

    sum2.onComplete {
      case Success(value) => {
        println(value)
        system.terminate()
      }
      case Failure(exception) => {
        println(exception)
      }
    }
  }

  def f4() = {
    Source(1 to 6).via(Flow[Int].map(_ * 2)).to(Sink.foreach(println(_))).run()
  }

  def f5() = {
    // Broadcast to a sink inline
    val otherSink: Sink[Int, NotUsed] = Flow[Int].alsoTo(Sink.foreach(println(_))).to(Sink.ignore)
    Source(1 to 6).to(otherSink).run()
  }

  def f6() = {
    val source: Source[Int, NotUsed] = Source(1 to 20)

    def evenFlow: Flow[Int, Int, NotUsed] = Flow[Int].filter(_ % 2 == 0)

    def oddFlow: Flow[Int, Int, NotUsed] = Flow[Int].filter(_ % 2 == 1)

    val evenSink = Sink.foreach[Int](x => print(x + "e "))
    val oddSink = Sink.foreach[Int](x => print(x + "o "))

    val graph = GraphDSL.create() { implicit builder =>

      val bcast = builder.add(Broadcast[Int](2))

      source ~> bcast.in
      bcast.out(0) ~> evenFlow ~> evenSink
      bcast.out(1) ~> oddFlow ~> oddSink

      ClosedShape
    }

    RunnableGraph.fromGraph(graph).run
  }

}
