import kotlin.random.Random

class Model {
    class Dataset {
        var name = String()
        var data = mutableListOf<Double>()
    }

    private val views: ArrayList<IView> = ArrayList()

    var dataset: ArrayList<Dataset> = ArrayList<Dataset>()

    var data = mutableListOf<Double>(0.1, 1.0, 4.0, 9.0, 16.0)

    var new = false

    var selectColor = "flashing"
        set(value) {
            field = value
            updateAllViews()
        }

    var selectView = "line"
        set(value) {
            field = value
            updateAllViews()
        }

    var name = "quadratic"
        set(value) {
            field = value
            findData(field)
            if (data.min() < 0.0 && selectView != "bar" && selectView != "hBar") selectView = "line"
            updateAllViews()
        }

    fun addData() {
        data.add(0.0)
        for (i in dataset) {
            if (i.name == name) {
                i.data = data
            }
        }
    }

    fun deleteData(index: Int) {
        data.removeAt(index)
        for (i in dataset) {
            if (i.name == name) {
                i.data = data
            }
        }
    }

    fun changeData(index: Int, newValue: Double) {
        if (data.min() >= 0.0 && newValue <= 0.0 && selectView != "bar" && selectView != "hBar") selectView = "line"
        data[index] = newValue
        for (i in dataset) {
            if (i.name == name) {
                i.data = data
            }
        }
    }

    private fun findData(name: String) {
        for (i in dataset) {
            if (i.name == name) {
                data = i.data
            }
        }
    }

    fun newData(name: String) {
        val tmp = Dataset().apply {
            this.name = name
            this.data.add(0.0)
        }
        dataset.add(tmp)
    }

    fun addView(view: IView) {
        views.add(view)
        view.updateView() // update Views!
    }

    fun removeView(view: IView) {
        views.remove(view)
    }

    private fun updateAllViews() {
        for (view in views) {
            view.updateView()
        }
    }

    private val quadratic = Dataset().apply {
        name = "quadratic"
        data.add(0.1)
        data.add(1.0)
        data.add(4.0)
        data.add(9.0)
        data.add(16.0)
    }

    private val negativeQuadratic = Dataset().apply {
        name = "negative quadratic"
        data.add(-0.1)
        data.add(-1.0)
        data.add(-4.0)
        data.add(-9.0)
        data.add(-16.0)
    }

    private val alternating = Dataset().apply {
        name = "alternating"
        data.add(-1.0)
        data.add(3.0)
        data.add(-1.0)
        data.add(3.0)
        data.add(-1.0)
        data.add(3.0)
    }

    private val random = Dataset().apply {
        name = "random"
        for (i in (0 until 20)) {
            data.add(Random.nextDouble(-100.0, 100.0))
        }
    }

    private val inflation = Dataset().apply {
        name = "inflation"
        data.add(4.8)
        data.add(5.6)
        data.add(1.5)
        data.add(1.9)
        data.add(0.2)
        data.add(2.1)
        data.add(1.6)
        data.add(1.6)
        data.add(1.0)
        data.add(1.7)
        data.add(2.7)
        data.add(2.5)
        data.add(2.3)
        data.add(2.8)
        data.add(1.9)
        data.add(2.2)
        data.add(2.0)
        data.add(2.1)
        data.add(2.4)
        data.add(0.3)
        data.add(1.8)
        data.add(2.9)
        data.add(1.5)
        data.add(0.9)
        data.add(1.9)
        data.add(1.1)
        data.add(1.4)
        data.add(1.6)
        data.add(2.3)
        data.add(1.9)
        data.add(0.7)
        data.add(3.4)
        data.add(6.8)
    }

    init {
        dataset.add(quadratic)
        dataset.add(negativeQuadratic)
        dataset.add(alternating)
        dataset.add(random)
        dataset.add(inflation)
    }
}
