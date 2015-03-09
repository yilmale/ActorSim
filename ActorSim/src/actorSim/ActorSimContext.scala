package actorSim

import akka.actor.{ ActorRef, ActorSystem, Props, Actor, Inbox, ActorLogging, Identify, ActorIdentity }
import scala.concurrent.duration._

case object Init
case object LPInit
case object RequestAdvance
case object GrantAdvance
case object AdvanceGranted
case object Acknowledge
case object EmulatorInit
case object Register
case object StateUpdated
case object Advance
case object getID
case class newAID(aID : Int)


object HelloAkkaScala extends App {

  val system = ActorSystem("ActorSim")

 
  val inbox = Inbox.create(system)
  
  var idGen = system.actorOf(Props[AIDGenerator],"AIDGenerator")
  
  var myBCE = system.actorOf(Props[BCE],"BroadcastEmulator")
  
  myBCE.tell(EmulatorInit,ActorRef.noSender)
   
  var i=0
  for(i <- 0 to 2) {
     var lpSimExec = system.actorOf(Props(new LPSimExecutive(myBCE)), name= "lpex"+i)
     lpSimExec.tell(Init,ActorRef.noSender)
   }
  
  
}