package schellingModel

import actorSim._
import akka.actor.{ ActorRef, ActorSystem, Props, Actor, Inbox, ActorLogging, Identify, ActorIdentity }


object Schelling extends App {
  val system = ActorSystem("ActorSim")
  val inbox = Inbox.create(system)
  
  var myBCE = system.actorOf(Props[BCE],"BroadcastEmulator")
  
  myBCE.tell(EmulatorInit,ActorRef.noSender)
   
  var i=0
  for(i <- 0 to 2) {
     var lpSimExec = system.actorOf(Props(new LPSimExecutive(myBCE)), name= "lpex"+i)
     lpSimExec.tell(Init,ActorRef.noSender)
   }
}