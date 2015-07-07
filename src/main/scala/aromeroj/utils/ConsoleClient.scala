package aromeroj.utils

/**
 * Created by Anderson on 6/13/15.
 */
object ConsoleClient {

  val IgnoreGroupIdsOption: String = "-i"
  val ProjectPathsOption: String = "-p"
  val HelpOption: String = "-?"
  val IgnoreArtifactIdsOption: String = "-ia"

  def main(args:Array[String]) {

    if (args.contains(HelpOption)) { printHelp; return }

    var groupsToIgnore: Array[String] = Array.empty[String]

    val ignoreIndex = args.indexOf(IgnoreGroupIdsOption)
    if (ignoreIndex != -1) {
      groupsToIgnore = args(ignoreIndex + 1).split(",")
    }

    var artifactsToIgnore: Array[String] = Array.empty[String]

    val ignoreArtifactIdsIndex = args.indexOf(IgnoreArtifactIdsOption)
    if (ignoreArtifactIdsIndex != -1) {
      artifactsToIgnore = args(ignoreArtifactIdsIndex + 1).split(",")
    }

    val generator = new EclipseUserLibGenerator(groupsToIgnore, artifactsToIgnore)


    val projectIndex = args.indexOf(ProjectPathsOption)
    if (projectIndex == -1) { println("Project paths option (-p) not found!"); printHelp; sys.exit(1) }

    val projectPaths: Array[String] = args(projectIndex + 1).split(",")

    val userLibs = generator.buildEclipseLibraries(projectPaths)

    println(userLibs.serialize)

  }

  def printHelp: Unit = {
    println("Usage -i <dependencies group ids separated by coma to ignore, optional> -ia <dependencies artifact ids separated by coma to ignore, optional> -p <project paths separated by coma> -? <usage help>")
  }
}
