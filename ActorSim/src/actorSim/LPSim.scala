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
    case LPInit(lt) => 
      logicalTime=lt 
      sender ! Identify(identifyId)
    case Initialize(myB : scala.collection.mutable.ArrayBuffer[Behavior]) => 
      ActiveBehaviorQ=myB
      log.info("Advanced logical time to "+ logicalTime )
      context.parent.tell(Step,self)
    case Update => 
      stateUpdateStatus=updateState(ActiveBehaviorQ)
      Thread.sleep(100)
      mySimExec ! StateUpdateCompleted(stateUpdateStatus) 
    case LPStep(lt) => 
        logicalTime=lt
        log.info("Advanced logical time to "+ logicalTime )
        context.parent.tell(Step,self)
    case Scan => 
        //log.info("Rescanning local behavior" )
        stateUpdateStatus=updateState(ActiveBehaviorQ)
        Thread.sleep(100)
        mySimExec ! StateUpdateCompleted(stateUpdateStatus)
  }
  
  
  def updateState(bQ:scala.collection.mutable.ArrayBuffer[Behavior]) : Boolean = {
    var aBehavior = bQ.head
    aBehavior.action()
    var r = scala.util.Random
    var myB= r.nextBoolean()
    myB
  }
}