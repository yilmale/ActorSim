package actorSim

import akka.actor.{ ActorRef, ActorSystem, Props, Actor, Inbox, ActorLogging, Identify, ActorIdentity }
import scala.concurrent.duration._

class LPSimExecutive (bce: ActorRef) extends Actor with ActorLogging {
  var identifyId = 1

  val myLP = context.actorOf(Props[LPSim], name = "MySimProcess")
  var broadcastEmulator=bce
  

  def receive = {
       
    case Init => 
      log.info("Received intialization from Emulator")
      broadcastEmulator ! Register
    case Acknowledge =>
      log.info("Registration with BCE is successful.. initializing SimProcess")
      myLP ! LPInit   
    case StateUpdated =>
      broadcastEmulator ! RequestAdvance
    case AdvanceGranted =>
       log.info("Sim Executive received advance request and granting it")
       myLP ! GrantAdvance

  }
}