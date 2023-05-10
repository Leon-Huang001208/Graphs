import javafx.scene.layout.*

class Visualizer(private val model: Model, private val line: Line, private val bar: Bar, private val barSEM: BarSEM, private val pie: Pie, private val hBar: HBar) : VBox(), IView {


    override fun updateView() {
        children.clear()
        when (model.selectView) {
            "line" -> children.add(line)
            "bar" -> children.add(bar)
            "hBar" ->children.add(hBar)
            "barSEM" -> children.add(barSEM)
            "pie" -> children.add(pie)
        }
    }

    init {
        this.model.addView(this)
    }
}