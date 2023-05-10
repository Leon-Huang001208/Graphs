import javafx.application.Application
import javafx.geometry.*
import javafx.scene.Scene
import javafx.scene.control.*
import javafx.scene.layout.*
import javafx.stage.Stage

class Main : Application() {
    override fun start(stage: Stage) {
        val model = Model()

        val menuBar = MenuBar(model)
        val dataEntry = DataEntry(model).apply {
        }
        val scroll = ScrollPane(dataEntry).apply {
            vbarPolicy = ScrollPane.ScrollBarPolicy.ALWAYS
            isFitToWidth = true
        }
        val line = Line(model)
        val bar = Bar(model)
        val barSEM = BarSEM(model)
        val pie = Pie(model)
        val hBar = HBar(model)
        val visual = Visualizer(model, line, bar, barSEM, pie, hBar)
        val split = SplitPane(scroll, visual)

        val root = BorderPane(split, menuBar, null, null, null)
        BorderPane.setMargin(split, Insets(0.5, 0.0, 0.0, 0.0))

        val scene = Scene(root, 850.0, 600.0)

        stage.apply {
            this.scene = scene
            title = "CS349 - A2 Graphs - y649huan"
            isResizable = true
            minWidth = 640.0
            minHeight = 480.0
            show()
        }
    }
}