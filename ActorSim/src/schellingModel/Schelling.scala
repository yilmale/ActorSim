package schellingModel

import actorSim._
import akka.actor.{ ActorRef, ActorSystem, Props, Actor, Inbox, ActorLogging, Identify, ActorIdentity }

class Experiment (a: Int) {
  var x:Int=0 
  var z: Int =0
  var str: String="test1"
  def action(i:Int) {var y=i; y=y+1; println(y); z=y }
  
  def Optimizer() : String = {"test"}
  def done(F:Int => Int) : Int = {
    F(5)
  }
  
  def myF (myX: Int): Int = {var c=myX; c}
  
  def withResults(Z:Int => Int): Int=>Int  = {v => Z(v)*Z(v)}
}

object ExperimentModel {
  def execute(e:Experiment) {e.action(5)}

}

object Schelling extends App {
  var A=5
  ExperimentModel.execute {
  new Experiment (A) {
    x=A
    action(x)
    done {A => A+1}
    println(withResults {x => {x+1}} {4})
    str=Optimizer()
    println(str)
  }}
  
}