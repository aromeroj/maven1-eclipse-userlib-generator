package aromeroj.utils

import scala.collection.mutable.ArrayBuffer

/**
 * Created by Anderson on 6/12/15.
 */
class EclipseUserLibGenerator(ignoredGroups: Array[String], ignoredArtifacts: Array[String]) {

  val XmlDependencyTag = "dependency"

  val ProjectXmlFileName = "project.xml"

  val MavenHomeLocal: String = "MAVEN_HOME_LOCAL"

  //TODO infer maven1 home local if not set
  val maven1Home = sys.env(MavenHomeLocal)


  def dependenciesPath(projectXmlPath: String): Array[String] = {
    val fileToLoad = projectXmlFilePath(projectXmlPath)

    val projectXml = scala.xml.XML.loadFile(fileToLoad)

    val maven1PropertiesResolver = new Maven1PropertiesResolver(projectXmlPath + DependencyPath.PathSeparator + Maven1PropertiesResolver.getFatherPropertiesName(projectXml))

    var dependencyRelativePath = ArrayBuffer[String]()

    (projectXml \ "dependencies").foreach { dependencies =>
      (dependencies \ "dependency").foreach { dependency =>

        val dependencyObject = Maven1Dependency.deserialize(dependency)
        if (!dependencyObject.isInIgnoredGroupIds(ignoredGroups) && !dependencyObject.isInIgnoredArtifactIds(ignoredArtifacts)) {
          dependencyRelativePath += maven1PropertiesResolver.resolveProperty(dependencyObject.relativePath)
        }
      }
    }

    maven1PropertiesResolver.close

    dependencyRelativePath.toArray
  }

  def completeDependencyPath(relativePaths: Array[String]): Array[String] = {

    if (maven1Home.isEmpty) {
      println("Environment variable " + MavenHomeLocal + " not set!")
      sys.exit(1)
    }

    relativePaths.map(x => maven1Home + DependencyPath.PathSeparator + "repository" + DependencyPath.PathSeparator + x)
  }

  def projectXmlFilePath(projectXmlPath: String): String = {
    projectXmlPath + DependencyPath.PathSeparator + ProjectXmlFileName
  }

  def projectName(projectXmlPath: String): String = {
    val fileToLoad = projectXmlFilePath(projectXmlPath)
    val projectXml = scala.xml.XML.loadFile(fileToLoad)
    (projectXml \ "artifactId").text
  }

  def eclipseUserLibraryNode(projectXmlPath: String): EclipseUserLibrary = {
    val libraryName = projectName(projectXmlPath)
    val archivePaths = completeDependencyPath(dependenciesPath(projectXmlPath))

    new EclipseUserLibrary(libraryName, false, archivePaths)
  }

  def buildEclipseLibraries(projectPaths: Array[String]): EclipseUserLibraries = {
    val userLibraries = new EclipseUserLibraries()
    projectPaths.map( x => userLibraries.addLibrary(eclipseUserLibraryNode(x)))
    return userLibraries
  }
}