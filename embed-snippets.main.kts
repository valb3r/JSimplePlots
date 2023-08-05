import java.io.File

val examples = mutableMapOf<String, String>()


File("examples").walk().filter { it.name.endsWith(".kt") ||  it.name.endsWith(".kts")}.forEach {
    extractExamplesFromFile(it, "kotlin")
}

File("examples").walk().filter { it.name.endsWith(".java") }.forEach {
    extractExamplesFromFile(it, "java")
}


var readme = ""
var blockName: String? = null

val readmeFile = File("README.md")
readmeFile.forEachLine { line ->
    if (line.contains("@embed-example-start")) {
        blockName = line.split(":")[1]
        val language = line.split(":")[2].split(" ")[0]
        readme += line + System.lineSeparator()
        readme += "```$language" + System.lineSeparator()
        readme += examples["$blockName:$language"]!!.trimIndent() + System.lineSeparator()
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

fun Embed_snippets_main.extractExamplesFromFile(it: File, language: String) {
    var blockName: String? = null
    it.forEachLine { line ->
        if (line.contains("@example-start:")) {
            blockName = line.split(":")[1]
            examples["$blockName:$language"] = ""
            return@forEachLine
        }

        if (line.contains("@example-end")) {
            blockName = null
            return@forEachLine
        }

        if (null != blockName) {
            examples["$blockName:$language"] += line + System.lineSeparator()
        }
    }
}