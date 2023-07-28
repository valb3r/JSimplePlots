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
            examples[blockName!!] += line + System.lineSeparator()
        }
    }
}



var readme = ""
var blockName: String? = null

val readmeFile = File("README.md")
readmeFile.forEachLine { line ->
    if (line.contains("@embed-example-start")) {
        blockName = line.split(":")[1]
        val language = line.split(":")[2]
        readme += line + System.lineSeparator()
        readme += "```$language" + System.lineSeparator()
        readme += examples[blockName]!!.trimIndent() + System.lineSeparator()
        return@forEachLine
    }

    if (line.contains("@embed-example-end")) {
        blockName = null
        readme += "```" + System.lineSeparator()
        readme += line + System.lineSeparator()
        return@forEachLine
    }

    if (null == blockName) {
        readme += line + System.lineSeparator()
    }
}


readmeFile.writeText(readme)