package actorSim

import akka.actor.{ ActorRef, ActorSystem, Props, Actor, Inbox, ActorLogging, Identify, ActorIdentity }
import scala.concurrent.duration._

class LPSimExecutive (bce: ActorRef, mLP: ActorRef) extends Actor with ActorLogging {
  var identifyId = 1
  var broadcastEmulator=bce
  var myLP = mLP

  def receive = {     
    case Init => 
      log.info("Received intialization from Emulator")
      broadcastEmulator ! Register
    case Acknowledge =>
      log.info("Registration with BCE is successful.. initializing SimProcess")
      myLP ! LPInit   
    case StateUpdateCompleted(status) =>
      broadcastEmulator ! RequestAdvance(status)
    case AdvanceGranted =>
       log.info("Sim Executive received advance request and granting it")
       myLP ! Step
    case Scan =>
      myLP ! Scan
  }
}