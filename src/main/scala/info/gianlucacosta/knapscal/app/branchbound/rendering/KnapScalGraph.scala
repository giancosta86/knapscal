package info.gianlucacosta.knapscal.app.branchbound.rendering

import info.gianlucacosta.eighthbridge.core.Graph
import info.gianlucacosta.knapscal.knapsack.branchbound.Node


object KnapScalGraph {
  def create(rootNode: Node): KnapScalGraph = {
    val rootVertex =
      new KnapScalVertex(rootNode)

    val rootGraph =
      Graph.createDirected()
        .addVertex(rootVertex)

    createSubGraph(
      rootGraph,
      rootVertex
    )
  }

  private def createSubGraph(cumulatedGraph: KnapScalGraph, vertex: KnapScalVertex): KnapScalGraph = {
    val node =
      vertex.node

    val takingGraph: KnapScalGraph =
      node.takingNode.map(takingNode => {
        val takingVertex =
          new KnapScalVertex(takingNode)

        val updatedGraph =
          cumulatedGraph
            .addVertex(takingVertex)
            .addLink(vertex, takingVertex, new KnapScalArc)

        createSubGraph(updatedGraph, takingVertex)
      })
        .getOrElse(cumulatedGraph)

    val leavingGraph: KnapScalGraph =
      node.leavingNode.map(leavingNode => {
        val leavingVertex =
          new KnapScalVertex(leavingNode)

        val updatedGraph =
          takingGraph
            .addVertex(leavingVertex)
            .addLink(vertex, leavingVertex, new KnapScalArc)

        createSubGraph(updatedGraph, leavingVertex)
      })
        .getOrElse(takingGraph)

    leavingGraph
  }
}
