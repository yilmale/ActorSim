package actorSim

import akka.actor.{ ActorRef, ActorSystem, Props, Actor, Inbox, ActorLogging, Identify, ActorIdentity }

class AIDGenerator extends Actor with ActorLogging {
  var id = 0
  
  def receive = { 
    case getAgentID => 
      id = id + 1
      sender ! newAID(id)
  }
}