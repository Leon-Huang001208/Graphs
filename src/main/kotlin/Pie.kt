import javafx.geometry.Insets
import javafx.scene.Group
import javafx.scene.canvas.Canvas
import javafx.scene.layout.*
import javafx.scene.paint.Color
import javafx.scene.shape.ArcType
import kotlin.math.*
import kotlin.random.Random

class Pie(private val model: Model) : Pane(), IView {

    override fun updateView() {
        draw(model.selectColor)
    }

    private fun draw(color: String) {
        children.clear()
        val canvas = Canvas(this.width, this.height)
        val width = canvas.width - 40
        val height = canvas.height - 40
        val ptsNumber = model.data.count()
        val posX = 20.0
        val posY = 20.0
        val sum = model.data.sum()
        var angel = 0.0
        var black = true
        var red = 255
        var green = 0
        var blue = 0

        canvas.graphicsContext2D.apply {
            model.data.forEach() {
                if (color == "flashing") {
                    fill = Color.color(Random.nextDouble(1.0), Random.nextDouble(1.0), Random.nextDouble(1.0))
                } else if (color == "rainbow") {
                    fill = Color.rgb(red, green, blue)
                    if (red == 255 && green < 255 && blue == 0) {
                        green += 51
                    } else if (red > 0 && green == 255 && blue == 0) {
                        red -= 51
                    } else if (red == 0 && green == 255 && blue < 255) {
                        blue += 51
                    } else if (red == 0 && green > 0 && blue == 255) {
                        green -= 51
                    } else if (red < 255 && green == 0 && blue == 255) {
                        red += 51
                    } else if (red == 255 && green == 0 && blue > 0) {
                        blue -= 51
                    }
                } else if (color == "B & W") {
                    if (black) {
                        fill = Color.BLACK
                        black = false
                    } else {
                        fill = Color.WHITE
                        black = true
                    }
                }
                if (width >= height) {
                    fillArc(posX + abs(width - height) / 2, posY, min(width, height), min(width, height), angel,  it / sum * 360.0, ArcType.ROUND)
                    if (color == "B & W") strokeArc(posX + abs(width - height) / 2, posY, min(width, height), min(width, height), angel,  it / sum * 360.0, ArcType.ROUND)
                }
                if (width < height) {
                    fillArc(posX, posY + abs(width - height) / 2, min(width, height), min(width, height), angel,  it / sum * 360.0, ArcType.ROUND)
                    if (color == "B & W") strokeArc(posX, posY + abs(width - height) / 2, min(width, height), min(width, height), angel,  it / sum * 360.0, ArcType.ROUND)
                }
                angel += it / sum * 360.0
            }
        }
        children.add(Group(canvas))
    }

    private fun controller() {
        this.heightProperty().addListener { _, _, _ ->
            draw(model.selectColor)
        }
        this.widthProperty().addListener { _, _, _ ->
            draw(model.selectColor)
        }
    }

    init {
        controller()
        HBox.setHgrow(this, Priority.ALWAYS)
        VBox.setVgrow(this, Priority.ALWAYS)
        padding = Insets(10.0)
        this.model.addView(this)
    }
}