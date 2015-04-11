package actorSim

import akka.actor.{ ActorRef, ActorSystem, Props, Actor, Inbox, ActorLogging, Identify, ActorIdentity }
import scala.concurrent.duration._

class BCE extends Actor with ActorLogging {
  var logicalTime:Int= 0
  var SimExecutives = scala.collection.mutable.ListBuffer.empty[ActorRef] 
  var simExecCount=0
  def receive = {
    case Add (myAgent)=>      
      myAgent.tell(SetContext(self),self)
      myAgent.tell(Init,ActorRef.noSender)
    case Register => 
      log.info("Received register messagefrom " + sender)
      SimExecutives+=sender
      sender ! Acknowledge(logicalTime)
    case EmulatorInit =>
      simExecCount=0
    case RequestAdvance(status) =>
      simExecCount = simExecCount + 1
      if (simExecCount == SimExecutives.size) self ! Advance
    case OutputProduced =>
      simExecCount=0
      self ! ReScan
    case Advance =>
        logicalTime=logicalTime+1
        simExecCount =0
        var i=0
        for(i <- 0 to SimExecutives.size-1) {
          SimExecutives(i) ! AdvanceGranted(logicalTime)
        }
        
    case ReScan =>
      var i=0
        for(i <- 0 to SimExecutives.size-1) {
          SimExecutives(i) ! ScanReq
        }
      
  } 
  
  
}
