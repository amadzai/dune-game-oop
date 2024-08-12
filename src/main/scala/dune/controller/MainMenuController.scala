// Class acts as controller for Main Menu page.
package dune.controller

import dune.Main
// ScalaFX Imports
import scalafx.event.ActionEvent
import scalafx.scene.image.{Image, ImageView}
import scalafx.scene.media.{Media, MediaPlayer}
// ScalaFXML Macro Imports
import scalafxml.core.macros.sfxml

// SFXML Annotated MainMenuController class.
@sfxml
class MainMenuController(private val menuBackground: ImageView, private val menuLogo: ImageView) {

  // Stores media file as a value - in this case it is the background music.
  val media = new Media(getClass.getResource("/dune/audio/menuSong.wav").toURI.toString)
  // Assign MediaPlayer to a value.
  val mediaPlayer = new MediaPlayer(media = media)
  // Loops the background music after it ends infinite times.
  mediaPlayer.setCycleCount(MediaPlayer.Indefinite)
  // Play the background music.
  mediaPlayer.play()

  // Garbage collector to recycle unused objects for better memory performance.
  System.gc()

  // Assign image from resources to act as background image.
  menuBackground.image = new Image(getClass.getResourceAsStream("/dune/picture/menuBackground.jpg"))
  // Assign image from resources to act as background logo "Dune".
  menuLogo.image = new Image(getClass.getResourceAsStream("/dune/picture/duneLogo.png"))

  // Method for going to the Battle page.
  def goBattle(action: ActionEvent): Unit = {
    // From Main class, call the method.
    Main.displayBattle()
    // Stops the current background music when going to the Battle page.
    mediaPlayer.stop()
  }

  // Method for going to the Tutorial page.
  def goTutorial(action: ActionEvent): Unit = {
    // From Main class, call the method.
    Main.displayTutorial()
    // Stops the current background music when going to the Tutorial page.
    mediaPlayer.stop()
  }

  // Method for exiting the game and closing the application.
  def goExit(action: ActionEvent): Unit = {
    // Call the exit method from System class.
    System.exit(0)
  }
}