package actorSim

import akka.actor.{ ActorRef, ActorSystem, Props, Actor, Inbox, ActorLogging, Identify, ActorIdentity }
import scala.concurrent.duration._

case object Init
case object LPInit
case class RequestAdvance(status:Boolean)
case object GrantAdvance
case object AdvanceGranted
case object Acknowledge
case object EmulatorInit
case object Register
case class StateUpdateCompleted(status:Boolean)
case object Advance
case object getID
case class newAID(aID : Int)
case class Add(myAgent:ActorRef)
case class SetContext(myContext:ActorRef)
case class AddBehavior(newBehavior:Behavior)
case object AgentSetUp
case class Initialize(behaviors: scala.collection.mutable.ArrayBuffer[Behavior])
case object Step
case object ReScan
case object Scan

object HelloAkkaScala extends App {

  val system = ActorSystem("ActorSim")

 
  val inbox = Inbox.create(system)

  var myBCE = system.actorOf(Props(new BCE()),"BroadcastEmulator")
  
  myBCE.tell(EmulatorInit,ActorRef.noSender)
   
  var i=0
  for(i <- 0 to 2) {
     var myAgentRef = system.actorOf(Props(new Agent()), name= "agent-"+i)
     myBCE.tell(Add(myAgentRef),ActorRef.noSender)
   }
  
  
}