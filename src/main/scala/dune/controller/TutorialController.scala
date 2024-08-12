// Class acts as controller for Tutorial page.
package dune.controller

import dune.Main
// ScalaFX Imports
import scalafx.event.ActionEvent
import scalafx.scene.image.{Image, ImageView}
import scalafx.scene.media.{Media, MediaPlayer}
// ScalaFXML Macro Imports
import scalafxml.core.macros.sfxml

// SFXML Annotated TutorialController class.
@sfxml
class TutorialController(private val tutorialBackground: ImageView) {

  // Stores media file as a value - in this case it is the background music.
  val media = new Media(getClass.getResource("/dune/audio/tutorialSong.wav").toURI.toString)
  // Assign MediaPlayer to a value.
  val mediaPlayer = new MediaPlayer(media = media)
  // Loops the background music after it ends infinite times.
  mediaPlayer.setCycleCount(MediaPlayer.Indefinite)
  // Play the background music.
  mediaPlayer.play()

  // Garbage collector to recycle unused objects for better memory performance.
  System.gc()

  // Assign image from resources to act as background image.
  tutorialBackground.image = new Image(getClass.getResourceAsStream("/dune/picture/tutorial.png"))

  // Method for returning to the Main Menu.
  def goReturn(action: ActionEvent): Unit = {
    // From Main class, call the method.
    Main.displayMainMenu()
    // Stops the current background music when going to the Main Menu.
    mediaPlayer.stop()
  }
}
