package info.gianlucacosta.knapscal.app.branchbound

import info.gianlucacosta.eighthbridge.fx.canvas.GraphCanvas
import info.gianlucacosta.eighthbridge.fx.canvas.basic.{DefaultBasicLink, DragDropController}
import info.gianlucacosta.knapscal.app.branchbound.rendering.{KnapScalGraph, KnapScalVertex}
import info.gianlucacosta.knapscal.knapsack.branchbound.Solution
import info.gianlucacosta.knapscal.knapsack.{ItemsFormatter, Problem}

import scalafx.geometry.Insets
import scalafx.scene.control.Alert.AlertType
import scalafx.scene.control._
import scalafx.scene.layout.BorderPane


private class SolutionDialog(problem: Problem, solution: Solution) extends Alert(AlertType.Information) {
  title = "Knapsack - Branch & Bound"
  headerText = "Solution"
  contentText = solution.bestNode.toString
  resizable = true

  dialogPane().getStylesheets.addAll(
    KnapScalGraph.Stylesheets: _*
  )

  private val solutionTextArea = new TextArea {
    prefHeight = 140
    editable = false
    margin = Insets(0, 0, 15, 0)

    text =
      s"""Ordered problem items: ${ItemsFormatter.format(problem.items)}
          |
        |${solution}""".stripMargin
  }


  private val legendLabel = new Label {
    text =
      """NODE LEGEND:
        |
        |* Index = exploration index
        |
        |* Ū = computed upper bound
        |* U = inherited upper bound
        |
        |* P = cumulated profit
        |* W = cumulated weight
        |
        |* I = Items taken
        |
        |*STOP* = Skip more branching
        |
        |*SOLUTION* = A solution node
        |
        |* z = solution value
      """.stripMargin

    margin = Insets(0, 5, 0, 0)
  }


  private val solutionScrollPane = new ScrollPane {
    content = new GraphCanvas[KnapScalVertex, DefaultBasicLink, KnapScalGraph](
      new DragDropController(true),
      KnapScalGraph.create(solution.rootNode)
    )

    hvalue = hmax() / 2
  }


  private val solutionPane = new BorderPane {
    top = solutionTextArea
    left = legendLabel
    center = solutionScrollPane

    prefWidth = 1100
    prefHeight = 550
  }

  dialogPane().setContent(solutionPane)
}
