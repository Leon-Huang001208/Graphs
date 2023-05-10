import javafx.geometry.*
import javafx.geometry.VPos
import javafx.scene.Group
import javafx.scene.canvas.Canvas
import javafx.scene.layout.*
import javafx.scene.paint.Color
import javafx.scene.text.TextAlignment
import kotlin.math.*
import kotlin.random.Random

class BarSEM(private val model: Model) : Pane(), IView {

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
        val gap = width / (2 * ptsNumber - 1)
        val posX = 20.0
        val posY = 20.0
        var black = true
        var red = 255
        var green = 0
        var blue = 0

        canvas.graphicsContext2D.apply {// all data >= 0
            if (ptsNumber == 1 && top > 0.0) {
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
                fillRect(posX, posY, width, height)
                if (color == "B & W") strokeRect(posX, posY, width, height)
            } else if (ptsNumber > 1) {
                if (top == 0.0) top = height
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
                    fillRect(posX + gap * i * 2, posY + (top - model.data[i]) / top * height, gap, model.data[i] / top * height)
                    if (color == "B & W") strokeRect(posX + gap * i * 2, posY + (top - model.data[i]) / top * height, gap, model.data[i] / top * height)
                }
                stroke = Color.BLACK
                lineWidth = 1.5
                strokeLine(posX, posY + height, posX + width, posY + height)
            }

            // mean
            val mean = model.data.sum() / ptsNumber
            stroke = Color.BLACK
            lineWidth = 1.5
            strokeLine(posX, posY + (top - mean) / top * height, posX + width, posY + (top - mean) / top * height)

            var sd = 0.0
            model.data.forEach {
                sd += (it - mean).pow(2)
            }
            sd = sqrt(sd / ptsNumber)

            val sem = sd / sqrt(ptsNumber.toDouble())

            setLineDashes(5.0)
            strokeLine(posX, posY + (top - mean - sem) / top * height, posX + width, posY + (top - mean - sem) / top * height)
            strokeLine(posX, posY + (top - mean + sem) / top * height, posX + width, posY + (top - mean + sem) / top * height)

            fill = Color.BLACK
            textAlign = TextAlignment.LEFT
            textBaseline = VPos.CENTER
            background = Background.EMPTY
            fillText("mean: " + String.format("%.2f", mean) + "\nSEM: " + String.format("%.2f", sem), posX + 10.0, posY + 20.0)
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