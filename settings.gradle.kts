rootProject.name = "JSimplePlots"
include("plots")
include("data-adapters")

if (null == System.getenv()["JITPACK"]) {
    include("tests")
    include("examples")
    include("examples:kotlin")
    findProject(":examples:kotlin")?.name = "kotlin"
    include("examples:java")
    findProject(":examples:java")?.name = "java"
}
