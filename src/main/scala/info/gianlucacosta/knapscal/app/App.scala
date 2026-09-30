package info.gianlucacosta.knapscal.app

import javafx.stage.Stage

import info.gianlucacosta.helios.apps.{AppInfo, AuroraAppInfo}
import info.gianlucacosta.helios.fx.application.{AppBase, AppMain, SplashStage}
import info.gianlucacosta.knapscal.ArtifactInfo
import info.gianlucacosta.knapscal.icons.MainIcon

import scalafx.application.Platform


object App extends AppMain[App](classOf[App])

class App extends AppBase(AuroraAppInfo(ArtifactInfo, MainIcon)) {
  override def startup(appInfo: AppInfo, splashStage: SplashStage, primaryStage: Stage): Unit = {
    val mainScene = new MainScene(appInfo)

    Platform.runLater {
      primaryStage.setScene(mainScene)
    }

    Platform.runLater {
      primaryStage.sizeToScene()
    }

    Platform.runLater {
      primaryStage.show()

      primaryStage.centerOnScreen()
    }
  }
}
