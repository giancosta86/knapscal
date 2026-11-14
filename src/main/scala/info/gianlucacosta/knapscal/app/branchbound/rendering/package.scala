package info.gianlucacosta.knapscal.app.branchbound

import info.gianlucacosta.eighthbridge.fx.Styles
import info.gianlucacosta.eighthbridge.graphs.directed.DirectedGraph
import info.gianlucacosta.knapscal.app.App

package object rendering {
  type KnapScalGraph =
    DirectedGraph[KnapScalVertex, KnapScalArc]

  val Stylesheets: List[String] = List(
    Styles.resourceUrl.toExternalForm,
    App.getResource("KnapScal.css").toExternalForm
  )
}
