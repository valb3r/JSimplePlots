import com.valb3r.jsimpleplots.data_adapters.DataAdapter
import com.valb3r.jsimpleplots.plots.SimplePlots
import com.valb3r.jsimpleplots.plots.log10
import java.io.File

class Main {

    class Main {

        companion object {

            @JvmStatic
            fun main(args: Array<String>) {
                val csv = DataAdapter.csv().ofNumeric(File("data/results.csv"))
                SimplePlots.surface()
                    .x(csv.columnFloat("Learning rate").log10())
                    .y(csv.columnFloat("L2").log10())
                    .z(csv.columnFloat("F1"))
                    .plot()
            }
        }
    }
}