// Abstract class for the Heroes of the game.
package dune.model

import dune.model.HeroAbility._

// Singleton object that acts as the effectiveness table for the Heroes.
object heroTypeEffectivenessTable {
  // Value of map that stores the Hero Types.
  val table: Map[String, Int] = Map("Ranged" -> 0, "Magic" -> 1, "Melee" -> 2)
  // Value of a multi-dimensional array (2 dimensions) used to compare the effectiveness between Hero Types.
  val matrix: Array[Array[Double]] = Array.ofDim[Double](3, 3)
  // The effectiveness between the Hero Types (Ranged = 0, Magic = 1, Melee = 2).
  matrix(0) = Array(0.5, 0.5, 2.0)
  matrix(1) = Array(2.0, 0.5, 0.5)
  matrix(2) = Array(0.5, 2.0, 0.5)
}

// Hero class extend MediaHandler trait to use its methods. Primarily the ScalaFXSound methods.
abstract class Hero extends MediaHandler {
  // Value to store the title (name) of the Hero.
  val name: String
  // Value to store the Hero Type of the Hero.
  val heroType: String

  // Variables to store the abilities of the Hero.
  var ability1: AttackAbility
  var ability2: AttackAbility
  var ability3: AttackAbility
  var ability4: BuffAbility

  // Value to store the total health points of the Hero.
  val totalHealthPoints: Double
  // Variable to store the current health points (HP) of the Hero.
  var currentHealthPoints: Double

  // Variables to store the attack and defense stat of the Hero.
  var attack: Int
  var defense: Int

  // Method for when the Hero attacks using an AttackAbility.
  def attack(abilityPressed: AttackAbility, opponent: Player): String = {

    // Value to find and store the effectiveness of the Hero's attack based on the Hero Type Effectiveness table.
    val effectiveness: Double = heroTypeEffectivenessTable.matrix(heroTypeEffectivenessTable.table.apply(this.heroType))(heroTypeEffectivenessTable.table.apply(opponent.currentHero.heroType))
    // Variable to store the damage value of the Hero's attack based on the Hero Type Effectiveness and the opponent Hero's stats.
    var damage: Double = abilityPressed.attackDamage * this.attack / 5 / opponent.currentHero.defense * effectiveness

    // Conditional for damage values that are less than 1 (Can occurs when a Hero uses a buff ability).
    if (damage < 1) damage = 1
    opponent.currentHero.currentHealthPoints -= damage

    // Conditional to determine if the attack damages or kills the opponent's Hero.
    if (opponent.currentHero.currentHealthPoints <= 0) {
      opponent.currentHero.currentHealthPoints = 0
      opponent.currentHeroHealthPoints.value = opponent.currentHero.currentHealthPoints
      // Adds one (1) death to the counter.
      opponent.heroDeathCounter += 1
      // Play the death sound.
      startDeathSound()
      // Changes the current Hero of the opponent.
      opponent.swapHero()
    } else {
      // Play the damage sound.
      startDamageSound()
      // Update the opponents current health points (HP).
      opponent.currentHeroHealthPoints.value = opponent.currentHero.currentHealthPoints
    }

    // Displays the effectiveness of the damage on the Battle page after an attack.
    if (effectiveness > 1) {
      "Damage: " + damage + ", attack is effective."
    } else if (effectiveness < 1) {
      "Damage: " + damage + ", attack is not effective."
    } else {
      "Damage: " + damage
    }
  }

  // Method for when the Hero attacks using a Hero Buff.
  def heroBuff(abilityPressed: BuffAbility, opponent: Player): String = {
    // Variable to store the opponent hero to determine who receives the buff.
    var opponentHero: Hero = null
    // Conditional to determine if the buff is applied to the Player's hero or the opponents hero.
    if (abilityPressed.buffSelf) {
      opponentHero = this
    } else {
      opponentHero = opponent.currentHero
    }

    // Match expression to determine the type of buff used and its effects.
    // Displays the buff effect on the Battle page after a buff.
    abilityPressed.buffTitle match {
      case "Defense" =>
        opponentHero.defense += abilityPressed.buffValue
        if (opponentHero.defense < 1) opponentHero.defense = 1
        (if (abilityPressed.buffSelf) "Your " else "Opponent's ") + "Defense " + abilityPressed.buffValue.toString
      case "Attack" =>
        opponentHero.attack += abilityPressed.buffValue
        if (opponentHero.attack < 1) opponentHero.attack = 1
        (if (abilityPressed.buffSelf) "Your " else "Opponent's ") + "Attack " + abilityPressed.buffValue.toString
      case default =>
        "Ability has no effect"
    }
  }
}

// Abstract class for the Hero's abilities.
abstract class Ability {
}

// Abstract class for the Hero's attack abilities.
abstract class AttackAbility extends Ability {
  // Value storing the damage value of the attack.
  val attackDamage: Int
}

// Abstract class for the Hero's buff abilities.
abstract class BuffAbility extends Ability {
  // Value to store the buffs type (Attack or Defence).
  val buffTitle: String
  // Value to store the value of the buff ability.
  val buffValue: Int
  // Value boolean to determine who the buff applies to.
  val buffSelf: Boolean
}

// Trait for Melee Hero Types.
trait Melee {
  val heroType = "Melee"
}

// Trait for Ranged Hero Types.
trait Ranged {
  val heroType = "Ranged"
}

// Trait for Magic Hero Types.
trait Magic {
  val heroType = "Magic"
}

// Singleton object containing the attack abilities of the Heroes.
object HeroAbility {

  // Singleton object that acts as a single ability of the Attack type.
  object Pierce extends AttackAbility {
    // Value to store the damage value of the attack.
    val attackDamage = 41
  }

  // Singleton object that acts as a single ability of the Attack type.
  object Slash extends AttackAbility {
    // Value to store the damage value of the attack.
    val attackDamage = 49
  }

  // Singleton object that acts as a single ability of the Attack type.
  object Riposte extends AttackAbility {
    // Value to store the damage value of the attack.
    val attackDamage = 60
  }

  // Singleton object that acts as a single ability of the Attack type.
  object Punch extends AttackAbility {
    // Value to store the damage value of the attack.
    val attackDamage = 70
  }

  // Singleton object that acts as a single ability of the Attack type.
  object Barrage extends AttackAbility {
    // Value to store the damage value of the attack.
    val attackDamage = 41
  }

  // Singleton object that acts as a single ability of the Attack type.
  object Hawk extends AttackAbility {
    // Value to store the damage value of the attack.
    val attackDamage = 49
  }

  // Singleton object that acts as a single ability of the Attack type.
  object QuickFire extends AttackAbility {
    // Value to store the damage value of the attack.
    val attackDamage = 60
  }

  // Singleton object that acts as a single ability of the Attack type.
  object Snipe extends AttackAbility {
    // Value to store the damage value of the attack.
    val attackDamage = 70
  }

  // Singleton object that acts as a single ability of the Attack type.
  object Spell extends AttackAbility {
    // Value to store the damage value of the attack.
    val attackDamage = 41
  }

  // Singleton object that acts as a single ability of the Attack type.
  object Curse extends AttackAbility {
    // Value to store the damage value of the attack.
    val attackDamage = 49
  }

  // Singleton object that acts as a single ability of the Attack type.
  object Summon extends AttackAbility {
    // Value to store the damage value of the attack.
    val attackDamage = 60
  }

  // Singleton object that acts as a single ability of the Attack type.
  object Pyro extends AttackAbility {
    // Value to store the damage value of the attack.
    val attackDamage = 70
  }
}

// Singleton object that acts as a single ability of the Hero Buff type.
object WarCry extends BuffAbility {
  // Value to store the buffs type (Attack or Defence).
  val buffTitle = "Defense"
  // Value to store the value of the buff ability.
  val buffValue: Int = 15
  // Value boolean to determine who the buff applies to.
  val buffSelf = true
}

// Singleton object that acts as a single ability of the Hero Buff type.
object Scare extends BuffAbility {
  // Value to store the buffs type (Attack or Defence).
  val buffTitle = "Attack"
  // Value to store the value of the buff ability.
  val buffValue: Int = -20
  // Value boolean to determine who the buff applies to.
  val buffSelf = false
}

// Singleton object that acts as a single ability of the Hero Buff type.
object Charm extends BuffAbility {
  // Value to store the buffs type (Attack or Defence).
  val buffTitle = "Defense"
  // Value to store the value of the buff ability.
  val buffValue: Int = -20
  // Value boolean to determine who the buff applies to.
  val buffSelf = false
}

// Abstract class for the specific Archer hero that extend the Hero abstract class with the Ranged trait (mixin).
abstract class Archer extends Hero with Ranged {
  // Value to store the title (name) of the Hero.
  val name = "Archer"
  // Value to store the total health points of the Hero.
  val totalHealthPoints = 45
  // Variable to store the current health points (HP) of the Hero.
  var currentHealthPoints: Double = totalHealthPoints
  // Variables to store the attack and defense stat of the Hero.
  var attack = 49
  var defense = 49
  // Variables to store the abilities of the Hero.
  var ability1: AttackAbility = Barrage
  var ability2: AttackAbility = Hawk
  var ability3: AttackAbility = Snipe
  var ability4: BuffAbility = Scare
}

// Abstract class for the specific Berserker hero that extend the Hero abstract class with the Ranged trait (mixin).
abstract class Berserker extends Hero with Ranged {
  // Value to store the title (name) of the Hero.
  val name = "Berserker"
  // Value to store the total health points of the Hero.
  val totalHealthPoints = 45
  // Variable to store the current health points (HP) of the Hero.
  var currentHealthPoints: Double = totalHealthPoints
  // Variables to store the attack and defense stat of the Hero.
  var attack = 52
  var defense = 43
  // Variables to store the abilities of the Hero.
  var ability1: AttackAbility = Barrage
  var ability2: AttackAbility = QuickFire
  var ability3: AttackAbility = Snipe
  var ability4: BuffAbility = WarCry
}

// Abstract class for the specific Blademan hero that extend the Hero abstract class with the Melee trait (mixin).
abstract class Blademan extends Hero with Melee {
  // Value to store the title (name) of the Hero.
  val name = "Blademan"
  // Value to store the total health points of the Hero.
  val totalHealthPoints = 44
  // Variable to store the current health points (HP) of the Hero.
  var currentHealthPoints: Double = totalHealthPoints
  // Variables to store the attack and defense stat of the Hero.
  var attack = 48
  var defense = 65
  // Variables to store the abilities of the Hero.
  var ability1: AttackAbility = Pierce
  var ability2: AttackAbility = Slash
  var ability3: AttackAbility = Riposte
  var ability4: BuffAbility = Scare
}

// Abstract class for the specific Brawler hero that extend the Hero abstract class with the Melee trait (mixin).
abstract class Brawler extends Hero with Melee {
  // Value to store the title (name) of the Hero.
  val name = "Brawler"
  // Value to store the total health points of the Hero.
  val totalHealthPoints = 45
  // Variable to store the current health points (HP) of the Hero.
  var currentHealthPoints: Double = totalHealthPoints
  // Variables to store the attack and defense stat of the Hero.
  var attack = 49
  var defense = 65
  // Variables to store the abilities of the Hero.
  var ability1: AttackAbility = Pierce
  var ability2: AttackAbility = Riposte
  var ability3: AttackAbility = Punch
  var ability4: BuffAbility = Charm
}

// Abstract class for the specific Caster hero that extend the Hero abstract class with the Magic trait (mixin).
abstract class Caster extends Hero with Magic {
  // Value to store the title (name) of the Hero.
  val name = " "
  // Value to store the total health points of the Hero.
  val totalHealthPoints = 45
  // Variable to store the current health points (HP) of the Hero.
  var currentHealthPoints: Double = totalHealthPoints
  // Variables to store the attack and defense stat of the Hero.
  var attack = 52
  var defense = 43
  // Variables to store the abilities of the Hero.
  var ability1: AttackAbility = Spell
  var ability2: AttackAbility = Summon
  var ability3: AttackAbility = Pyro
  var ability4: BuffAbility = Charm
}

// Abstract class for the specific Caster hero that extend the Hero abstract class with the Magic trait (mixin).
abstract class Cleric extends Hero with Magic {
  // Value to store the title (name) of the Hero.
  val name = "Cleric"
  // Value to store the total health points of the Hero.
  val totalHealthPoints = 50
  // Variable to store the current health points (HP) of the Hero.
  var currentHealthPoints: Double = totalHealthPoints
  // Variables to store the attack and defense stat of the Hero.
  var attack = 65
  var defense = 64
  // Variables to store the abilities of the Hero.
  var ability1: AttackAbility = Curse
  var ability2: AttackAbility = Summon
  var ability3: AttackAbility = Pyro
  var ability4: BuffAbility = WarCry
}
