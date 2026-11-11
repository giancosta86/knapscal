package info.gianlucacosta.knapscal.app.branchbound.rendering

import info.gianlucacosta.eighthbridge.fx.{AdjustingGraphCanvas, VertexNode}
import info.gianlucacosta.eighthbridge.fx.graph.{VisualGraph, VisualVertex}
import scalafx.geometry.Point2D
import scalafx.Includes._

class KnapScalGraphCanvas extends AdjustingGraphCanvas {
  private val VerticalGap =
    60

  private val MinHorizontalGap =
    40

  controller() =
    new KnapScalController

  override protected def adjustGraph(graph: VisualGraph): VisualGraph = {
    val vertexNodesByLevel: Map[Int, List[VertexNode]] =
      getVertexNodesByLevel(graph)

    val totalWidthByLevel: Map[Int, Double] =
      vertexNodesByLevel
        .mapValues(nodesInLevel =>
          MinHorizontalGap * (nodesInLevel.size + 1) +
            nodesInLevel.map(_.width()).sum
        )

    val graphWidth: Double =
      totalWidthByLevel
        .values
        .max

    val heightByLevel: Map[Int, Double] =
      vertexNodesByLevel
        .mapValues(
          _.map(_.height())
            .max
        )

    val levels: Int =
      vertexNodesByLevel
        .keySet
        .size

    val verticalCenterByLevel: Map[Int, Double] =
      Range(1, levels)
        .foldLeft(Map(0 ->
          (VerticalGap + heightByLevel(0) / 2)
        ))((cumulatedMap, level) => {
          val levelCenter =
            cumulatedMap(level - 1) + heightByLevel(level - 1) / 2 + VerticalGap + heightByLevel(level) / 2

          cumulatedMap +
            (level -> levelCenter)
        })

    val leafLevel: Int =
      levels - 1

    val leafVertexNodes: List[VertexNode] =
      vertexNodesByLevel(leafLevel)

    val leafHorizontalGap: Double =
      (graphWidth - leafVertexNodes.map(_.width()).sum) /
        (leafVertexNodes.size + 1)

    val leafHorizontalCenters: List[Double] =
      Range(1, leafVertexNodes.size)
        .foldLeft(
          List(leafHorizontalGap + leafVertexNodes.head.width() / 2)
        )((cumulatedHorizontalCenters, index) =>
          cumulatedHorizontalCenters :+
            cumulatedHorizontalCenters.last +
              leafVertexNodes(index - 1).width() / 2 +
              leafHorizontalGap +
              leafVertexNodes(index).width()
        )

    val leafVerticalCenter =
      verticalCenterByLevel(leafLevel)


    val graphWithUpdatedLeafVertexes: VisualGraph =
      leafVertexNodes
        .zipWithIndex
        .foldLeft(graph) { case (cumulatedGraph, (leafVertexNode, index)) =>
          val updatedLeafVertex =
            leafVertexNode
              .vertex()
              .asInstanceOf[KnapScalVertex]
              .setCenter(
                new Point2D(
                  leafHorizontalCenters(index),
                  leafVerticalCenter
                )
              )

          cumulatedGraph
            .updateVertex(updatedLeafVertex)
        }

    Range(0, leafLevel)
      .reverse
      .foldLeft(graphWithUpdatedLeafVertexes)((outerCumulatedGraph, level) => {
        val verticalCenter =
          verticalCenterByLevel(level)

        vertexNodesByLevel(level)
          .zipWithIndex
          .foldLeft(outerCumulatedGraph) {
            case (innerCumulatedGraph, (vertexNode, index)) =>
              val vertex =
                vertexNode.vertex()

              val successorHorizontalCenters: List[Double] =
                innerCumulatedGraph
                  .getSuccessors(vertex)
                  .toList
                  .map(_.center.x)
                  .sorted

              val vertexHorizontalCenter: Double =
                successorHorizontalCenters match {
                  case leftCenter::rightCenter::Nil =>
                    leftCenter + (rightCenter - leftCenter) / 2

                  case singleCenter::Nil =>
                    singleCenter

                  case Nil =>
                    index match {
                      case 0 =>
                        MinHorizontalGap +
                          vertexNode.width() / 2

                      case _ =>
                        val previousVertexNodeInLevel: VertexNode =
                          vertexNodesByLevel(level)(index - 1)

                        val previousHorizontalCenter: Double =
                          innerCumulatedGraph
                            .vertexesById(
                              previousVertexNodeInLevel
                                .vertex()
                                .id
                            )
                            .center
                            .x

                        previousHorizontalCenter +
                          previousVertexNodeInLevel.width() / 2 +
                          MinHorizontalGap +
                          vertexNode.width() / 2
                    }

                  case _ =>
                    throw new AssertionError("This should never match")
                }

              val updatedVertex =
                vertex
                  .asInstanceOf[KnapScalVertex]
                  .setCenter(
                    new Point2D(
                      vertexHorizontalCenter,
                      verticalCenter
                    )
                  )

              innerCumulatedGraph
                .updateVertex(updatedVertex)
            }
      })
  }

  private def getVertexNodesByLevel(graph: VisualGraph): Map[Int, List[VertexNode]] = {
    val rootVertex =
      graph.rootVertexes.head

    getLevelAndVertexNodeTuples(
      Nil,
      rootVertex
    )
      .groupBy(_._1)
      .mapValues(_.map(_._2))
  }

  private def getLevelAndVertexNodeTuples(
                                           cumulatedTuples: List[(Int, VertexNode)],
                                           vertex: VisualVertex
                                         ): List[(Int, VertexNode)] = {
    val node =
      vertex
        .asInstanceOf[KnapScalVertex]
        .node

    val vertexNode =
      vertexNodesByVertex(vertex)

    val tuplesWithVertex =
      cumulatedTuples :+
        (node.level -> vertexNode)

    val successors =
      graph().getSuccessors(vertex)
        .map(_.asInstanceOf[KnapScalVertex])

    List(
      node.takingNode,
      node.leavingNode
    ).foldLeft(tuplesWithVertex)((updatedTuples, optionalSubNode) =>
      optionalSubNode.flatMap(subNode =>
        successors
          .find(_.node == subNode)
          .map(subVertex =>
            getLevelAndVertexNodeTuples(
              updatedTuples,
              subVertex
            )
          )
      )
        .getOrElse(updatedTuples)
    )
  }
}
