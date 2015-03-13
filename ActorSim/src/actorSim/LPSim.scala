package actorSim

import akka.actor.{ ActorRef, ActorSystem, Props, Actor, Inbox, ActorLogging, Identify, ActorIdentity }
import scala.concurrent.duration._

class LPSim extends Actor with ActorLogging {
  var logicalTime:Int= -1 
  var greeting = ""
  val identifyId = 1
  var mySimExec:ActorRef = null
  var stateUpdateStatus = false
  var ActiveBehaviorQ = scala.collection.mutable.ArrayBuffer.empty[Behavior]
  
  def receive = { 
    case ActorIdentity(identifyId,Some(ref))  =>
      mySimExec = ref  
      log.info("My Simulation Executive is" + mySimExec)
      context.parent.tell(AgentSetUp,self)    
    case LPInit => 
      logicalTime= -1 
      sender ! Identify(identifyId)
    case Initialize(myB : scala.collection.mutable.ArrayBuffer[Behavior]) => 
      logicalTime=logicalTime+1
      log.info("Advanced logical time to "+ logicalTime )
      stateUpdateStatus=updateState(myB)
      if (stateUpdateStatus==true) {
        log.info("LP is sending advance request")
        mySimExec ! StateUpdateCompleted
      }
   
  }
  
  
  def updateState(bQ:scala.collection.mutable.ArrayBuffer[Behavior]) : Boolean = {
    var cycleComplete: Boolean = false
    log.info("Updating state....")
    Thread.sleep(1000)
  /*  while (cycleComplete==false) {
      
    }*/
   
    true

  }
  
}