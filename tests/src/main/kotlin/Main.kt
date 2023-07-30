import com.valb3r.jsimpleplots.data_adapters.DataAdapter
import com.valb3r.jsimpleplots.plots.SimplePlots
import com.valb3r.jsimpleplots.plots.log10
import java.io.File

class MainLinear {

    companion object {

        @JvmStatic
        fun main(args: Array<String>) {
            val csv = DataAdapter.csv().of(File("data/results.csv"))
            SimplePlots.linear()
                .y(csv["Learning rate"].float())
                .named("Learning rate")
                .plot()
        }
    }
}

class MainDistribution {

    companion object {

        @JvmStatic
        fun main(args: Array<String>) {
            val csv = DataAdapter.csv().of(File("data/results.csv"))
            SimplePlots.distributionHistogram()
                .y(csv["Learning rate"].float())
                .named("Learning rate")
                .plot()
        }
    }
}

class MainSimple {

    companion object {

        @JvmStatic
        fun main(args: Array<String>) {
            val csv = DataAdapter.csv().of(File("data/results.csv"))
            SimplePlots.surface()
                .x(csv.column("Learning rate").float().log10())
                .y(csv.column("L2").float().log10())
                .z(csv.column("F1").float())
                .plot()
        }
    }
}

class MainIndexed {

    companion object {

        @JvmStatic
        fun main(args: Array<String>) {
            val csv = DataAdapter.csv().of(File("data/results.csv"))
            SimplePlots.surface()
                .x(csv["Learning rate"].float().log10())
                .y(csv["L2"].float().log10())
                .z(csv["F1"].float())
                .plot()
        }
    }
}