package actorSim

import akka.actor.{ ActorRef, ActorSystem, Props, Actor, Inbox, ActorLogging, Identify, ActorIdentity }
import scala.concurrent.duration._

class BCE extends Actor with ActorLogging {
  var SimExecutives = scala.collection.mutable.ListBuffer.empty[ActorRef] 
  var simExecCount=0
  def receive = {
    case Register => 
      log.info("Received register messagefrom " + sender)
      SimExecutives+=sender
      sender ! Acknowledge
    case EmulatorInit =>
      simExecCount=0
    case RequestAdvance =>
      simExecCount = simExecCount + 1
      if (simExecCount == SimExecutives.size) self ! Advance
    case Advance =>
        var i=0
        for(i <- 0 to simExecCount-1) {
          SimExecutives(i) ! AdvanceGranted
        }
        simExecCount =0
       
  } 
  
}
