package actorSim

import akka.actor.{ ActorRef, ActorSystem, Props, Actor, Inbox, ActorLogging, Identify, ActorIdentity }
import scala.concurrent.duration._

case object Init
case class LPInit(vTime: Int)
case class RequestAdvance(status:Boolean)
case object GrantAdvance
case class AdvanceGranted(vTime:Int)
case class Acknowledge(vTime: Int)
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
case class LPStep(vTime:Int)
case object ReScan
case object Scan
case object ScanReq
case object Update
case object OutputProduced 
case class SetScheduler(myContext:ActorRef)
case object Ready 
case object ExecuteStep 
case class XStep(vTime:Int)
case object StepAck


object HelloAkkaScala extends App {

  val system = ActorSystem("ActorSim")

 
  val inbox = Inbox.create(system)

  var myBCE = system.actorOf(Props(new SimScheduler()),"SimulationScheduler")
  
  myBCE.tell(EmulatorInit,ActorRef.noSender)
   
  var i=0
  for(i <- 0 to 2) {
     var myAgentRef = system.actorOf(Props(new SimAgent()), name= "agent-"+i)
     myBCE.tell(Add(myAgentRef),ActorRef.noSender)
   }
  
}