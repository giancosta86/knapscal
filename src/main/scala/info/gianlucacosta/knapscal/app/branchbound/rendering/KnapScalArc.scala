package info.gianlucacosta.knapscal.app.branchbound.rendering

import info.gianlucacosta.eighthbridge.fx.VisualArc
import info.gianlucacosta.eighthbridge.core.directed.HasDefaultBarbs
import info.gianlucacosta.eighthbridge.core.facets.{HasSelectedSettable, HasStyleClassesSettable}

class KnapScalArc
  extends VisualArc
  with HasDefaultBarbs
  with HasStyleClassesSettable
  with HasSelectedSettable
