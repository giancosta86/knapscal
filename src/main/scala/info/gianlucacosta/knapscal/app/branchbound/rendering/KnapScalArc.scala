package info.gianlucacosta.knapscal.app.branchbound.rendering

import info.gianlucacosta.eighthbridge.fx.graph.VisualArc
import info.gianlucacosta.eighthbridge.graphs.directed.HasDefaultBarbs
import info.gianlucacosta.eighthbridge.graphs.facets.{HasHandlesSettable, HasSelectedSettable, HasStyleClassesSettable}

class KnapScalArc
  extends VisualArc
  with HasDefaultBarbs
  with HasHandlesSettable
  with HasStyleClassesSettable
  with HasSelectedSettable
