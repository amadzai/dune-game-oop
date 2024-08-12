// Trait for ScalaFX sounds.
package dune.model.scalafxHandler

import dune.model.MediaHandler
// ScalaFX Imports
import scalafx.scene.media.{Media, MediaPlayer}

// ScalafxSound extends MediaHandler Trait.
trait ScalafxSound extends MediaHandler {

  // Method for playing the sound selected by the controller of each page.
  def startSoundSelected(filename: String, loop: Boolean = false): Unit ={
    // Stores media file as a value.
    val media = new Media(getClass.getResource("/dune/audio/" + filename).toURI().toString())
    // Assign MediaPlayer to a value.
    val player = new MediaPlayer(media = media)
    // Loops the sound after it ends infinite times.
    if (loop) player.setCycleCount(MediaPlayer.Indefinite)
    // Stop the sound.
    player.stop()
    // Play the sound.
    player.play()
  }

  // Method overridden from MediaHandler to start the damage sound when a Player's Hero is attacked.
  override def startDamageSound(): Unit = {
    // Call method to play the sound from the file.
    startSoundSelected("damageSound.mp3")
  }

  // Method overridden from MediaHandler to start the damage sound when a Player's Hero is dead.
  override def startDeathSound(): Unit = {
    // Call method to play the sound from the file.
    startSoundSelected("deathSound.wav")
  }
}
