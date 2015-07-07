package aromeroj.utils

import scala.xml.Node

/**
 * Created by Anderson on 6/13/15.
 */
class Maven1Dependency(val groupId: String, val artifactId: String, val version: String, val id: String, val jar: String, val dType: String) {

  def relativePath: String = {
    val relativePathBuilder = new StringBuilder()

    if (groupId.isEmpty && !id.isEmpty) {
      relativePathBuilder.append(id)
    } else {
      relativePathBuilder.append(groupId)
    }

    relativePathBuilder.append(DependencyPath.PathSeparator)
    if (!dType.isEmpty && dType.equals("plugin")) {
      relativePathBuilder.append(DependencyPath.PluginFolder)
    } else {
      relativePathBuilder.append(DependencyPath.JarsFolder)
    }
    relativePathBuilder.append(DependencyPath.PathSeparator)

    if (!jar.isEmpty) {
      relativePathBuilder.append(jar)
    } else if (!artifactId.isEmpty) {
      relativePathBuilder.append(artifactId)
      relativePathBuilder.append(DependencyPath.JarVersionSeparator)
      relativePathBuilder.append(version)
      relativePathBuilder.append(DependencyPath.JarExtension)
    } else if (artifactId.isEmpty && !id.isEmpty) {
      relativePathBuilder.append(id)
      relativePathBuilder.append(DependencyPath.JarVersionSeparator)
      relativePathBuilder.append(version)
      relativePathBuilder.append(DependencyPath.JarExtension)
    }

    relativePathBuilder.toString()
  }

  def isInIgnoredGroupIds(ignoredGroups: Array[String]): Boolean = ignoredGroups.contains(groupId)

  def isInIgnoredArtifactIds(ignoredArtifacts: Array[String]): Boolean = ignoredArtifacts.contains(artifactId)
}

object Maven1Dependency extends XmlDeserializable[Maven1Dependency] {
  override def deserialize(dependencyXmlNode: Node): Maven1Dependency = {

    val groupId = (dependencyXmlNode \ "groupId").text
    val artifactId = (dependencyXmlNode \ "artifactId").text
    val version = (dependencyXmlNode \ "version").text
    val id = (dependencyXmlNode \ "id").text
    val jar = (dependencyXmlNode \ "jar").text
    val dType = (dependencyXmlNode \ "type").text

    new Maven1Dependency(groupId, artifactId, version, id, jar, dType)
  }
}
