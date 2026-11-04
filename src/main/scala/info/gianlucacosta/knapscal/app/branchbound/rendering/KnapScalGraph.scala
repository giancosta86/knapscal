package info.gianlucacosta.knapscal.app.branchbound.rendering


import info.gianlucacosta.eighthbridge.fx.graph.{VisualGraph, VisualLink}
import info.gianlucacosta.knapscal.knapsack.branchbound.Node


object KnapScalGraph {
  def create(rootNode: Node): VisualGraph = {
    val rootVertex =
      KnapScalVertex.create(rootNode)

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
          KnapScalVertex.create(takingNode)

        val updatedGraph =
          cumulatedGraph
            .addVertex(takingVertex)
            .addArc(vertex, takingVertex, VisualLink.DefaultDirected)

        createSubGraph(updatedGraph, takingVertex)
      })
        .getOrElse(cumulatedGraph)

    val leavingGraph: VisualGraph =
      node.leavingNode.map(leavingNode => {
        val leavingVertex =
          KnapScalVertex.create(leavingNode)

        val updatedGraph =
          takingGraph
            .addVertex(leavingVertex)
            .addArc(vertex, leavingVertex, VisualLink.DefaultDirected)

        createSubGraph(updatedGraph, leavingVertex)
      })
        .getOrElse(takingGraph)

    leavingGraph
  }
}
