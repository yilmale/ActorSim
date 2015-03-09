package actorSim

import akka.actor.{ ActorRef, ActorSystem, Props, Actor, Inbox, ActorLogging, Identify, ActorIdentity }

object SimExecInstantiator {
  def props (bce: ActorRef) : Props = Props(new LPSimExecutive(bce))
}

class Agent extends Actor with ActorLogging {
  var AID : Int = 0
  var agentSimExecutive : ActorRef = null;
  var agentContext : BCE = null
  
  
  def takeDown() {}
  
  def setContext(myContext : BCE) {
    agentContext=myContext
    agentSimExecutive = context.actorOf(Props(new LPSimExecutive(myContext.self)), name= "lpex-"+AID) 
   
  }
  
}