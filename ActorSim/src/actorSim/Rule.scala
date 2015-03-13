package actorSim

class Rule (condition : Condition, task: Behavior) extends Behavior {
  var guardCondition = condition
  var ruleAction = task
  
  def checkCondition() : Boolean = {
    if (condition.isSatisfied()) true else false
  }
  
  override def action() {
    task.action()
  }
  
}