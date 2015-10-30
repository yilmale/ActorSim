package schellingModel

/**
 * @author yilmaz
 */
trait XPoint {
  def isSimilar(x: Any): Boolean
  def isNotSimilar(x: Any): Boolean = !isSimilar(x)
}