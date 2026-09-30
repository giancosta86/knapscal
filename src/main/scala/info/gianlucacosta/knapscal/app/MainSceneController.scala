package info.gianlucacosta.knapscal.app

import javafx.event.ActionEvent
import javafx.fxml.FXML
import javafx.scene.control.{TextArea, TextField}

import info.gianlucacosta.helios.apps.AppInfo
import info.gianlucacosta.helios.fx.dialogs.about.AboutBox
import info.gianlucacosta.knapscal.app.branchbound.strategies.{DantzigStrategy, MartelloTothStrategy, OptimizedDantzigStrategy}
import info.gianlucacosta.knapscal.knapsack.dynamic.full.DynamicProgrammingSolver
import info.gianlucacosta.knapscal.knapsack.dynamic.optimized.OptimizedDynamicProgrammingSolver
import info.gianlucacosta.knapscal.knapsack.{ItemsFormatter, ItemsParser, Problem}

import scalafx.scene.control.Alert.AlertType
import scalafx.scene.control.{Alert, ChoiceDialog}

private class MainSceneController {
  private var aboutBox: AboutBox = _

  def setup(appInfo: AppInfo): Unit = {
    aboutBox = new AboutBox(appInfo)
  }


  private val itemsParser = new ItemsParser

  @FXML
  private var capacityField: TextField = null

  @FXML
  private var itemsArea: TextArea = null

  @FXML
  private def runBranchBound(event: ActionEvent): Unit = {
    val problem = prepareProblem()
    if (problem.isEmpty) {
      return
    }


    val choices = List(
      new DantzigStrategy,
      new OptimizedDantzigStrategy,
      new MartelloTothStrategy
    )


    val choiceDialog = new ChoiceDialog(choices(0), choices) {
      title = "Branch & Bound - Upper bound strategy"
      headerText = "Choose an algorithm for the upper bound:"
    }

    val chosenStrategy = choiceDialog.showAndWait()
    if (chosenStrategy.isEmpty) {
      return
    }


    chosenStrategy.get.run(problem.get)
  }

  private def prepareProblem(): Option[Problem] = {
    try {
      val items = itemsParser.parse(itemsArea.getText)
      val capacity = capacityField.getText.toInt

      return Some(Problem(items, capacity))
    } catch {
      case e: IllegalArgumentException => {
        val illegalInputAlert = new Alert(AlertType.Warning) {
          headerText = "Invalid input"
          contentText = e.getMessage
          dialogPane().setPrefWidth(500)
        }

        illegalInputAlert.showAndWait()

        return None
      }

    }
  }

  @FXML
  private def runDynamicProgramming(event: ActionEvent): Unit = {
    val problem = prepareProblem()

    if (problem.isEmpty) {
      return
    }

    val solver = new DynamicProgrammingSolver()
    val solution = solver.solve(problem.get)

    val solutionArea = new scalafx.scene.control.TextArea {
      prefWidth = 800
      prefHeight = 480
      editable = false


      text =
        s"""Ordered problem items: ${ItemsFormatter.format(problem.get.items)}
            |
          |${solution.toString()}
        """.stripMargin
    }

    val solutionAlert = new Alert(AlertType.Information) {
      title = "Knapsack - Dynamic Programming"
      headerText = "Solution"
      contentText = solution.value.toString
      dialogPane().setContent(solutionArea)
    }

    solutionAlert.showAndWait()
  }

  @FXML
  private def runOptimizedDynamicProgramming(event: ActionEvent): Unit = {
    val problem = prepareProblem()
    if (problem.isEmpty) {
      return
    }

    val solver = new OptimizedDynamicProgrammingSolver()
    val solution = solver.solve(problem.get)

    val alert = new Alert(AlertType.Information) {
      title = "Knapsack Dynamic Programming - Optimized"
      headerText = None
      contentText = solution.toString()
    }

    alert.showAndWait()
  }


  @FXML
  private def showAboutBox(event: ActionEvent): Unit = {
    aboutBox.show()
  }
}
