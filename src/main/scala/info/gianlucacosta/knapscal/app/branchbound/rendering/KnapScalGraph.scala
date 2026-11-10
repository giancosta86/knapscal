package info.gianlucacosta.knapscal.app.branchbound.rendering


import info.gianlucacosta.eighthbridge.fx.graph.{VisualGraph, VisualLink}
import info.gianlucacosta.knapscal.knapsack.branchbound.Node


object KnapScalGraph {
  def create(rootNode: Node): VisualGraph = {
    val rootVertex =
      new KnapScalVertex(rootNode)

    val rootGraph =
      new VisualGraph()
        .addVertex(rootVertex)

    createSubGraph(
      rootGraph,
      rootVertex
    )
  }

  private def createSubGraph(cumulatedGraph: VisualGraph, vertex: KnapScalVertex): VisualGraph = {
    val node =
      vertex.node

    val takingGraph: VisualGraph =
      node.takingNode.map(takingNode => {
        val takingVertex =
          new KnapScalVertex(takingNode)

        val updatedGraph =
          cumulatedGraph
            .addVertex(takingVertex)
            .addLink(vertex, takingVertex, VisualLink.createDirected())

        createSubGraph(updatedGraph, takingVertex)
      })
        .getOrElse(cumulatedGraph)

    val leavingGraph: VisualGraph =
      node.leavingNode.map(leavingNode => {
        val leavingVertex =
          new KnapScalVertex(leavingNode)

        val updatedGraph =
          takingGraph
            .addVertex(leavingVertex)
            .addLink(vertex, leavingVertex, VisualLink.createDirected())

        createSubGraph(updatedGraph, leavingVertex)
      })
        .getOrElse(takingGraph)

    leavingGraph
  }
}
