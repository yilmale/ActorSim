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
    case Acknowledge(lt) =>
      log.info("Registration with BCE is successful.. initializing SimProcess")
      myLP ! LPInit(lt)   
    case StateUpdateCompleted(status) =>
      if (status==false) {broadcastEmulator ! RequestAdvance(status)}
      else {broadcastEmulator ! OutputProduced}
    case AdvanceGranted(lt) =>
       //log.info("Sim Executive received advance request and granting it")
       myLP ! LPStep(lt)
    case ScanReq =>
      myLP ! Scan
  }
}