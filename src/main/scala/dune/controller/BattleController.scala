// Class acts as controller for Battle page.
package dune.controller

import dune.Main
import dune.model.Battle
// ScalaFX Imports
import scalafx.event.ActionEvent
import scalafx.scene.control.{Button, Label, ProgressIndicator}
import scalafx.scene.image.{Image, ImageView}
import scalafx.scene.layout.GridPane
import scalafx.scene.media.{Media, MediaPlayer}
import scalafx.scene.shape.Circle
import scalafx.scene.text.Text
// ScalaFXML Macro Imports
import scalafxml.core.macros.sfxml

// SFXML Annotated BattleController class.
@sfxml
class BattleController(

                        // Value to store the background image of the Battle page.
                        private val battleBackground: ImageView,

                        // Values to store the GIF images of the current Heroes in the Battle page.
                        private val currentHeroLeft: ImageView,
                        private val currentHeroRight: ImageView,

                        // Variables to store the static images of the Heroes Type in the Player's army.
                        private var hero1TypeLeft: ImageView,
                        private var hero2TypeLeft: ImageView,
                        private var hero3TypeLeft: ImageView,
                        private var hero1TypeRight: ImageView,
                        private var hero2TypeRight: ImageView,
                        private var hero3TypeRight: ImageView,

                        // Variables to store the circle shape where the Heroes Type will be displayed.
                        private var hero1TypeCircleLeft: Circle,
                        private var hero2TypeCircleLeft: Circle,
                        private var hero3TypeCircleLeft: Circle,
                        private var hero1TypeCircleRight: Circle,
                        private var hero2TypeCircleRight: Circle,
                        private var hero3TypeCircleRight: Circle,

                        // Variables to store the abilities of Player 1 (Left) as buttons.
                        private var ability1Left: Button,
                        private var ability2Left: Button,
                        private var ability3Left: Button,
                        private var ability4Left: Button,

                        // Variables to store the abilities of Player 2 (Right) as buttons.
                        private var ability1Right: Button,
                        private var ability2Right: Button,
                        private var ability3Right: Button,
                        private var ability4Right: Button,

                        // Variables to store the titles (names) of the Heroes.
                        private var heroTitleLeft: Label,
                        private var heroTitleRight: Label,

                        // Variables to store the health points (HP) of the current Heroes as a progress bar and text.
                        private var healthPointsLeft: ProgressIndicator,
                        private var healthPointsRight: ProgressIndicator,
                        private var healthPointsLeftText: Label,
                        private var healthPointsRightText: Label,

                        // Variables to store the grid pane as a position in the view (Battle.fxml).
                        private var gridPaneLeft: GridPane,
                        private var gridPaneRight: GridPane,

                        // Variable to store the text displayed during battle (eg.Damage 20.0).
                        private var battleText: Text,
                        // Variable to store the button to exit Battle as a button.
                        private var endButton: Button
                      ) {

  // Stores media file as a value - in this case it is the background music.
  val media = new Media(getClass.getResource("/dune/audio/battleSong.mp3").toURI.toString)
  // Assign MediaPlayer to a value.
  val mediaPlayer = new MediaPlayer(media = media)

  // Stores media file as a value - in this case it is the victory music played when the Battle ends.
  val media2 = new Media(getClass.getResource("/dune/audio/victorySong.mp3").toURI.toString)
  // Assign MediaPlayer to a value.
  val mediaPlayer2 = new MediaPlayer(media = media2)

  // Loops the background music after it ends infinite times.
  mediaPlayer.setCycleCount(MediaPlayer.Indefinite)
  // Play the background music.
  mediaPlayer.play()

  // Assign image from resources to act as background image.
  battleBackground.image = new Image(getClass.getResourceAsStream("/dune/picture/battleBackground.jpg"))
  // Value for storing the image displayed when a Hero dies.
  val deathImage = "/dune/picture/Dead.png"

  //Initialize Battle
  var battle = new Battle("1", "2")
  // Retrieve text from the current battle status as battle text.
  battleText.text <== battle.currentBattleStatus

  // Assign the Hero Type images from the images in the resource based on the Hero Titles from the Player's army.
  hero1TypeLeft.image = new Image(getClass.getResourceAsStream("/dune/picture/" + battle.playerLeft.army(0).name + ".png"))
  hero2TypeLeft.image = new Image(getClass.getResourceAsStream("/dune/picture/" + battle.playerLeft.army(1).name + ".png"))
  hero3TypeLeft.image = new Image(getClass.getResourceAsStream("/dune/picture/" + battle.playerLeft.army(2).name + ".png"))
  hero1TypeRight.image = new Image(getClass.getResourceAsStream("/dune/picture/" + battle.playerRight.army(0).name + ".png"))
  hero2TypeRight.image = new Image(getClass.getResourceAsStream("/dune/picture/" + battle.playerRight.army(1).name + ".png"))
  hero3TypeRight.image = new Image(getClass.getResourceAsStream("/dune/picture/" + battle.playerRight.army(2).name + ".png"))

  // Assign the Hero GIF's from the images in the resource based on the Hero Title from the Player's army.
  currentHeroLeft.image = new Image(getClass.getResourceAsStream("/dune/picture/" + battle.playerLeft.army(0).name + ".gif"))
  currentHeroLeft.scaleX = -1
  currentHeroRight.image = new Image(getClass.getResourceAsStream("/dune/picture/" + battle.playerRight.army(0).name + ".gif"))

  // Assign the names of the Hero's abilities to be displayed as text on the top bar.
  ability1Left.text = battle.playerLeft.army(0).ability1.getClass.getSimpleName.dropRight(1)
  ability2Left.text = battle.playerLeft.army(0).ability2.getClass.getSimpleName.dropRight(1)
  ability3Left.text = battle.playerLeft.army(0).ability3.getClass.getSimpleName.dropRight(1)
  ability4Left.text = battle.playerLeft.army(0).ability4.getClass.getSimpleName.dropRight(1)
  ability1Right.text = battle.playerRight.army(0).ability1.getClass.getSimpleName.dropRight(1)
  ability2Right.text = battle.playerRight.army(0).ability2.getClass.getSimpleName.dropRight(1)
  ability3Right.text = battle.playerRight.army(0).ability3.getClass.getSimpleName.dropRight(1)
  ability4Right.text = battle.playerRight.army(0).ability4.getClass.getSimpleName.dropRight(1)

  // Assigns the health points (HP) of the starting Hero's during the battle.
  updateHealthPointsBar(battle.playerLeft.army(0).currentHealthPoints, battle.playerLeft.army(0).totalHealthPoints, healthPointsLeft, healthPointsLeftText)
  updateHealthPointsBar(battle.playerRight.army(0).currentHealthPoints, battle.playerRight.army(0).totalHealthPoints, healthPointsRight, healthPointsRightText)

  // Highlights the current selected Hero on the Hero Type bar. (White)
  hero1TypeCircleLeft.strokeWidth = 6
  hero1TypeCircleRight.strokeWidth = 6

  // Assigns the titles (names) of the Hero's as text to be displayed at the bottom of the Battle page.
  heroTitleLeft.text = battle.playerLeft.army(0).name
  heroTitleRight.text = battle.playerRight.army(0).name


  // Allows a Player to use their Hero's ability when it is their turn.
  battle.playerTurn.onChange((_, old, newV) => {
    // Left player turn ends or right player turn ends.
    gridPaneLeft.disable.value = gridPaneRight.disable.value
    // Swap the allowed abilities for the Player.
    gridPaneRight.disable.value = !gridPaneRight.disable.value
  })

  // Occurs when the game ends either through defeat or forfeiting.
  battle.endGame.onChange((_, old, newV) => {
    // Stop the current background music.
    mediaPlayer.stop()
    // Loops the new background music after it ends infinite times.
    mediaPlayer2.setCycleCount(MediaPlayer.Indefinite)
    // Play the new background music (VictorySong).
    mediaPlayer2.play()
    // Stops the Players from using their Hero's abilities.
    gridPaneLeft.disable.value = true
    gridPaneRight.disable.value = true
    // Show the "Return to Main Menu" button.
    endButton.setVisible(true)
  })

  // Occurs when Player 1 (Left) is attacked by the opponent.
  battle.playerLeft.currentHeroHealthPoints.onChange((_, oldHP, newHP) => {
    // Updates the current Hero of Player 1's health points (HP).
    updateHealthPointsBar(newHP.doubleValue(), battle.playerLeft.currentHero.totalHealthPoints, healthPointsLeft, healthPointsLeftText)

    // Conditional that occurs when the current Hero of Player 1 dies.
    if (battle.playerLeft.currentHero.currentHealthPoints <= 0) {
      // Changes the image of the current Hero's Type to a skull image representing death of the hero.
      currentHeroLeft.image = new Image(getClass.getResourceAsStream(deathImage))
      currentHeroLeft.scaleX = 1
      // Changes the current Hero to a different Hero in Player 1's army.
      battle.playerLeft.currentHeroID.intValue() match {
        case 0 =>
          hero1TypeLeft.image = new Image(getClass.getResourceAsStream(deathImage))
        case 1 =>
          hero2TypeLeft.image = new Image(getClass.getResourceAsStream(deathImage))
        case 2 =>
          hero3TypeLeft.image = new Image(getClass.getResourceAsStream(deathImage))
        case default =>
      }
    }
  })

  // Occurs when Player 2 (Right) is attacked by the opponent.
  battle.playerRight.currentHeroHealthPoints.onChange((_, oldHP, newHP) => {
    // Updates the current Hero of Player 2's health points (HP).
    updateHealthPointsBar(newHP.doubleValue(), battle.playerRight.currentHero.totalHealthPoints, healthPointsRight, healthPointsRightText)

    // Conditional that occurs when the current Hero of Player 2 dies.
    if (battle.playerRight.currentHero.currentHealthPoints <= 0) {
      // Changes the image of the current Hero's Type to a skull image representing death of the hero.
      currentHeroRight.image = new Image(getClass.getResourceAsStream(deathImage))
      // Changes the current Hero to a different Hero in Player 2's army.
      battle.playerRight.currentHeroID.intValue() match {
        case 0 =>
          hero1TypeRight.image = new Image(getClass.getResourceAsStream(deathImage))
        case 1 =>
          hero2TypeRight.image = new Image(getClass.getResourceAsStream(deathImage))
        case 2 =>
          hero3TypeRight.image = new Image(getClass.getResourceAsStream(deathImage))
        case default =>
      }
    }
  })

  // Occurs when the current Hero of Player 1 (Left) is changed either through Swap or Death.
  battle.playerLeft.currentHeroID.onChange((_, oldID, newID) => {
    // Assigns the title (name) of the new Hero as text to be displayed at the bottom.
    heroTitleLeft.text = battle.playerLeft.army(newID.intValue).name

    // Assign the names of the new Hero's abilities to be displayed as text on the top bar.
    ability1Left.text = battle.playerLeft.army(newID.intValue).ability1.getClass.getSimpleName.dropRight(1)
    ability2Left.text = battle.playerLeft.army(newID.intValue).ability2.getClass.getSimpleName.dropRight(1)
    ability3Left.text = battle.playerLeft.army(newID.intValue).ability3.getClass.getSimpleName.dropRight(1)
    ability4Left.text = battle.playerLeft.army(newID.intValue).ability4.getClass.getSimpleName.dropRight(1)

    // Assigns the health points (HP) of the new Hero's during the battle.
    updateHealthPointsBar(battle.playerLeft.army(newID.intValue).currentHealthPoints, battle.playerLeft.army(newID.intValue).totalHealthPoints, healthPointsLeft, healthPointsLeftText)

    // Assign the Hero GIF's from the images in the resource based on the Hero Title from the Player's army.
    currentHeroLeft.image = new Image(getClass.getResourceAsStream("/dune/picture/" + battle.playerLeft.army(newID.intValue).name + ".gif"))
    currentHeroLeft.scaleX = -1

    // Highlights the current selected Hero on the Hero Type bar based on the new Hero's ID. (White)
    newID.intValue() match {
      case 0 =>
        hero1TypeCircleLeft.strokeWidth = 4
      case 1 =>
        hero2TypeCircleLeft.strokeWidth = 4
      case 2 =>
        hero3TypeCircleLeft.strokeWidth = 4
      case default =>
    }
    // Reduces the circle weight of the unselected Hero on the Hero Type bar. (White)
    if (oldID != null)
      oldID.intValue() match {
        case 0 =>
          hero1TypeCircleLeft.strokeWidth = 1
        case 1 =>
          hero2TypeCircleLeft.strokeWidth = 1
        case 2 =>
          hero3TypeCircleLeft.strokeWidth = 1
        case default =>
      }
  })

  // Occurs when the current Hero of Player 2 (Right) is changed either through Swap or Death.
  battle.playerRight.currentHeroID.onChange((_, oldID, newID) => {
    // Assigns the title (name) of the new Hero as text to be displayed at the bottom.
    heroTitleRight.text = battle.playerRight.army(newID.intValue).name

    // Assign the names of the new Hero's abilities to be displayed as text on the top bar.
    ability1Right.text = battle.playerRight.army(newID.intValue).ability1.getClass.getSimpleName.dropRight(1)
    ability2Right.text = battle.playerRight.army(newID.intValue).ability2.getClass.getSimpleName.dropRight(1)
    ability3Right.text = battle.playerRight.army(newID.intValue).ability3.getClass.getSimpleName.dropRight(1)
    ability4Right.text = battle.playerRight.army(newID.intValue).ability4.getClass.getSimpleName.dropRight(1)

    // Assigns the health points (HP) of the new Hero's during the battle.
    updateHealthPointsBar(battle.playerRight.army(newID.intValue).currentHealthPoints, battle.playerRight.army(newID.intValue).totalHealthPoints, healthPointsRight, healthPointsRightText)
    currentHeroRight.image = new Image(getClass.getResourceAsStream("/dune/picture/" + battle.playerRight.army(newID.intValue).name + ".gif"))

    // Highlights the current selected Hero on the Hero Type bar based on the new Hero's ID. (White)
    newID.intValue() match {
      case 0 =>
        hero1TypeCircleRight.strokeWidth = 4
      case 1 =>
        hero2TypeCircleRight.strokeWidth = 4
      case 2 =>
        hero3TypeCircleRight.strokeWidth = 4
      case default =>
    }
    // Reduces the circle weight of the unselected Hero on the Hero Type bar. (White)
    if (oldID != null)
      oldID.intValue() match {
        case 0 =>
          hero1TypeCircleRight.strokeWidth = 1
        case 1 =>
          hero2TypeCircleRight.strokeWidth = 1
        case 2 =>
          hero3TypeCircleRight.strokeWidth = 1
        case default =>
      }
  })

  // Method for updating the health points (HP) bar of a Hero.
  def updateHealthPointsBar(healthPoints: Double, totalHealthPoints: Double, healthPointsBar: ProgressIndicator, healthPointsText: Label): Unit = {

    // Value for storing the current health points (HP) of the Hero over the total health points of the Hero.
    val healthPointsValue = healthPoints / totalHealthPoints
    // Changes the health points bar's progress based on the current health points value. (0.0 - 1.0).
    healthPointsBar.progress = healthPointsValue

    // Value for storing the color of the health points bar based on the current health points value. (0.0 - 1.0).
    val healthPointsBarColor = {
      // Starting color.
      if (healthPointsValue >= 0.8) "-fx-accent: Green;"
      // Slightly Damaged color.
      else if (healthPointsValue >= 0.6) "-fx-accent: Yellow;"
      // Very Damaged color.
      else if (healthPointsValue >= 0.4) "-fx-accent: Orange;"
      // Close to Death color.
      else "-fx-accent: Red;"
    }
    // Set the color of the health points bar.
    healthPointsBar.setStyle(healthPointsBarColor)
    // Sets the current Hero's health points (HP) / total health points.
    healthPointsText.text = healthPoints + "/" + totalHealthPoints
  }

  // Method for exiting the Battle and go to the Main Menu page.
  def goExit(action: ActionEvent): Unit = {
    mediaPlayer.stop()
    mediaPlayer2.stop()
    Main.displayMainMenu()
  }

  // Methods for each Player's Hero's abilities.
  // Method for Player 2's (Right) Hero's first ability.
  def goAbility1Right(action: ActionEvent): Unit = {
    // Tracks the battle progress based on this ability whether to change turns or end the game if all of the opponents Heroes are dead.
    battle.battleProgress {
      // Provides the current battle status value based on the abilities damage or buff. Applies the damage or buff to opponent.
      battle.currentBattleStatus.value = battle.playerRight.currentHero.attack(battle.playerRight.currentHero.ability1, battle.playerLeft)
    }
  }

  // Method for Player 2's (Right) Hero's second ability.
  def goAbility2Right(action: ActionEvent): Unit = {
    // Tracks the battle progress based on this ability whether to change turns or end the game if all of the opponents Heroes are dead.
    battle.battleProgress {
      // Provides the current battle status value based on the abilities damage or buff. Applies the damage or buff to opponent.
      battle.currentBattleStatus.value = battle.playerRight.currentHero.attack(battle.playerRight.currentHero.ability2, battle.playerLeft)
    }
  }

  // Method for Player 2's (Right) Hero's third ability.
  def goAbility3Right(action: ActionEvent): Unit = {
    // Tracks the battle progress based on this ability whether to change turns or end the game if all of the opponents Heroes are dead.
    battle.battleProgress {
      // Provides the current battle status value based on the abilities damage or buff. Applies the damage or buff to opponent.
      battle.currentBattleStatus.value = battle.playerRight.currentHero.attack(battle.playerRight.currentHero.ability3, battle.playerLeft)
    }
  }

  // Method for Player 2's (Right) Hero's fourth ability.
  def goAbility4Right(action: ActionEvent): Unit = {
    // Tracks the battle progress based on this ability whether to change turns or end the game if all of the opponents Heroes are dead.
    battle.battleProgress {
      // Provides the current battle status value based on the abilities damage or buff. Applies the damage or buff to opponent.
      battle.currentBattleStatus.value = battle.playerRight.currentHero.heroBuff(battle.playerRight.currentHero.ability4, battle.playerLeft)
    }
  }

  // Method for Player 2's (Right) Swap to change their current Hero.
  def goSwapRight(action: ActionEvent): Unit = {
    // Tracks the battle progress based on this ability whether to change turns or end the game if all of the opponents Heroes are dead.
    battle.battleProgress {
      // Swaps the current Hero of the player.
      battle.playerRight.swapHero()
    }
  }

  // Method for Player 2's (Right) Forfeit to surrender and lose the game.
  def goForfeitRight(action: ActionEvent): Unit = {
    // Tracks the battle progress based on this ability whether to change turns or end the game if all of the opponents Heroes are dead.
    battle.battleProgress {
      // Players loses the game and the battle ends.
      battle.playerRight.lose()
    }
  }

  // Method for Player 1's (Left) Hero's first ability.
  def goAbility1Left(action: ActionEvent): Unit = {
    // Tracks the battle progress based on this ability whether to change turns or end the game if all of the opponents Heroes are dead.
    battle.battleProgress {
      // Provides the current battle status value based on the abilities damage or buff. Applies the damage or buff to opponent.
      battle.currentBattleStatus.value = battle.playerLeft.currentHero.attack(battle.playerLeft.currentHero.ability1, battle.playerRight)
    }
  }

  // Method for Player 1's (Left) Hero's second ability.
  def goAbility2Left(action: ActionEvent): Unit = {
    // Tracks the battle progress based on this ability whether to change turns or end the game if all of the opponents Heroes are dead.
    battle.battleProgress {
      // Provides the current battle status value based on the abilities damage or buff. Applies the damage or buff to opponent.
      battle.currentBattleStatus.value = battle.playerLeft.currentHero.attack(battle.playerLeft.currentHero.ability2, battle.playerRight)
    }
  }

  // Method for Player 1's (Left) Hero's third ability.
  def goAbility3Left(action: ActionEvent): Unit = {
    // Tracks the battle progress based on this ability whether to change turns or end the game if all of the opponents Heroes are dead.
    battle.battleProgress {
      // Provides the current battle status value based on the abilities damage or buff. Applies the damage or buff to opponent.
      battle.currentBattleStatus.value = battle.playerLeft.currentHero.attack(battle.playerLeft.currentHero.ability3, battle.playerRight)
    }
  }

  // Method for Player 1's (Left) Hero's fourth ability.
  def goAbility4Left(action: ActionEvent): Unit = {
    // Tracks the battle progress based on this ability whether to change turns or end the game if all of the opponents Heroes are dead.
    battle.battleProgress {
      // Provides the current battle status value based on the abilities damage or buff. Applies the damage or buff to opponent.
      battle.currentBattleStatus.value = battle.playerLeft.currentHero.heroBuff(battle.playerLeft.currentHero.ability4, battle.playerRight)
    }
  }

  // Method for Player 1's (Left) Swap to change their current Hero.
  def goSwapLeft(action: ActionEvent): Unit = {
    // Tracks the battle progress based on this ability whether to change turns or end the game if all of the opponents Heroes are dead.
    battle.battleProgress {
      // Swaps the current Hero of the player.
      battle.playerLeft.swapHero()
    }
  }

  // Method for Player 1's (Left) Forfeit to surrender and lose the game.
  def goForfeitLeft(action: ActionEvent): Unit = {
    // Tracks the battle progress based on this ability whether to change turns or end the game if all of the opponents Heroes are dead.
    battle.battleProgress {
      // Players loses the game and the battle ends.
      battle.playerLeft.lose()
    }
  }
}
