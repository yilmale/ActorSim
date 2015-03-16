package actorSim

import akka.actor.{ ActorRef, ActorSystem, Props, Actor, Inbox, ActorLogging, Identify, ActorIdentity }

object SimExecInstantiator {
  def props (bce: ActorRef, lp: ActorRef) : Props = Props(new LPSimExecutive(bce,lp))
  
}

object SimLPInstantiator {
  def props () : Props = Props(new LPSim())
  
}

class Agent extends Actor with ActorLogging {
  var AID : Int = 0
  var agentSimExecutive : ActorRef = null;
  var agentContext : ActorRef = null
  var myLP : ActorRef = null
  var BehaviorQ = scala.collection.mutable.ArrayBuffer.empty[Behavior]
  setUp()
  
  def takeDown() {}
  
  def setUp() {
    var myC = new Condition()
    var myT = new Behavior()
    var r = new Rule(myC, myT)
    addBehavior(r)
  }
  
  def addBehavior(b: Behavior) {
    BehaviorQ+=b
  }
  
  def receive = {     
    
    case SetContext(myBCE) =>
       agentContext = myBCE
       myLP = context.actorOf(SimLPInstantiator.props(), name= "lp-"+AID)
       agentSimExecutive = context.actorOf(SimExecInstantiator.props(myBCE,myLP), name= "lpex-"+AID) 
    case Init => 
       agentSimExecutive ! Init
    case AgentSetUp =>
        setUp()
        log.info("Agent setup is complete ")
        myLP.tell(Initialize(BehaviorQ),self)
  }
  
}