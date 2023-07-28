import java.io.File

val examples = mutableMapOf<String, String>()


File("examples").walk().filter { it.name.endsWith(".kt") }.forEach {
    var blockName: String? = null
    it.forEachLine { line ->
        if (line.contains("@example-start:")) {
            blockName = line.split(":")[1]
            examples[blockName!!] = ""
            return@forEachLine
        }

        if (line.contains("@example-end")) {
            blockName = null
            return@forEachLine
        }

        if (null != blockName) {
            examples[blockName!!] += line
        }
    }
}



var readme = ""
var blockName: String? = null

File("README.md").forEachLine { line ->
    if (line.contains("@embed-example-start")) {
        blockName = line.split(":")[1]
        readme += line
        return@forEachLine
    }

    if (line.contains("@example-end")) {
        blockName = null
        readme += line
        return@forEachLine
    }

    if (null == blockName) {
        readme += line
    }
}