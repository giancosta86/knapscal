package info.gianlucacosta.knapscal.app.branchbound.rendering

import info.gianlucacosta.eighthbridge.fx.AdjustingGraphCanvas
import info.gianlucacosta.eighthbridge.fx.graph.VisualGraph

class KnapScalGraphCanvas(graph: VisualGraph) extends AdjustingGraphCanvas(
  new KnapScalController,
  graph
) {

  override protected def adjustGraph(currentGraph: VisualGraph): Option[VisualGraph] = {
    None
  }
}
