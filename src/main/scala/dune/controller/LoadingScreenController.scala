// Class acts as controller for Loading screen.
package dune.controller

// ScalaFX Imports
import scalafx.scene.image.{Image, ImageView}
// ScalaFXML Macro Imports
import scalafxml.core.macros.sfxml

// SFXML Annotated LoadingScreenController class.
@sfxml
class LoadingScreenController(private val loadingBackground: ImageView) {

  // Assign image from resources to act as background image.
  loadingBackground.image = new Image(getClass().getResourceAsStream("/dune/picture/loadingBackground.jpg"))
}
