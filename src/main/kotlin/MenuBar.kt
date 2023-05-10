import javafx.collections.FXCollections
import javafx.event.EventHandler
import javafx.geometry.*
import javafx.scene.control.*
import javafx.scene.layout.*


class MenuBar(private val model: Model) : HBox(), IView {

    private val selector = ChoiceBox(FXCollections.observableArrayList("quadratic", "negative quadratic", "alternating", "random", "inflation")).apply {
        minWidth = 130.0
        value = "quadratic"
    }

    private val creator = TextField("").apply {
        promptText = "Data set name"
        prefWidth = 130.0
        minWidth = 130.0
    }

    private val create = Button("Create")
    private val line = Button("Line").apply { prefWidth = 75.0 }
    private val bar = Button("Bar").apply { prefWidth = 75.0 }
    private val hBar = Button("HBar").apply { prefWidth = 75.0 }
    private val barSEM = Button("Bar (SEM)").apply { prefWidth = 75.0 }
    private val pie = Button("Pie").apply { prefWidth = 75.0 }

    private val color = ChoiceBox(FXCollections.observableArrayList("flashing", "rainbow", "B & W")).apply {
        minWidth = 80.0
        value = "flashing"
    }

    override fun updateView() {
        if (model.new) {
            selector.items.add(model.name)
            model.new = false
            selector.value = model.name
        }
        if (model.data.min() < 0.0) {
            barSEM.isDisable = true
            pie.isDisable = true
        } else if (model.data.min() >= 0.0) {
            barSEM.isDisable = false
            pie.isDisable = false
        }
    }

    private fun constructView() {

        // data set selector
        val DSS = HBox(selector).apply {
            alignment = Pos.CENTER
            spacing = 0.0
            padding = Insets(0.0, 10.0, 0.0, 0.0)
        }

        // data set creator
        val DSC = HBox(creator, create).apply {
            alignment = Pos.CENTER
            spacing = 0.0
            padding = Insets(0.0, 10.0, 0.0, 10.0)
        }

        // visualization selector
        val VS = HBox(line, bar, hBar, barSEM, pie, color).apply {
            alignment = Pos.CENTER
            spacing = 0.0
            padding = Insets(0.0, 0.0, 0.0, 10.0)
        }

        children.addAll(DSS, Separator(Orientation.VERTICAL), DSC, Separator(Orientation.VERTICAL), VS)
        padding = Insets(0.0)
    }

    private fun controller() {
        create.onAction = EventHandler {
            val name = creator.text.toString()
            if (name != "") {
                creator.text = ""
                model.new = true
                model.newData(name)
                model.name = name
                model.new = false
            }
        }

        selector.selectionModel.selectedIndexProperty().addListener { _, _, newValue ->
            model.name = selector.items[newValue.toInt()]
        }

        line.onAction = EventHandler {
            model.selectView = "line"
        }

        bar.onAction = EventHandler {
            model.selectView = "bar"
        }

        hBar.onAction = EventHandler {
            model.selectView = "hBar"
        }

        barSEM.onAction = EventHandler {
            model.selectView = "barSEM"
        }

        pie.onAction = EventHandler {
            model.selectView = "pie"
        }

        color.selectionModel.selectedIndexProperty().addListener { _, _, newValue ->
            model.selectColor = color.items[newValue.toInt()]
        }
    }

    init {
        constructView()
        controller()
        this.model.addView(this)
    }
}
