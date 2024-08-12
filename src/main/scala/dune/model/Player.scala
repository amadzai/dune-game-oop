// Class acts as a model for the Player.
package dune.model

import dune.model.scalafxHandler.ScalafxSound
import scala.collection.mutable.ArrayBuffer
// ScalaFX Imports
import scalafx.beans.property.{DoubleProperty, IntegerProperty}

// Player class.
class Player(val name: String) {
  // Variable to store the number of Hero deaths in a Player's army.
  var heroDeathCounter = 0

  // Variable of array buffer to store the Heroes in a Player's army.
  var army:ArrayBuffer[Hero] = ArrayBuffer.empty[Hero]
  // Call method to randomly create an army for the Player.
  createArmy()

  // Variable to store and determine the starting current Hero of a Player's army during a Battle.
  var currentHero: Hero = army(0)
  // Variable to store the current Hero's ID of a Player's army.
  var currentHeroID: IntegerProperty = IntegerProperty(0)
  // Variable to store the current Hero's health points (HP) of a Player's army.
  var currentHeroHealthPoints: DoubleProperty = DoubleProperty(currentHero.currentHealthPoints)

  // Variable initialized to determine if the Player has lost.
  var lost = false

  // Method to determine that the Player has lost.
  def lose(): Unit = {
    this.lost = true
  }

  // Method for the Player to change the current Hero. If all the Heroes are dead, the Player has lost the game.
  def swapHero(): Unit = {
    if (heroDeathCounter == army.length) {
      // Player loses the game as all their Heroes are dead.
      lost = true
    } else {
      // Resets the current Hero's ID depending on which Hero position they are in.
      do {
        if (currentHeroID.value == army.length - 1) {
          currentHeroID.value = 0
        } else {
          currentHeroID.value += 1
        }
      } while (army(currentHeroID.value).currentHealthPoints <= 0)
      currentHero = army(currentHeroID.value)
    }
  }

  // Method for randomly generating an army of 3 Heroes for the Player.
  def createArmy(): Unit = {
    // Value to store scala's Random function.
    val randomizer = scala.util.Random
    // For loop to assign the Heroes with the ScalafxSound trait (mixin).
    for (i <- 0 until 3) {
      var newHero = Array(
        new Archer() with ScalafxSound,
        new Blademan() with ScalafxSound,
        new Berserker() with ScalafxSound,
        new Brawler() with ScalafxSound,
        new Cleric() with ScalafxSound,
        new Caster() with ScalafxSound
      )
      // Adds 3 Heroes to the Player's army randomly.
      army += newHero(randomizer.nextInt(newHero.length))
    }
  }
}