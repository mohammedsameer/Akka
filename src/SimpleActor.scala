import akka.actor.{Props, ActorSystem, Actor}

/**
 * Simple actor
 */
object SimpleActor extends App {
  class Reader extends Actor {
    override def receive = {
      case n: Number => println("Number:" + n)
      case s: String => println("String:" + s)
    }
  }

  val system = ActorSystem("SimpleActorSystem")
  val myActor1 = system.actorOf(Props[Reader], "MyActor1")
  val myActor2 = system.actorOf(Props[Reader], "MyActor2")
  myActor1 ! 10
  myActor1 ! "Sameer"
  myActor2 ! 20
  myActor2 ! "Samrah"
  system.shutdown()
}