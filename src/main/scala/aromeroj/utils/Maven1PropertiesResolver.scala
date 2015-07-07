package aromeroj.utils

import java.io.FileInputStream
import java.util.Properties

import scala.xml.Node

/**
 * Created by Anderson on 6/13/15.
 */
class Maven1PropertiesResolver(filePath: String) extends Closable {

  val maven1Properties = new Properties()

  val fis = new FileInputStream(filePath);

  maven1Properties.load(fis)

  def resolveProperty(dependencyPath: String): String = {
    if(dependencyPath.contains("$")) {
      val placeHolder = dependencyPath.substring(dependencyPath.indexOf('{') + 1, dependencyPath.indexOf('}'))
      val propertyValue = maven1Properties.getProperty(placeHolder)

      return dependencyPath.replaceFirst("\\$\\{.*\\}", propertyValue)
    }
    return dependencyPath
  }

  override def close: Unit = {
    if (fis != null) {
      try {
        fis.close()
      } catch {
        case e: Exception => System.err.println(e)
      }
    }
  }
}

object Maven1PropertiesResolver {

  def getFatherPropertiesName(projectXmlMainNode: Node): String = {

    val fatherMaven1Path = (projectXmlMainNode \ "extend").text

    val fatherXmlPath = fatherMaven1Path.replace("${basedir}", ".")

    fatherXmlPath.replace(".xml", ".properties")
  }

}
