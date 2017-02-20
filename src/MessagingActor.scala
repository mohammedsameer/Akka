import akka.actor.{Props, ActorSystem, ActorRef, Actor}

/**
 * Communicating actor
 */
object MessagingActor extends App {
  case object CoffeeRequest
  case object SnackRequest
  case object CloseRequest
  case object CaffeineRequest
  case object FoodRequest
  case class Bill(d : Int)

  class Barista extends Actor {
    override def receive = {
      case CoffeeRequest => {
        sender ! Bill(1)
        println("Preparing coffee")
      }
      case SnackRequest => {
        sender ! Bill(2)
        println("Preparing snack")
      }
      case CloseRequest => {
        println("Closing shop, see you tomorrow!")
        context.system.shutdown()
      }
    }
  }

  class Customer(coffeeShop : ActorRef) extends Actor {
    override def receive = {
      case Bill(d: Int) => println("Ok, I owe " + d + " dollar(s)")
      case CaffeineRequest => coffeeShop ! CoffeeRequest
      case FoodRequest => coffeeShop ! SnackRequest
    }
  }

  val system = ActorSystem("CoffeeShop")
  val barista = system.actorOf(Props[Barista], "Barista")
  val customer = system.actorOf(Props(classOf[Customer], barista), "Customer")
  customer ! CaffeineRequest
  customer ! FoodRequest
  barista ! CloseRequest
}
