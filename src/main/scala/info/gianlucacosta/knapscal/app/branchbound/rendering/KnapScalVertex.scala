package info.gianlucacosta.knapscal.app.branchbound.rendering

import java.util.UUID

import info.gianlucacosta.eighthbridge.fx.canvas.basic.BasicVertex
import info.gianlucacosta.knapscal.knapsack.branchbound.Node

import scalafx.geometry.Point2D


object KnapScalVertex {
  def formatNode(node: Node): String =
    if (node.isSolution) {
      (
        s"Index = ${node.index}\n"
          + s"*SOLUTION*\n"
          + s"z = ${node.totalProfit}\n"
          + s"I = ${node.takenItems.mkString("[", ",\n", "]")}"
        )
    } else {
      (s"Index = ${node.index}\n"
        + s"${if (node.isUpperBoundComputed) "Ū" else "U"} = ${node.upperBound}\n"
        + s"P = ${node.totalProfit};  W = ${node.totalWeight}"
        + s"${if (node.isStopped) "\n*STOP*" else ""}"
        )
    }
}

case class KnapScalVertex(
                           node: Node,
                           center: Point2D,
                           selected: Boolean = false,

                           id: UUID = UUID.randomUUID()
                         ) extends BasicVertex[KnapScalVertex] {
  override def text: String =
    KnapScalVertex.formatNode(node)


  override def styleClasses: List[String] =
    if (node.isSolution)
      List("solution")
    else if (node.isStopped)
      List("stopped")
    else
      List()


  override def visualCopy(center: Point2D, selected: Boolean): KnapScalVertex =
    copy(center = center, selected = selected)
}