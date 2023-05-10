import javafx.geometry.Insets
import javafx.scene.Group
import javafx.scene.canvas.Canvas
import javafx.scene.layout.*
import javafx.scene.paint.Color

class Line(private val model: Model) : Pane(), IView {

    override fun updateView() {
        draw()
    }

    private fun draw() {
        children.clear()
        val canvas = Canvas(this.width, this.height)
        val width = canvas.width - 40
        val height = canvas.height - 40
        val ptsNumber = model.data.count()
        val top = model.data.max()
        val bottom = model.data.min()
        val gap = width / (ptsNumber - 1)
        val posX = 20.0
        val posY = 20.0
        canvas.graphicsContext2D.apply {
            if (ptsNumber > 1) {
                if (top - bottom > 0.0) {
                    stroke = Color.BLACK
                    for (i in (0 until ptsNumber - 1)) {
                        lineWidth = 1.5
                        strokeLine(posX + gap * i,posY +(top - model.data[i]) / (top - bottom) * height, posX + gap * (i + 1), posY + (top - model.data[i+1]) / (top - bottom) * height)
                    }
                    fill = Color.RED
                    for (i in (0 until ptsNumber)) {
                        fillOval(posX + gap * i - 2.5, posY + (top - model.data[i]) / (top - bottom) * height - 2.5, 5.0, 5.0)
                    }
                } else if (top - bottom == 0.0) {
                    stroke = Color.BLACK
                    strokeLine(posX, posY + height / 2, posX + width, posY + height / 2)
                    fill = Color.RED
                    for (i in (0 until ptsNumber)) {
                        fillOval(posX + gap * i - 2.5, posY + height / 2 - 2.5, 5.0, 5.0)
                    }
                }

            } else if (ptsNumber == 1) {
                fill = Color.RED
                fillOval(posX + width / 2 - 2.5, posY + height - 2.5, 5.0, 5.0)
            }
        }
        children.add(Group(canvas))
    }

    private fun controller() {
        this.heightProperty().addListener { _, _, _ ->
            draw()
        }
        this.widthProperty().addListener { _, _, _ ->
            draw()
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