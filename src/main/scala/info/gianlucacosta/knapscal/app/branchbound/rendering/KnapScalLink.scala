package info.gianlucacosta.knapscal.app.branchbound.rendering

import info.gianlucacosta.eighthbridge.fx.{LinkArrow, LinkHandleRadius, VisualLink}
import scalafx.geometry.Point2D

import java.util.UUID

case class KnapScalLink(
                         text: String = "",
                         internalPoints: List[Point2D] = List(),
                         selected: Boolean = false,
                         labelCenter: Option[Point2D] = None,
                         styleClasses: Set[String] = Set(),
                         id: UUID = UUID.randomUUID()
                       ) extends VisualLink {

  override def visualCopy(
                           text: String,
                           internalPoints: List[Point2D],
                           selected: Boolean,
                           labelCenter: Option[Point2D],
                           arrow: LinkArrow,
                           handleRadius: LinkHandleRadius,
                           styleClasses: Set[String]): KnapScalLink.this.type =
    copy(
      text = text,
      internalPoints = internalPoints,
      selected = selected,
      labelCenter = labelCenter,
      styleClasses = styleClasses,
      id = id
    ).asInstanceOf[this.type]
}
