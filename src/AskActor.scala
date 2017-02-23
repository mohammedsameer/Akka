import akka.actor.{Props, ActorSystem, Actor}
import akka.pattern._
import akka.util.Timeout
import scala.concurrent.Future
import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global

/**
 * msameer on 2/22/17.
 */
object AskActor extends App {
  case class NameResponse(first: String, last: String)
  case object NameRequest

  class Hello(first: String, last: String) extends Actor {
    override def receive  = {
      case NameRequest => sender ! NameResponse(first, last)
    }
  }

  val system = ActorSystem("AskSystem")
  val hello = system.actorOf(Props(new Hello("Mohammed", "Sameer")), "Hello")

  implicit val timeout = Timeout(1 second)
  val response: Future[Any] = hello ? NameRequest

  response.foreach(name => name match {
    case NameResponse(first, last) => {
      println("First Name: " + first)
      println("Last Name: " + last)
    }
  })

  //system.shutdown()
}
