package info.gianlucacosta.knapscal.app.branchbound.rendering

import info.gianlucacosta.eighthbridge.fx.{GraphCanvasController, LinkNode, VertexNode}
import info.gianlucacosta.eighthbridge.fx.controller.composite.NonStructuralEditing
import info.gianlucacosta.eighthbridge.fx.graph.{VisualLink, VisualVertex}
import info.gianlucacosta.eighthbridge.fx.linknodes.plain.PlainLinkNode
import info.gianlucacosta.eighthbridge.fx.vertexnodes.labeled.LabeledVertexNode

class KnapScalController extends GraphCanvasController
  with NonStructuralEditing {
  override def createVertexNode(vertex: VisualVertex): VertexNode =
    new LabeledVertexNode(graphCanvas, vertex)

  override def createLinkNode(link: VisualLink): LinkNode =
    new PlainLinkNode(graphCanvas, link)
}

