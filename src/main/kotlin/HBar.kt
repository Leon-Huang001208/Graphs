import javafx.geometry.Insets
import javafx.scene.Group
import javafx.scene.canvas.Canvas
import javafx.scene.layout.*
import javafx.scene.paint.Color
import kotlin.math.abs
import kotlin.random.Random

class HBar(private val model: Model) : Pane(), IView {

    override fun updateView() {
        draw(model.selectColor)
    }

    private fun draw(color: String) {
        children.clear()
        val canvas = Canvas(this.width, this.height)
        val width = canvas.width - 40
        val height = canvas.height - 40
        val ptsNumber = model.data.count()
        var top = model.data.max()
        var bottom = model.data.min()
        val gap = height / (2 * ptsNumber - 1)
        val posX = 20.0
        val posY = 20.0
        var black = true
        var red = 255
        var green = 0
        var blue = 0

        canvas.graphicsContext2D.apply {
            if (ptsNumber == 1 && top != 0.0) {
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
                } else if (color == "black and white") {
                    if (black) {
                        fill = Color.BLACK
                        black = false
                    } else {
                        fill = Color.WHITE
                        black = true
                    }
                }
                fillRect(posX, posY, width, height)
                if (color == "black and white") strokeRect(posX, posY, width, height)
            } else if (ptsNumber > 1) {
                if (top == bottom) {
                    if (top > 0.0)  {
                        bottom = 0.0
                    } else if (top < 0.0) {
                        top = 0.0
                    } else if (top == 0.0) {
                        top = height
                    }
                } else if (bottom >= 0.0) {
                    bottom = 0.0
                } else if (top <= 0.0) {
                    top = 0.0
                }
                for (i in 0 until ptsNumber) {
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
                    if (model.data[i] >= 0.0) {
                        fillRect(posX + abs(bottom) / (top - bottom) * width, posY + gap * i * 2, model.data[i] / (top - bottom) * width, gap)
                        if (color == "B & W") strokeRect(posX + abs(bottom) / (top - bottom) * width, posY + gap * i * 2, model.data[i] / (top - bottom) * width, gap)
                    } else if (model.data[i] < 0.0) {
                        fillRect(posX + (model.data[i] - bottom) / (top - bottom) * width, posY + gap * i * 2, abs(model.data[i]) / (top - bottom) * width, gap)
                        if (color == "B & W") strokeRect(posX + (model.data[i] - bottom) / (top - bottom) * width, posY + gap * i * 2, abs(model.data[i]) / (top - bottom) * width, gap)
                    }
                }
                stroke = Color.BLACK
                lineWidth = 1.5
                if (bottom < 0.0 && top > 0.0) strokeLine(posX + abs(bottom) / (top - bottom) * width, posY, posX + abs(bottom) / (top - bottom) * width, posY + height)
                if (bottom == 0.0) strokeLine(posX, posY, posX, posY + height)
                if (top == 0.0) strokeLine(posX + width, posY, posX + width, posY + height)
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