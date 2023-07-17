package plots

class SimplePlots {

    companion object Plots {

        fun xy(): XY {
            return XY()
        }

        fun heatmap(): Heatmap {
            return Heatmap()
        }

        fun surface(): Surface {
            return Surface()
        }
    }
}

class XY {
    fun plot(): XY {
        return this
    }
}

class Heatmap {

    fun plot(): Heatmap {
        return this
    }
}

class Surface {

    fun plot(): Surface {
        return this
    }
}