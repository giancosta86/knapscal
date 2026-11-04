package info.gianlucacosta.knapscal.app.branchbound.rendering

import info.gianlucacosta.eighthbridge.fx.AdjustingGraphCanvas
import info.gianlucacosta.eighthbridge.fx.graph.VisualGraph
import info.gianlucacosta.knapscal.knapsack.branchbound.Node
import scalafx.geometry.{Dimension2D, Point2D}

class KnapScalGraphCanvas(graph: VisualGraph) extends AdjustingGraphCanvas(
  new KnapScalController,
  graph
) {
  private type CenterX =
    Double

  private type CenterY =
    Double

  private type Width =
    Double

  private type Height =
    Double

  private val VerticalSpacing =
    60

  private val HorizontalSpacing =
    40

  override protected def adjustGraph(currentGraph: VisualGraph): VisualGraph = {
    val nodeDimensions: Map[Node, Dimension2D] =
      graph
        .vertexes
        .map {
          case vertex: KnapScalVertex =>
            val vertexNode =
              vertexNodesByVertex(vertex)

            val dimension =
              new Dimension2D(
                vertexNode.width(),
                vertexNode.height()
              )

            vertex.node -> dimension
        }
        .toMap

    val rootVertex =
      graph
        .rootVertexes
        .head
        .asInstanceOf[KnapScalVertex]

    val levelNodes: Map[Int, List[Node]] =
      graph
        .fold(List[Node]())((cumulatedVertexes, vertex, _) =>
          vertex.asInstanceOf[KnapScalVertex].node :: cumulatedVertexes
        )
        .groupBy(_.level)

    val levelHeights: Map[Int, Height] =
      levelNodes.map {
        case (levelIndex, nodes) =>
          levelIndex ->
            nodes.map(node => nodeDimensions(node).height).max
      }

    val lastLevelIndex =
      levelNodes.keySet.max

    val levelCenterYs: Map[Int, CenterY] = {
      val rootCenterY =
        VerticalSpacing + nodeDimensions(rootVertex.node).height / 2

      Range.inclusive(1, lastLevelIndex)
        .foldLeft(List(rootCenterY))((cumulatedCenterYs, currentLevel) => {
          val previousLevel = currentLevel - 1
          val previousHeight = levelHeights(previousLevel)
          val previousCenterY = cumulatedCenterYs.head

          val currentHeight = levelHeights(currentLevel)

          val currentCenterY = previousCenterY + previousHeight / 2 + VerticalSpacing + currentHeight / 2

          List(currentCenterY) ++ cumulatedCenterYs
        })
        .reverse
        .zipWithIndex
        .map(pair => (pair._2, pair._1))
        .toMap
    }

    val centerXs: Map[Node, CenterX] =
      Range.inclusive(lastLevelIndex, 0, -1)
        .foldLeft(Map[Node, CenterX]())((cumulatedCenterXs, levelIndex) => {
          val currentNodes = levelNodes(levelIndex)

          cumulatedCenterXs ++ getLevelCenterXs(cumulatedCenterXs, currentNodes, nodeDimensions)
        })

    graph.vertexes.foldLeft(graph) {
      case (cumulatedGraph: VisualGraph, vertex: KnapScalVertex) =>
        val updatedVertex =
          vertex.setCenter(
            new Point2D(
              centerXs(vertex.node),
              levelCenterYs(vertex.node.level)
            )
          )

        cumulatedGraph
          .updateVertex(updatedVertex)
    }
  }

  private def getLevelCenterXs(lowerLevelCenterXs: Map[Node, CenterX], levelNodes: List[Node], nodeSizes: Map[Node, Dimension2D])
  : Map[Node, CenterX] = {

    levelNodes
      .foldLeft(List[(Node, (CenterX, Width))]())(
        (cumulatedTuplesInLevel, currentNode) => {

          val takingNodeCenterXOption: Option[CenterX] =
            currentNode.takingNode.map(takingNode =>
              lowerLevelCenterXs(takingNode)
            )


          val leavingNodeCenterXOption: Option[CenterX] =
            currentNode.leavingNode.map(leavingNode =>
              lowerLevelCenterXs(leavingNode)
            )

          val currentWidth =
            nodeSizes(currentNode).width

          val currentCenterX: CenterX =
            cumulatedTuplesInLevel.headOption.map(previousTupleInLevel => {
              val (_, (previousCenterX: CenterX, previousWidth: Width)) = previousTupleInLevel

              previousCenterX + previousWidth / 2 + HorizontalSpacing + currentWidth / 2
            }).getOrElse({
              if (takingNodeCenterXOption.isEmpty && leavingNodeCenterXOption.isEmpty)
                HorizontalSpacing + currentWidth / 2
              else if (takingNodeCenterXOption.isEmpty || leavingNodeCenterXOption.isEmpty)
                takingNodeCenterXOption.getOrElse(leavingNodeCenterXOption.get)
              else {
                val takingNode = currentNode.takingNode.get
                val takingNodeWidth = nodeSizes(takingNode).width
                takingNodeCenterXOption.get + takingNodeWidth / 2 + HorizontalSpacing / 2
              }
            })

          val currentTuple: (Node, (CenterX, Width)) =
            currentNode ->(currentCenterX, currentWidth)

          List(currentTuple) ++ cumulatedTuplesInLevel
        })
      .map {
        case (node, (centerX, _)) =>
          node -> centerX
      }
      .toMap
  }
}
