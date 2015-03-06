package actorSim

import akka.actor.{ ActorRef, ActorSystem, Props, Actor, Inbox, ActorLogging, Identify, ActorIdentity }
import scala.concurrent.duration._

class LPSim extends Actor with ActorLogging {
  var logicalTime=0
  var greeting = ""
  val identifyId = 1
  var mySimExec:ActorRef = null

  def receive = { 
    case ActorIdentity(identifyId,Some(ref))  =>
      mySimExec = ref  
      log.info("My Simulation Executive is" + mySimExec)
      updateState()
      log.info("LP is sending advance request")
      mySimExec ! StateUpdated
    case LPInit => 
      logicalTime=0 
      sender ! Identify(identifyId)
    case GrantAdvance => 
      logicalTime=logicalTime+1
      log.info("Advanced logical time to "+ logicalTime )
      updateState()
      mySimExec ! StateUpdated
   
  }
  
  def updateState() {
    log.info("Updating state....")
    Thread.sleep(1000)

  }
  
}