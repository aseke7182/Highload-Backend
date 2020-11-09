package SIS7

import java.io.File

import akka.actor.typed.ActorSystem
import akka.actor.typed.scaladsl.Behaviors
import akka.http.scaladsl.Http
import akka.http.scaladsl.model._
import akka.http.scaladsl.model.Multipart.BodyPart
import akka.http.scaladsl.server.Directives._
import akka.stream.scaladsl.FileIO
import org.slf4j.{Logger, LoggerFactory}

import scala.concurrent.Future
import scala.concurrent.duration.DurationInt
import scala.io.StdIn


object Lab extends App {
  implicit val log: Logger = LoggerFactory.getLogger(getClass)

  case class Video(file: String, title: String, date: DateTime)

  object db {
    private var files: Seq[String] = Seq.empty

    def create(video: Video): Future[Unit] = {
      files :+= video.title + video.date
      Future.successful(())
    }

    def get(): String = {
      var file = ""
      var ans = ""
      for (file <- files) {
        ans += file + "\n"
      }
      ans
    }
  }

  implicit val system = ActorSystem(Behaviors.empty, "my-system")
  implicit val executionContext = system.executionContext

  val uploadVideo =
    concat(
      path("all") {
        get {
          complete(HttpEntity(ContentTypes.`text/html(UTF-8)`, db.get()))
        }
      },
      path("video") {
        entity(as[Multipart.FormData]) { formData =>

          // collect all parts of the multipart as it arrives into a map
          val allPartsF: Future[Map[String, Any]] = formData.parts.mapAsync[(String, Any)](1) {

            case b: BodyPart if b.name == "file" =>
              // stream into a file as the chunks of it arrives and return a future
              // file to where it got stored
              val file = File.createTempFile("upload", "tmp")
              b.entity.dataBytes.runWith(FileIO.toPath(file.toPath)).map(_ =>
                (b.name -> file))


            case b: BodyPart if b.name == "upload" =>
              // collect form field values
              db.create(Video(b.name,b.toString, DateTime.now))
              b.toStrict(2.seconds).map(strict =>
                (b.name -> strict.entity.data.utf8String))

            case b: BodyPart =>
              // collect form field values
              b.toStrict(2.seconds).map(strict =>
                (b.name -> strict.entity.data.utf8String))

          }.runFold(Map.empty[String, Any])((map, tuple) => map + tuple)
          // when processing have finished create a response for the user
          onSuccess(allPartsF) { allParts =>
            complete {
              "ok!"
            }
          }
        }
      }
    )

  val bindingFuture = Http().newServerAt("localhost", 8080).bind(uploadVideo)
  log.info("Server online at http://localhost:8080/\nPress RETURN to stop...")
  StdIn.readLine()
  bindingFuture.flatMap(_.unbind()).onComplete(_ => system.terminate())

}
