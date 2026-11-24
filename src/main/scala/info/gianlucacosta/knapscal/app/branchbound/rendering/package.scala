package info.gianlucacosta.knapscal.app.branchbound

import info.gianlucacosta.eighthbridge.core.Graph
import info.gianlucacosta.eighthbridge.fx.Styles
import info.gianlucacosta.knapscal.app.App

package object rendering {
  type KnapScalGraph =
    Graph[KnapScalVertex, KnapScalArc]

  val Stylesheets: List[String] = List(
    Styles.resourceUrl.toExternalForm,
    App.getResource("KnapScal.css").toExternalForm
  )
}
