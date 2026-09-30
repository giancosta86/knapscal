package info.gianlucacosta.knapscal.app

import javafx.fxml.FXMLLoader
import javafx.scene.Scene
import javafx.scene.layout.BorderPane

import info.gianlucacosta.helios.apps.AppInfo

import scalafx.application.Platform


private class MainScene(appInfo: AppInfo) extends Scene({
  val loader = new FXMLLoader()

  loader.setLocation(classOf[MainScene].getResource("MainScene.fxml"))

  val root = loader.load[BorderPane]

  val controller = loader.getController[MainSceneController]

  Platform.runLater {
    root.setPrefSize(700, 450)
  }

  Platform.runLater {
    controller.setup(appInfo)
  }

  root
})