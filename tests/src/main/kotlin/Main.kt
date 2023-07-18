import com.valb3r.jsimpleplots.data_adapters.DataAdapter
import com.valb3r.jsimpleplots.plots.SimplePlots
import java.io.File

class Main {

    class Main {

        companion object {

            @JvmStatic
            fun main(args: Array<String>) {
                val csv = DataAdapter.csv().ofNumeric(File("data/results.csv"))
                SimplePlots.surface()
                    .x(csv.columnFloat("Learning rate"))
                    .y(csv.columnFloat("L2"))
                    .z(csv.columnFloat("F1"))
                    .plot()
            }
        }
    }
}