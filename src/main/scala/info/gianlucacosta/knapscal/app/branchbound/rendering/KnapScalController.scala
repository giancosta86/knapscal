package info.gianlucacosta.knapscal.app.branchbound.rendering

import info.gianlucacosta.eighthbridge.fx.GraphCanvasController
import info.gianlucacosta.eighthbridge.fx.controller.{Directed, LayoutEditing}

class KnapScalController extends GraphCanvasController[KnapScalVertex, KnapScalLink, KnapScalGraph]
  with LayoutEditing[KnapScalVertex, KnapScalLink, KnapScalGraph]
  with Directed[KnapScalVertex, KnapScalLink, KnapScalGraph]
