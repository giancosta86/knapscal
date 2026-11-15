package info.gianlucacosta.knapscal.app.branchbound.rendering

import info.gianlucacosta.eighthbridge.fx.controllers.bundles.NonStructuralEditing
import info.gianlucacosta.eighthbridge.fx.{GraphCanvasController, LinkNode, VertexNode, VisualVertex}
import info.gianlucacosta.eighthbridge.fx.vertexnodes.labeled.LabeledVertexNode

class KnapScalController extends GraphCanvasController
  with NonStructuralEditing {
  override def createVertexNode(vertex: VisualVertex): VertexNode =
    new LabeledVertexNode(graphCanvas, vertex)
}

