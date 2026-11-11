package info.gianlucacosta.knapscal.app.branchbound.rendering

import info.gianlucacosta.eighthbridge.fx.graph.VisualLink
import info.gianlucacosta.eighthbridge.graphs.facets.{HasDefaultArcBarbs, HasHandlesSettable, HasSelectedSettable, HasStyleClassesSettable}

class KnapScalLink
  extends VisualLink
  with HasDefaultArcBarbs
  with HasHandlesSettable
  with HasStyleClassesSettable
  with HasSelectedSettable
