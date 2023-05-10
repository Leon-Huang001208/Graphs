import javafx.event.EventHandler
import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.control.*
import javafx.scene.layout.*

class DataEntry(private val model: Model) : VBox(), IView {

    private val datasetName = Label("Dataset name: quadratic")

    private val addEntry = Button("Add Entry").apply {
        prefWidth = Double.MAX_VALUE
        alignment = Pos.CENTER
        HBox.setHgrow(this, Priority.ALWAYS)
    }

    override fun updateView() {
        datasetName.text = "Dataset name: ${model.name}"
        displayEntry()
    }

    private fun constructView() {
        children.addAll(datasetName, addEntry)
        prefWidth = 400.0
        spacing = 10.0
        padding = Insets(5.0)
    }

    private fun controller() {
        addEntry.onAction = EventHandler {
            model.addData()
            model.name = model.name
        }
    }


    private fun removeEntry() {
        val size = children.count()
        if (size > 2) children.remove(1, size-1)
    }

    private fun displayEntry() {
        removeEntry()
        val size = model.data.count()
        for (i in (0 until size)) {
            val entry = Label("Entry #${i}").apply {
                prefWidth = 55.0
                alignment = Pos.BOTTOM_CENTER
                padding = Insets(5.0, 0.0, 5.0, 0.0)
            }
            val input = TextField("${model.data[i]}").apply {
                HBox.setHgrow(this, Priority.ALWAYS)
            }
            val cancel = Button("X").apply {
                isWrapText = true
                if (size == 1) isDisable = true
            }
            val encap = HBox(entry, input, cancel).apply {
                spacing = 10.0
            }

            this.children.add(i+1, encap)

            input.onAction = EventHandler {
                val newValue = input.text.toDouble()
                model.changeData(i, newValue)
                model.name = model.name
            }

            input.onKeyPressedProperty().addListener { _, _, _ ->
                model.changeData(i, input.text.toDouble())
                model.name = model.name
            }

            cancel.onAction = EventHandler {
                cancel.isDisable = false
                model.deleteData(i)
                model.name = model.name
            }
        }
    }

    init {
        constructView()
        controller()
        this.model.addView(this)
    }
}