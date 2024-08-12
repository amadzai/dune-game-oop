package dune

import java.net.URL
import javafx.scene.image.Image
import javafx.{scene => jfxs}
import scalafx.Includes._
import scalafx.animation.PauseTransition
import scalafx.application.JFXApp3
import scalafx.application.JFXApp3.PrimaryStage
import scalafx.scene.Scene
import scalafx.util.Duration
import scalafxml.core.{FXMLLoader, NoDependencyResolver}

object Main extends JFXApp3 {

  // Declare roots as a var so it can be modified later
  var roots: jfxs.layout.BorderPane = _

  // Stage definition moved to `start` method as required by `JFXApp3`.
  override def start(): Unit = {
    val rootResource: URL = getClass.getResource("/dune/view/RootLayout.fxml")
    val loader = new FXMLLoader(rootResource, NoDependencyResolver)
    loader.load()
    // Initialize roots
    roots = loader.getRoot[jfxs.layout.BorderPane]

    stage = new PrimaryStage {
      title = "Dune"
      width = 914
      height = 637
      icons += new Image(getClass.getResource("/dune/picture/duneLogo4.png").toURI.toString)
      scene = new Scene {
        root = roots
      }
    }

    // Start the application with the loading screen
    displayLoadingScreen()
  }

  def displayLoadingScreen(): Unit = {
    val viewAsset = getClass.getResource("/dune/view/LoadingScreen.fxml")
    val loader = new FXMLLoader(viewAsset, NoDependencyResolver)
    loader.load()
    val loadingScreenRoot = loader.getRoot[jfxs.layout.AnchorPane]
    roots.setCenter(loadingScreenRoot)
    new PauseTransition(Duration(1500)) {
      onFinished = handle {
        displayMainMenu()
      }
    }.play()
  }

  def displayMainMenu(): Unit = {
    val viewAsset = getClass.getResource("/dune/view/MainMenu.fxml")
    val loader = new FXMLLoader(viewAsset, NoDependencyResolver)
    loader.load()
    val mainMenuRoot = loader.getRoot[jfxs.layout.AnchorPane]
    roots.setCenter(mainMenuRoot)
  }

  def displayTutorial(): Unit = {
    val viewAsset = getClass.getResource("/dune/view/Tutorial.fxml")
    val loader = new FXMLLoader(viewAsset, NoDependencyResolver)
    loader.load()
    val tutorialRoot = loader.getRoot[jfxs.layout.AnchorPane]
    roots.setCenter(tutorialRoot)
  }

  def displayBattle(): Unit = {
    val viewAsset = getClass.getResource("/dune/view/Battle.fxml")
    val loader = new FXMLLoader(viewAsset, NoDependencyResolver)
    loader.load()
    val battleRoot = loader.getRoot[jfxs.layout.AnchorPane]
    roots.setCenter(battleRoot)
  }
}
