// Class acts as a model for the Battle.
package dune.model

// ScalaFX Imports
import scalafx.beans.property.{BooleanProperty, StringProperty}

// Battle class.
class Battle(
              // Values for the titles (names) of the Players (Player 1 (Left) and Player 2 (Right)).
              val player1Title: String,
              val player2Title: String
            ) {

  // Assigns the titles (names) of the Players to values representing the Left and Right player.
  val playerLeft = new Player(player1Title)
  val playerRight = new Player(player2Title)

  // Variable storing the text of the battle (eg. Damage 30.0).
  var currentBattleStatus: StringProperty = new StringProperty()

  // Variable boolean to determine which Player's turn.
  var playerTurn: BooleanProperty = BooleanProperty(false)

  // Variable boolean to determine whether the Battle has ended.
  var endGame: BooleanProperty = BooleanProperty(false)

  // Method to determine which Player is the winner at the end of the battle.
  def winner: Player = {
    if (playerLeft.lost) playerRight
    else if (playerRight.lost) playerLeft
    else null
  }

  // Method boolean to determine if the battle has ended.
  def isBattleOver: Boolean = {
    // True when a player has lost.
    playerLeft.lost || playerRight.lost
  }

  // Method to change the Player's turn.
  def change(): Unit = {
    playerTurn.value = !playerTurn.value
  }

  // Method to determine the progress of the battle.
  def battleProgress(activity: => Unit): Unit = {
    activity
    // If the battle has ended, the current battle status will display the name of the winner.
    if (isBattleOver) {
      endGame.value = true
      currentBattleStatus.value = "Player " + winner.name + " is Victorious!"
    } else {
      // If the battle is still ongoing, change the player's turn.
      change()
    }
  }
}
