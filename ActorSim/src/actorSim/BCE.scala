package actorSim

import akka.actor.{ ActorRef, ActorSystem, Props, Actor, Inbox, ActorLogging, Identify, ActorIdentity }
import scala.concurrent.duration._

class BCE extends Actor with ActorLogging {
  var SimExecutives = scala.collection.mutable.ListBuffer.empty[ActorRef] 
  var simExecCount=0
  def receive = {
    case Add (myAgent)=>      
      myAgent.tell(SetContext(self),self)
      myAgent.tell(Init,ActorRef.noSender)
    case Register => 
      log.info("Received register messagefrom " + sender)
      SimExecutives+=sender
      sender ! Acknowledge
    case EmulatorInit =>
      simExecCount=0
    case RequestAdvance(status) =>
      if (status==false) {
        simExecCount = simExecCount + 1
        if (simExecCount == SimExecutives.size) self ! Advance
      }
      else {
        simExecCount = 0
        self ! ReScan
      }
      
    case Advance =>
        var i=0
        for(i <- 0 to simExecCount-1) {
          SimExecutives(i) ! AdvanceGranted
        }
        simExecCount =0
    case ReScan =>
      var i=0
        for(i <- 0 to simExecCount-1) {
          SimExecutives(i) ! Scan
        }
      
  } 
  
  
}
