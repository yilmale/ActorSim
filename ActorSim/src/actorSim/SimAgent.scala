package actorSim

import akka.actor.{ ActorRef, ActorSystem, Props, Actor, Inbox, ActorLogging, Identify, ActorIdentity }


class SimAgent extends Actor with ActorLogging {
  var AID : Int = 0
  var agentSimExecutive : ActorRef = null;
  var agentScheduler : ActorRef = null
  var myLP : ActorRef = null
  var BehaviorQ = scala.collection.mutable.ArrayBuffer.empty[Behavior]
    
  def takeDown() {}
  
  def setUp() {
    var myC = new Condition()
    var myT = new Behavior()
    var r = new Rule(myC, myT)
    addBehavior(r)
  }
  
  def step() {
     log.info("Agent step is complete ")
  }
  
  
  def addBehavior(b: Behavior) {
    BehaviorQ+=b
  }
  
  def receive = {     
    
    case SetScheduler(scheduler) =>
       agentScheduler = scheduler
       setUp()
       log.info("Agent setup is complete ")
       agentScheduler ! Ready
    
    case XStep(lt) =>
        step()
        agentScheduler ! StepAck
        

  }
  
}