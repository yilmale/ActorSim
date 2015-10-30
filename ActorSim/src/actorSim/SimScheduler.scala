package actorSim

import akka.actor.{ ActorRef, ActorSystem, Props, Actor, Inbox, ActorLogging, Identify, ActorIdentity }
import scala.concurrent.duration._

class SimScheduler extends Actor with ActorLogging {
  var logicalTime:Int= 0
  var SimExecutives = scala.collection.mutable.ListBuffer.empty[ActorRef] 
  var simExecCount=0
  var response=0
  var next = 0;
  val MAX =5
  def receive = {
    
   case EmulatorInit =>
      simExecCount=0
      logicalTime=0
    case Add (myAgent)=>  
      SimExecutives+=myAgent
      myAgent.tell(SetScheduler(self),self)
      simExecCount=simExecCount+1  
    case ExecuteStep  =>
      if (logicalTime < MAX) {
          next+=1 
          if (next < SimExecutives.size) {
            SimExecutives(next-1) ! XStep(logicalTime)
          }
          else {
            SimExecutives(next-1) ! XStep(logicalTime)
            next = 0
           }
      }
      
    case StepAck =>
      response+=1
      if (response ==  SimExecutives.size) {
           response=0
           logicalTime+=1
           log.info("Logical time is incremented to "+logicalTime)
           self ! ExecuteStep
      }
      else {
        self ! ExecuteStep
      }
      
     case Ready => 
       response+=1
       if (response ==  SimExecutives.size) {
           response=0
           logicalTime+=1
           log.info("Logical time is incremented to "+logicalTime)
           self ! ExecuteStep
      }
   }  
}
  
  
