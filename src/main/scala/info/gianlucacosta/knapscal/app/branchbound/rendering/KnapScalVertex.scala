package info.gianlucacosta.knapscal.app.branchbound.rendering

import info.gianlucacosta.eighthbridge.fx.graph.{LabeledVisualVertex, VisualVertex}

import info.gianlucacosta.knapscal.knapsack.branchbound.Node
import scalafx.geometry.Point2D


object KnapScalVertex {
  private def getLabel(node: Node): String =
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

  private def getStyleClasses(node: Node): Set[String] =
    if (node.isSolution)
      Set("solution")
    else if (node.isStopped)
      Set("stopped")
    else
      Set()

  def create(node: Node): KnapScalVertex =
    new KnapScalVertex(node)
      .setStyleClasses(
        getStyleClasses(node)
      )
}

class KnapScalVertex private (
                           val node: Node
                         ) extends LabeledVisualVertex(
    new Point2D(50, 50),
    KnapScalVertex.getLabel(node)
)